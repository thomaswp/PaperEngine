package com.paperengine.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;

import com.paperengine.core.GameObject;
import com.paperengine.core.Scene;
import com.paperengine.core.editor.EditorLayer;
import com.paperengine.core.editor.EditorLayer.SelectedListener;

public class ObjectTree implements SelectionListener {

	private Tree tree;
	private Scene scene;
	private ObjectSelectedListener listener;
	private boolean setCallback;
	
	public ObjectTree(Composite arg0, int arg1) {
		tree = new Tree(arg0, arg1);
		tree.addSelectionListener(this);
	}

	public void setScene(Scene scene) {
		this.scene = scene;
		refresh();
	}
	
	public void setListener(ObjectSelectedListener listener) {
		this.listener = listener;
	}

	private void refresh() {
		
		TreeItem[] selected = tree.getSelection();
		int[] selectedIds = new int[selected.length];
		for (int i = 0; i < selected.length; i++) {
			selectedIds[i] = (Integer) selected[i].getData();
		}
		
		List<Integer> expanded = new ArrayList<Integer>();
		for (TreeItem item : tree.getItems()) {
			addExpanded(item, expanded);
		}
		
		tree.removeAll();
		if (scene == null) return;
		for (GameObject object : scene.gameObjects()) {
			createTreeItemForObject(object, null);
		}
		
		for (TreeItem item : tree.getItems()) {
			expand(item, expanded);
		}
		
		TreeItem[] newSelected = new TreeItem[selected.length];
		for (int i = 0; i < selectedIds.length; i++) {
			for (TreeItem item : tree.getItems()) {
				TreeItem match = getItemWithId(item, selectedIds[i]);
				if (match != null) {
					newSelected[i] = match;
					break;
				}
			}
		}

		tree.setSelection(newSelected);
		select(newSelected.length == 0 ? null : newSelected[0], true);
	}
	
	private TreeItem getItemWithId(TreeItem item, int id) {
		if ((Integer) item.getData() == id) return item;
		for (TreeItem i : item.getItems()) {
			TreeItem match = getItemWithId(i, id);
			if (match != null) return match;
		}
		return null;
	}
	
	private TreeItem getItemWithId(int id) {
		for (TreeItem item : tree.getItems()) {
			TreeItem ti = getItemWithId(item, id);
			if (ti != null) return ti;
		}
		return null;
	}
	
	private void addExpanded(TreeItem item, List<Integer> list) {
		if (item.getExpanded()) list.add((Integer) item.getData());
		for (TreeItem i : item.getItems()) {
			addExpanded(i, list);
		}
	}
	
	private void expand(TreeItem item, List<Integer> list) {
		if (list.contains(item.getData())) item.setExpanded(true);
		for (TreeItem i : item.getItems()) {
			expand(i, list);
		}
	}
	
	private TreeItem createTreeItemForObject(GameObject object, TreeItem parent) {
		TreeItem item;
		if (parent == null) {
			item = new TreeItem(tree, 0);
		} else {
			item = new TreeItem(parent, SWT.NONE);
		}
		item.setText(object.name());
		item.setData(object.id());
		for (GameObject child : object.children()) {
			createTreeItemForObject(child, item);
		}
		return item;
	}

	public void update(Scene scene) {
		if (!setCallback && EditorLayer.get() != null) {
			setCallback = true;
			EditorLayer.get().setSelectedListener(new SelectedListener() {
				@Override
				public void onSelected(final GameObject selected) {
					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							if (selected == null) {
								select((TreeItem) null, false);
							} else {
								select(getItemWithId(selected.id()), false);
							}
						}
					});
				}
			});
		}
	}

	public Tree tree() {
		return tree;
	}
	
	public interface ObjectSelectedListener {
		void onObjectSelected(GameObject object);
	}

	private void select(Widget item, boolean fromEvent) {
		GameObject object = null;
		if (item != null) object = scene.getObjectById((Integer) item.getData());
		if (listener != null) {
			listener.onObjectSelected(object);
		}
		if (fromEvent && EditorLayer.get() != null) {
			final GameObject fo = object;
			EditorLayer.get().handler().post(new Runnable() {
				@Override
				public void run() {
					EditorLayer.get().setSelectedObject(fo);
				}
			});
		} else {
			tree.setSelection(new TreeItem[] { (TreeItem) item });
		}
	}
	
	@Override
	public void widgetDefaultSelected(SelectionEvent event) {
		select(null, true);
	}

	@Override
	public void widgetSelected(SelectionEvent event) {
		select(event.item, true);
	}
}
