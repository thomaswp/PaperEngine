package com.paperengine.core.render;

public abstract class SizedRenderer extends Renderer {
	
	protected float width, height;
	protected boolean centered;
	
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
		center();
	}

	public boolean centered() {
		return centered;
	}

	public void setCentered(boolean centered) {
		this.centered = centered;
		center();
	}
	
	@Override
	public void setOrigin(float x, float y) {
		super.setOrigin(x, y);
		setCentered(false);
	}
	
	protected void center() {
		if (centered) {
			super.setOrigin(width / 2, height / 2);
		}
	}
}
