package com.paperengine.editor.game;

import playn.core.Game;
import playn.core.PlayN;
import playn.java.JavaGraphics;
import playn.java.JavaPlatform;

public class JavaEditorPlatform extends JavaPlatform {

	public JavaEditorPlatform(Config config) {
		super(config);
	}

	public static JavaPlatform register() {
		return register(new Config());
	}
	
	boolean resize;
	int width, height;

	/**
	 * Registers the Java platform with the specified configuration.
	 */
	public static JavaEditorPlatform register(Config config) {
		// guard against multiple-registration (only in headless mode because this can happen when
		// running tests in Maven; in non-headless mode, we want to fail rather than silently ignore
		// erroneous repeated registration)
		if (config.headless && testInstance != null) {
			return testInstance;
		}
		JavaEditorPlatform instance = new JavaEditorPlatform(config);
		if (config.headless) {
			testInstance = instance;
		}
		PlayN.setPlatform(instance);
		return instance;
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		resize = true;
	}
	
	@Override
	protected JavaGraphics createGraphics(Config config) {
		return new JavaEditorGraphics(this, config);
	}
	
	@Override
	protected void processFrame(Game game) {
		if (resize) {
			((JavaEditorGraphics) graphics()).resize(width, height);
			resize = false;
		}
		super.processFrame(game);
	}
	
	private static JavaEditorPlatform testInstance;

}
