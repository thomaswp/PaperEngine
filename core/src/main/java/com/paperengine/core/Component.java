package com.paperengine.core;

import com.paperengine.core.Handler.Postable;

import playn.core.util.Clock;

public abstract class Component implements IUpdatable, Postable {
	
	protected GameObject gameObject;
	private final Handler handler = new Handler();
	
	protected Component() { }
	
	public Handler handler() {
		return handler;
	}
	
	public GameObject gameObject() {
		return gameObject;
	}
	
	public void update(float delta) { 
		handler.update();
	}
	
	public void paint(Clock clock) { }
	
	public void updateEditor(float delta) { 
		handler.update();
	}
	
	public void paintEditor(Clock clock) { }
}
