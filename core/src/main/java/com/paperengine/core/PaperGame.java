package com.paperengine.core;

import static playn.core.PlayN.graphics;
import static playn.core.PlayN.mouse;
import playn.core.Game;
import playn.core.util.Clock;

import com.paperengine.core.editor.EditorLayer;

public class PaperGame extends Game.Default {

	private Scene scene;
	private Clock.Source clock;
	private Runnable callback;
	private EditorLayer editorLayer;
	
	public Scene scene() {
		return scene;
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
		editorLayer.reset();
		editorLayer.setScene(scene);
		mouse().setListener(editorLayer);
		graphics().rootLayer().removeAll();
		if (scene != null) {
			graphics().rootLayer().add(scene.layer());
			scene.init();
		}
		graphics().rootLayer().add(editorLayer.layer());
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
		editorLayer = new EditorLayer();
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
			if (Editor.updateEditor()) {
				scene.updateEditor(delta);
				editorLayer.update(delta);
			}
			if (Editor.updateGame()) scene.update(delta);
		}
	}

	@Override
	public void paint(float alpha) {
		clock.paint(alpha);
		editorLayer.layer().setVisible(Editor.updateEditor());
		if (scene != null) {
			if (Editor.updateEditor()) {
				scene.paintEditor(clock);
				editorLayer.paint(clock);
			}
			if (Editor.updateGame()) scene.paint(clock);
		}
	}
}
