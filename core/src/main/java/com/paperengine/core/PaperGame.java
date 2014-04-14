package com.paperengine.core;

import static playn.core.PlayN.*;
import playn.core.Game;
import playn.core.util.Clock;

public class PaperGame extends Game.Default {

	private Scene scene;
	private Clock.Source clock;
	private Runnable initCallback;
	
	public Scene scene() {
		return scene;
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
		graphics().rootLayer().removeAll();
		if (scene != null) {
			graphics().rootLayer().add(scene.layer());
		}
	}
	
	public void setInitCallback(Runnable initCallback) {
		this.initCallback = initCallback;
	}
	
	public PaperGame() {
		super(16);
	}

	@Override
	public void init() {
		clock = new Clock.Source(16);
		if (this.initCallback != null) {
			this.initCallback.run();
			this.initCallback = null;
		}
	}

	@Override
	public void update(int delta) {
		clock.update(delta);
		if (scene != null) {
			if (Editor.editing) scene.updateEditor(delta); 
			else scene.update(delta);
		}
	}

	@Override
	public void paint(float alpha) {
		clock.paint(alpha);
		if (scene != null) {
			if (Editor.editing) scene.paintEditor(clock);
			else scene.paint(clock);
		}
	}
}
