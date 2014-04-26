package com.paperengine.editor.editor.accessor;

import java.lang.reflect.Field;

import pythagoras.f.IPoint;
import pythagoras.f.Point;

public class PointAccessor extends FieldAccessor {

	public PointAccessor(Field field, Object object) {
		super(field, object);
	}
	
	@Override
	public Object get() {
		Point point = (Point) super.get();
		return point.clone();
	}
	
	@Override
	public void set(Object value) {	
		Point point = (Point) super.get();
		point.set((IPoint) value);
	}

	@Override
	public Accessor copyForObject(Object object) {
		return new PointAccessor(field, object);
	}
}
