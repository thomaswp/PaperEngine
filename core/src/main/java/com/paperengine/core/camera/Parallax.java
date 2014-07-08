package com.paperengine.core.camera;

import playn.core.Layer;
import playn.core.util.Clock;
import pythagoras.f.Point;

import com.paperengine.core.Component;

public class Parallax extends Component {
	
	public float moveXFactor = 0.7f, moveYFactor = 0.7f;
	
	private final Point startPosition = new Point(), lastPosition = new Point();
	
	@Override
	public void init() {
		super.init();
		startPosition.set(gameObject().transform().position);
		lastPosition.set(startPosition);
	}
	
	@Override
	public void paint(Clock clock) {
		super.paint(clock);
		Point currentPos = gameObject().transform().position; 
		if (!currentPos.equals(lastPosition)) {
			startPosition.subtractLocal(lastPosition.x - currentPos.x, lastPosition.y - currentPos.y);
		}
		
		currentPos.set(0, 0);
		Layer.Util.screenToLayer(gameObject().layer().parent(), currentPos, currentPos);
		currentPos.x = (currentPos.x - startPosition.x) * (1 - moveXFactor) + startPosition.x;
		currentPos.y = (currentPos.y - startPosition.y) * (1 - moveYFactor) + startPosition.y;
		
		lastPosition.set(currentPos);
	}
}
