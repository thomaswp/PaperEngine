package com.paperengine.editor.editor.field;

import java.lang.reflect.Field;

import javax.swing.JLabel;

import com.paperengine.core.Component;
import com.paperengine.core.Scene;

public class FloatFieldEditor extends FieldEditor<Float> {
	private static final long serialVersionUID = 1L;
	
	protected JLabel labelName;
	protected String name;

	public FloatFieldEditor(Component component, Field field) {
		super(component, field);
		
		name = field.getName();
		labelName = new JLabel(name + ": " + getValue());
		add(labelName);
		
	}

	@Override
	public void update(Scene scene) {
		labelName.setText(name + ": " + getValue());
		System.out.println(getValue());
	}
}
