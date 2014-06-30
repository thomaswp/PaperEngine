package com.paperengine.core.physics;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;

import playn.core.util.Clock;
import pythagoras.f.Point;

import com.paperengine.core.Scene;
import com.paperengine.core.Transform;

public class BoxCollider extends Collider {

//	public final Point origin = new Point();
	public float width, height;

	private Point lastSize = new Point();
	
	@Override
	public void paint(Clock clock) {
		super.paint(clock);
		if (fixture == null) return;
		
		Transform transform = gameObject().transform();
		if (lastSize.x != width * transform.scaleX || 
				lastSize.y != height * transform.scaleY) {
			createFixture();
		}
	}
	
	@Override
	protected Fixture createFixture() {
		PhysicsBody pBody = gameObject().physicsBody();
		if (pBody == null) return null;
		Body body = pBody.body();
		if (body == null) return null;
		
		if (fixture != null) {
//			body.destroyFixture(fixture);
		}
		
		Transform transform = gameObject().transform();	
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width * transform.scaleX * Scene.PHYSICS_SCALE / 2, 
				height * transform.scaleY * Scene.PHYSICS_SCALE / 2);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = density;
		fixtureDef.restitution = resitiution;
		fixtureDef.friction = friction;
		fixtureDef.shape = shape;

		lastSize.set(width * transform.scaleX, height * transform.scaleY);
		
		return body.createFixture(fixtureDef);
	}
}
