package com.paperengine.editor;

import java.awt.Dimension;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

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
		root.removeAllChildren();
		if (scene == null) return;
		for (GameObject object : scene.gameObjects()) {
			root.add(getNodeForObject(object));
		}
		model.reload();
	}
	
	private MutableTreeNode getNodeForObject(GameObject object) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(object.name());
		for (GameObject child : object.children()) {
			node.add(getNodeForObject(child));
		}
		return node;
	}
}
