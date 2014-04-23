package com.paperengine.editor.editor.field;

import java.awt.Dimension;
import java.lang.reflect.Field;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.paperengine.core.Component;
import com.paperengine.core.Editor;
import com.paperengine.core.Scene;

public class FloatFieldEditor extends FieldEditor<Float> {
	private static final long serialVersionUID = 1L;
	
	protected JLabel labelName;
	protected JTextField textFieldValue;
	protected String name;

	public FloatFieldEditor(Component component, Field field) {
		super(component, field);
		
		name = field.getName();
		labelName = new JLabel(name + ": ");
		textFieldValue = new JTextField(getText());
		textFieldValue.setMinimumSize(new Dimension(100, textFieldValue.getHeight()));
		add(labelName);
		add(textFieldValue);
		
	}

	@Override
	public void update(Scene scene) {
		textFieldValue.setEnabled(!Editor.playing);
		if (Editor.playing) {
			textFieldValue.setText(getText());
		}
	}
	
	private String getText() {
		return String.format("%.04f",getValue());
	}
}
