package com.paperengine.editor.editor.accessor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pythagoras.f.Point;

public class FieldAccessor implements Accessor {
	protected final Field field;
	protected final Object object;
	
	public FieldAccessor(Field field, Object object) {
		this.field = field;
		this.object = object;
	}

	@Override
	public Object get() {
		try {
			return field.get(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void set(Object value, Runnable callback) {
		try {
			field.set(object, value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (callback != null) callback.run();
		}
	}

	@Override
	public String name() {
		return field.getName();
	}

	@Override
	public Type type() {
		return field.getType();
	}
	
	public static List<FieldAccessor> getForObject(Object object) {
		ArrayList<FieldAccessor> accessors = new ArrayList<FieldAccessor>();
		Field[] fields = object.getClass().getFields();
		for (Field field : fields) {
			if (Modifier.isPublic(field.getModifiers())) {
				Type type = field.getType();
				if (type == Point.class) {
					accessors.add(new PointAccessor(field, object));
				} else if (!Modifier.isFinal(field.getModifiers())) {
					accessors.add(new FieldAccessor(field, object));
				}
			}
		}
		return accessors;
	}

	@Override
	public Accessor copyForObject(Object object) {
		return new FieldAccessor(field, object);
	}

	@Override
	public boolean sameAs(Object value) {
		Object obj = get();
		if (value == null) return value == obj;
		return value.equals(obj);
	}
}
