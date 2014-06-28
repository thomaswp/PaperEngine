package com.paperengine.core.render;

import playn.core.Layer;
import tripleplay.util.Colors;

import com.paperengine.core.Component;

public abstract class Renderer extends Component {
	
	public int tintColor = Colors.WHITE;
	
	public float originX() {
		return layer().originX();
	}
	
	public float originY() {
		return layer().originY();
	}
	
	public void setOrigin(float x, float y) {
		layer().setOrigin(x, y);
	}
	
	public void setOriginX(float x) {
		setOrigin(x, originY());
	}
	
	public void setOriginY(float y) {
		setOrigin(originX(), y);
	}
	
	public abstract Layer layer();

	@Override
	public void update(float delta) {
		super.update(delta);
		layer().setTint(tintColor);
	}
	
	@Override
	public void updateEditor(float delta) {
		super.updateEditor(delta);
		layer().setInteractive(true);
		layer().setTint(tintColor);
	}
}
