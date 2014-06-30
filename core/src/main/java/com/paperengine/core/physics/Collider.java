package com.paperengine.core.physics;

import org.jbox2d.dynamics.Fixture;

import com.paperengine.core.Component;

public abstract class Collider extends Component {
	
	public float density = 1, resitiution, friction = 0.2f;
	protected transient Fixture fixture;
	
	protected abstract Fixture createFixture();
	
	@Override
	public void init() {
		super.init();
		fixture = createFixture();
	}
	
	@Override
	public int compareTo(Component o) {
		if (o instanceof PhysicsBody) return 1;
		return super.compareTo(o);
	}
}
