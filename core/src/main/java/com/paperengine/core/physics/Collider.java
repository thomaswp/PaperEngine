package com.paperengine.core.physics;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;

import com.paperengine.core.Component;

public abstract class Collider extends Component {
	
	public float density = 1, resitiution, friction = 0.2f;
	protected transient Fixture fixture;
	
	protected abstract Shape createShape();
	
	@Override
	public void init() {
		super.init();
		createFixture();
	}
	
	protected final void createFixture() {
		PhysicsBody pBody = gameObject().physicsBody();
		if (pBody == null) return;
		Body body = pBody.body();
		if (body == null) return;
		
		if (fixture != null) {
			body.destroyFixture(fixture);
		}
		
		Shape shape = createShape();
		if (shape == null) return;
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = density;
		fixtureDef.restitution = resitiution;
		fixtureDef.friction = friction;
		fixtureDef.shape = shape;

		fixture = body.createFixture(fixtureDef);
	}
	
	@Override
	public int compareTo(Component o) {
		if (o instanceof PhysicsBody) return 1;
		return super.compareTo(o);
	}
}
