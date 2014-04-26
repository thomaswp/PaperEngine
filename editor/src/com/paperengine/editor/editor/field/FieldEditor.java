package com.paperengine.editor.editor.field;

import java.lang.reflect.Type;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import pythagoras.f.Point;

import com.paperengine.core.Editor;
import com.paperengine.core.Scene;
import com.paperengine.editor.editor.accessor.Accessor;

public abstract class FieldEditor<T> extends Composite {
	
	protected Accessor accessor;
	private boolean wasPlaying;
	
	public FieldEditor(Composite parent, Accessor accessor) {
		super(parent, SWT.NONE);
		this.accessor = accessor;
	}
	
	public static FieldEditor<?> create(Composite parent, Accessor accessor) {
		Type type = accessor.type();
		if (type == float.class || type == Float.class) {
			return new FloatFieldEditor(parent, accessor); 
		} else if (type == int.class || type == Integer.class) {
			return new IntegerFieldEditor(parent, accessor); 
		} else if (type == boolean.class || type == Boolean.class) {
			return new BooleanFieldEditor(parent, accessor); 
		} else if (type == Point.class) {
			return new PointFieldEditor(parent, accessor); 
		} else if (type instanceof Class<?> && ((Class<?>) type).isEnum()) {
			return new EnumFieldEditor(parent, accessor);
		} else {
			System.out.println("No editor for: " + type.toString());
		}
		return null;
	}

	public void update(Scene scene) {
		if (wasPlaying || Editor.playing) {
			updateField();
		}
		wasPlaying = Editor.playing;
	}
	
	protected void updateField() {
	}
	
	@SuppressWarnings("unchecked")
	protected T getValue() {
		return (T) accessor.get();
	}
	
	protected void setValue(T value) {
		accessor.set(value);
	}
	
	public static String humanReadableField(String name) {
		String hName = "";
		boolean capitalize = true;
		for (char c : name.toCharArray()) {
			if (!Character.isLowerCase(c) && hName.length() > 0) {
				hName += " " + c;
			} else if (capitalize) {
				hName += Character.toUpperCase(c);
				capitalize = false;
			} else {
				hName += c;
				capitalize = false;
			}
		}
		return hName;
	}
}
