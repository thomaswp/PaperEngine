package com.paperengine.core.physics;

import playn.core.util.Clock;
import pythagoras.f.Point;

import com.paperengine.core.Transform;

public abstract class SimpleCollider extends Collider {
	protected final Point lastSize = new Point();
	protected final Point lastOrigin = new Point();
	
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
}
