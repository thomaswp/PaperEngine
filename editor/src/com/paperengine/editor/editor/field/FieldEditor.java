package com.paperengine.editor.editor.field;

import java.lang.reflect.Field;

import javax.swing.JPanel;

import com.paperengine.core.Component;
import com.paperengine.core.Scene;

public abstract class FieldEditor<T> extends JPanel {
	private static final long serialVersionUID = 1L;
	
	protected Component component;
	protected Field field;
	
	public FieldEditor(Component component, Field field) {	
		this.component = component;
		this.field = field;
	}
	
	public static FieldEditor<?> create(Component component, Field field) {
		if (field.getType() == float.class || field.getType() == Float.class) {
			return new FloatFieldEditor(component, field); 
		}
		return null;
	}

	public void update(Scene scene) {
		
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
}
