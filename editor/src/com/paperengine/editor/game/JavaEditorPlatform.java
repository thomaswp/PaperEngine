package com.paperengine.editor.game;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

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
	boolean dispose;

	public void dispose() {
		dispose = true;
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


	@Override
	public void run(final Game game) {
		try {
			Display.create();
		} catch (LWJGLException e) {
			throw new RuntimeException(e);
		}
		init(game);

		boolean wasActive = Display.isActive();
		while (!Display.isCloseRequested() && !dispose) {
			// Notify the app if lose or regain focus (treat said as pause/resume).
			boolean newActive = Display.isActive();
			if (wasActive != newActive) {
				if (wasActive)
					onPause();
				else
					onResume();
				wasActive = newActive;
			}
			processFrame(game);
			Display.update();
			// Sleep until it's time for the next frame.
			Display.sync(60);
		}
		System.out.println("!");

		shutdown();
		System.out.println("!!");
	}
	
	protected void shutdown() {
		// let the game run any of its exit hooks
		onExit();

		// shutdown our thread pool
//		try {
//			_exec.shutdown();
//			_exec.awaitTermination(1, TimeUnit.SECONDS);
//		} catch (InterruptedException ie) {
//			// nothing to do here except go ahead and exit
//		}

		// and finally stick a fork in the JVM
		System.exit(0);
	}
}
