package com.paperengine.core.physics;

public class CircleCollider extends AbstractCircleCollider {

	public float radius, originX, originY;
	
	@Override
	public float width() {
		return radius * 2;
	}

	@Override
	public float height() {
		return radius * 2;
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
