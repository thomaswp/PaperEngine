package com.paperengine.core.render;

import playn.core.Layer;

import com.paperengine.core.Component;

public abstract class Renderer extends Component {

	protected float width, height;
	
	public float width() {
		return width;
	}
	
	public float height() {
		return height;
	}
	
	public final void setWidth(float width) {
		setSize(width, height);
	}
	
	public final void setHeight(float height) {
		setSize(width, height);
	}
	
	public void setSize(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
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

}
