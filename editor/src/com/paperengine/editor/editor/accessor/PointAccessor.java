package com.paperengine.editor.editor.accessor;

import java.lang.reflect.Field;
import java.security.InvalidParameterException;

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
	public void set(Object value, Runnable callback) {	
		Point point = (Point) super.get();
		if (value == null) {
			throw new InvalidParameterException("Point cannot be null.");
		} else {
			point.set((IPoint) value);
		}
		if (callback != null) callback.run();
	}

	@Override
	public Accessor copyForObject(Object object) {
		return new PointAccessor(field, object);
	}
}
