package com.paperengine.core;

import pythagoras.f.Point;

public class Transform extends Component {
	public final Point position = new Point();
	public float rotation;
	public float depth;
	public float scaleX = 1, scaleY = 1;
}
