package com.paperengine.editor.editor.field;

import java.lang.reflect.Field;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.paperengine.core.Component;
import com.paperengine.core.Scene;

public abstract class FieldEditor<T> extends Composite {
	
	protected Component component;
	protected Field field;
	
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
