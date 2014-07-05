package com.paperengine.core;

import playn.core.util.Clock;

import com.paperengine.core.Handler.Postable;

public abstract class Component implements IUpdatable, Postable, Comparable<Component> {
	
	GameObject gameObject;
	private final Handler handler = new Handler();
	
	protected Component() { }
	
	public Handler handler() {
		return handler;
	}
	
	public GameObject gameObject() {
		return gameObject;
	}
	
	protected Scene scene() {
		return gameObject.scene();
	}
	
	public void init() { }
	
	public void update(float delta) { 
		handler.update();
	}
	
	public void paint(Clock clock) { }
	
	public void updateEditor(float delta) {
		handler.update();
	}
	
	public void paintEditor(Clock clock) { }
	
	@Override
	public int compareTo(Component o) {
		return 0;
	}

	public boolean draggableInEditor() {
		return true;
	}
}
