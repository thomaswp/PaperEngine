package com.paperengine.core;

import playn.core.util.Clock;

public abstract class Component implements IUpdatable{
	
	protected GameObject gameObject;
	
	public GameObject gameObject() {
		return gameObject;
	}
	
	public void update(float delta) { }
	public void paint(Clock clock) { }
	public void updateEditor(float delta) { }
	public void paintEditor(Clock clock) { }
}
