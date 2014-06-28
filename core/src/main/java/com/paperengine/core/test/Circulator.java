package com.paperengine.core.test;

import com.paperengine.core.Component;

public class Circulator extends Component {
	
	float time;
	
	@Override
	public void update(float delta) {
		time += delta / 500;
		gameObject.transform().position.set(30 * (float) Math.sin(time), 
				30 * (float) Math.cos(time));
		gameObject.transform().rotation = time / 50;
	}
}
