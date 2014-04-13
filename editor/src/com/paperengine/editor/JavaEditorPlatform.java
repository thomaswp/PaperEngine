package com.paperengine.editor;

import playn.core.Game;
import playn.core.PlayN;
import playn.java.JavaPlatform;

public class JavaEditorPlatform extends JavaPlatform {

	public JavaEditorPlatform(Config config) {
		super(config);
	}

	public static JavaPlatform register() {
		return register(new Config());
	}

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
	
	@Override
	public void run(Game game) {
		super.run(game);
	}

	private static JavaEditorPlatform testInstance;

}
