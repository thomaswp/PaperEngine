package com.paperengine.editor;

import java.awt.Dimension;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

import org.jdesktop.swingx.JXTree;

import com.paperengine.core.GameObject;
import com.paperengine.core.Scene;

public class ObjectTree extends JXTree {
	private static final long serialVersionUID = 1L;

	private Scene scene;
	
	public ObjectTree() {
		setEditable(true);
		setMinimumSize(new Dimension(100, 0));
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
		refresh();
	}

	private void refresh() {
		DefaultTreeModel model = (DefaultTreeModel) getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		root.setUserObject("Scene");
		root.removeAllChildren();
		if (scene == null) return;
		for (GameObject object : scene.gameObjects()) {
			root.add(getNodeForObject(object));
		}
		model.reload();
	}
	
	private MutableTreeNode getNodeForObject(GameObject object) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(new GameObjectHolder(object));
		for (GameObject child : object.children()) {
			node.add(getNodeForObject(child));
		}
		return node;
	}
	
	public static class GameObjectHolder {
		public GameObject object;
		
		public GameObjectHolder(GameObject object) {
			this.object = object;
		}
		
		@Override
		public String toString() {
			return object.name();
		}
	}

	public void update(Scene scene) {
		
	}
}
