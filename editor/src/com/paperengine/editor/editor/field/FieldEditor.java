package com.paperengine.editor.editor.field;

import java.lang.reflect.Field;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.paperengine.core.Component;
import com.paperengine.core.Editor;
import com.paperengine.core.Scene;

public abstract class FieldEditor<T> extends Composite {
	
	protected Component component;
	protected Field field;
	private boolean wasPlaying;
	
	public FieldEditor(Composite parent, Component component, Field field) {
		super(parent, SWT.NONE);
		this.component = component;
		this.field = field;
	}
	
	public static FieldEditor<?> create(Composite parent, Component component, Field field) {
		if (field.getType() == float.class || field.getType() == Float.class) {
			return new FloatFieldEditor(parent, component, field); 
		}
		return null;
	}

	public void update(Scene scene) {
		if (wasPlaying || Editor.playing) {
			updateField(scene);
		}
		wasPlaying = Editor.playing;
	}
	
	protected void updateField(Scene scene) {
		
	}
	
	@SuppressWarnings("unchecked")
	protected T getValue() {
		try {
			return (T) field.get(component);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String humanReadableField(String name) {
		String hName = "";
		boolean capitalize = true;
		for (char c : name.toCharArray()) {
			if (!Character.isLowerCase(c) && hName.length() > 0) {
				hName += " " + c;
				capitalize = true;
			} else if (capitalize) {
				hName += Character.toUpperCase(c);
				capitalize = false;
			} else {
				hName += c;
			}
		}
		return hName;
	}
}
