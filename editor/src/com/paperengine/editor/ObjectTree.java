package com.paperengine.editor;

import javax.swing.JTree;

import com.paperengine.core.Scene;

public class ObjectTree extends JTree {
	private static final long serialVersionUID = 1L;

	private Scene scene;
	
	public ObjectTree() {
		setEditable(true);
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
		refresh();
	}

	private void refresh() {
		System.out.println(scene);
	}
}
