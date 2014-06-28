package com.paperengine.core.test;

import playn.core.Color;

import com.paperengine.core.Component;

public class Colorizor extends Component {

	double time;
	
	@Override
	public void update(float delta) {
		time += delta / 10;
		super.update(delta);
		if (gameObject.renderer() != null) {
			gameObject().renderer().tintColor = Color.argb((int)(time % 255), 255, (int)(time % 255), 255);
		}
	}
	
}
