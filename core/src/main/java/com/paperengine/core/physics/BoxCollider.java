package com.paperengine.core.physics;


public class BoxCollider extends AbstractBoxCollider {

	//	public final Point origin = new Point();
	public float width, height, originX, originY;

	@Override
	public float width() {
		return width;
	}

	@Override
	public float height() {
		return height;
	}

	@Override
	public float originX() {
		return originX;
	}

	@Override
	public float originY() {
		return originY;
	}
}
