package com.paperengine.editor;

import playn.java.JavaGraphics;
import playn.java.JavaPlatform;

public class JavaEditorGraphics extends JavaGraphics {
	
	public JavaEditorGraphics(JavaPlatform platform, JavaPlatform.Config config) {
		super(platform, config);
	}
	
	public void resize(int width, int height) {
		ctx.setSize(width, height);
	}
}
