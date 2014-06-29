package com.paperengine.core;

import pythagoras.f.Point;

public class Transform extends Component {
	public final Point position = new Point();
	public float rotation;
	public float depth;
	public float scaleX = 1, scaleY = 1;
	
	public Data createData() {
		return new Data().set(this);
	}
	
	public Transform set(Data data) {
		position.set(data.position);
		rotation = data.rotation;
		depth = data.depth;
		scaleX = data.scaleX;
		scaleY = data.scaleY;
		return this;
	}
	
	public static class Data {
		public final Point position = new Point();
		public float rotation;
		public float depth;
		public float scaleX = 1, scaleY = 1;
		
		public Data set(Transform transform) {
			position.set(transform.position);
			rotation = transform.rotation;
			depth = transform.depth;
			scaleX = transform.scaleX;
			scaleY = transform.scaleY;
			return this;
		}
		
		public boolean equals(Data data) {
			return position.equals(data.position) &&
					rotation == data.rotation &&
					depth == data.depth &&
					scaleX == data.scaleX &&
					scaleY == data.scaleY;
		}
		
		public boolean equals(Transform transform) {
			return position.equals(transform.position) &&
					rotation == transform.rotation &&
					depth == transform.depth &&
					scaleX == transform.scaleX &&
					scaleY == transform.scaleY;
		}
	}
}
