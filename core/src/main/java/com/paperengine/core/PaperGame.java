package com.paperengine.core;

import static playn.core.PlayN.*;
import playn.core.Game;
import playn.core.util.Clock;

public class PaperGame extends Game.Default {

	private Scene scene;
	private Clock.Source clock;
	private Runnable callback;
	
	public Scene scene() {
		return scene;
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
		graphics().rootLayer().removeAll();
		if (scene != null) {
			graphics().rootLayer().add(scene.layer());
		}
		mouse().setListener(scene);
	}
	
	public void post(Runnable callback) {
		this.callback = callback;
	}
	
	public PaperGame() {
		super(16);
	}

	@Override
	public void init() {
		clock = new Clock.Source(16);
		if (this.callback != null) {
			this.callback.run();
			this.callback = null;
		}
	}

	@Override
	public void update(int delta) {
		if (this.callback != null) {
			this.callback.run();
			this.callback = null;
		}
		clock.update(delta);
		if (scene != null) {
			if (Editor.updateEditor()) scene.updateEditor(delta); 
			if (Editor.updateGame()) scene.update(delta);
		}
	}

	@Override
	public void paint(float alpha) {
		clock.paint(alpha);
		if (scene != null) {
			if (Editor.updateEditor()) scene.paintEditor(clock);
			if (Editor.updateGame()) scene.paint(clock);
		}
	}
}
