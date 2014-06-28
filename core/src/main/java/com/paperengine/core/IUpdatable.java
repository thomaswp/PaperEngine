package com.paperengine.core;

import playn.core.util.Clock;

public interface IUpdatable {
	
	void init();
	void update(float delta);
	void paint(Clock clock);
	void updateEditor(float delta);
	void paintEditor(Clock clock);
}
