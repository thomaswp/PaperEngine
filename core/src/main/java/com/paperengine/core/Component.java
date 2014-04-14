package com.paperengine.core;

import playn.core.util.Clock;

public abstract class Component {
	
	public void update(float delta) { }
	public void paint(Clock clock) { }
}
