package com.paperengine.editor.editor.field;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FieldAccessor implements Accessor {
	private final Field field;
	private final Object object;
	
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
	public void set(Object value) {
		try {
			field.set(object, value);
		} catch (Exception e) {
			e.printStackTrace();
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
			if ((Modifier.isPublic(field.getModifiers())) && !Modifier.isFinal(field.getModifiers())) {
				accessors.add(new FieldAccessor(field, object));
			}
		}
		return accessors;
	}
}
