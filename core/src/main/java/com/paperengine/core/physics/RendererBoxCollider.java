package com.paperengine.core.physics;

import org.jbox2d.collision.shapes.Shape;

import playn.core.util.Clock;

import com.paperengine.core.Component;
import com.paperengine.core.render.Renderer;
import com.paperengine.core.render.SizedRenderer;

public class RendererBoxCollider extends AbstractBoxCollider {

	private SizedRenderer renderer;
	
	@Override
	public float width() {
		return renderer == null ? 0 : renderer.width();
	}

	@Override
	public float height() {
		return renderer == null ? 0 : renderer.height();
	}

	@Override
	public float originX() {
		return renderer == null ? 0 : renderer.originX();
	}
	
	@Override
	public float originY() {
		return renderer == null ? 0 : renderer.originY();
	}
	
	@Override
	public void init() {
		Renderer rend = gameObject().renderer();
		if (rend instanceof SizedRenderer) {
			renderer = (SizedRenderer) rend;
		}
		super.init();
	}
	
	@Override
	public void paint(Clock clock) {
		Renderer rend = gameObject().renderer();
		if (rend instanceof SizedRenderer) {
			renderer = (SizedRenderer) rend;
		}
		super.paint(clock);
	}
	
	@Override
	protected Shape createShape() {
		if (renderer == null) return null;
		return super.createShape();
	}
	
	@Override
	public int compareTo(Component o) {
		if (o instanceof SizedRenderer) return 1;
		return super.compareTo(o);
	}
}
