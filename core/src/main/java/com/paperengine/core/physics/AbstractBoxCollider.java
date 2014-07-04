package com.paperengine.core.physics;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;

import playn.core.util.Clock;
import pythagoras.f.Point;

import com.paperengine.core.Scene;
import com.paperengine.core.Transform;

public abstract class AbstractBoxCollider extends Collider {
	
	private Point lastSize = new Point();
	private Point lastOrigin = new Point();
	
	public abstract float width();
	public abstract float height();
	public abstract float originX();
	public abstract float originY();

	@Override
	public void paint(Clock clock) {
		super.paint(clock);

		Transform transform = gameObject().transform();
		if (lastSize.x != width() * transform.scaleX || 
				lastSize.y != height() * transform.scaleY ||
				lastOrigin.x != originX() || 
				lastOrigin.y != originY()) {
			
			createFixture();
		}
		if (fixture == null) return;
	}

	@Override
	protected Shape createShape() {
		if (width() * height() == 0) return null;
		Transform transform = gameObject().transform();	
		PolygonShape shape = new PolygonShape();
		float dw = width() * transform.scaleX * Scene.PHYSICS_SCALE / 2;
		float dh = height() * transform.scaleY * Scene.PHYSICS_SCALE / 2;
		Vec2 center = new Vec2(-originX() * transform.scaleX * Scene.PHYSICS_SCALE  + dw, 
				-originY() * transform.scaleY * Scene.PHYSICS_SCALE + dh);
		shape.setAsBox(dw, dh, center, 0);
		lastSize.set(width() * transform.scaleX, height() * transform.scaleY);
		lastOrigin.set(originX(), originY());
		return shape;
	}
}
