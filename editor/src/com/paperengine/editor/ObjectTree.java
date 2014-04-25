package com.paperengine.editor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.paperengine.core.GameObject;
import com.paperengine.core.Scene;

public class ObjectTree  {

	private Tree tree;
	private Scene scene;
	
	
	public ObjectTree(Composite arg0, int arg1) {
		tree = new Tree(arg0, arg1);
	}

	public void setScene(Scene scene) {
		this.scene = scene;
		refresh();
	}

	private void refresh() {
		tree.removeAll();
		if (scene == null) return;
		for (GameObject object : scene.gameObjects()) {
			createTreeItemForObject(object, null);
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
		item.setData(object);
		for (GameObject child : object.children()) {
			createTreeItemForObject(child, item);
		}
		return item;
	}

	public void update(Scene scene) {
		
	}

	public Tree tree() {
		return tree;
	}
}
