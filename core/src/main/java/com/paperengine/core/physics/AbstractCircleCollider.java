package com.paperengine.core.physics;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;

import com.paperengine.core.Scene;
import com.paperengine.core.Transform;

public abstract class AbstractCircleCollider extends SimpleCollider {
	@Override
	protected Shape createShape() {
		if (width() * height() == 0) return null;
		Transform transform = gameObject().transform();	
		CircleShape shape = new CircleShape();
		float dw = width() * transform.scaleX * Scene.PHYSICS_SCALE / 2;
		float dh = height() * transform.scaleY * Scene.PHYSICS_SCALE / 2;
		float radius = Math.min(dw, dh);
		Vec2 center = new Vec2(-originX() * transform.scaleX * Scene.PHYSICS_SCALE  + dw, 
				-originY() * transform.scaleY * Scene.PHYSICS_SCALE + dh);
		shape.m_p.set(center);
		shape.m_radius = radius;
		lastSize.set(width() * transform.scaleX, height() * transform.scaleY);
		lastOrigin.set(originX(), originY());
		return shape;
	}
}
