package com.paperengine.editor.editor.field;

import java.lang.reflect.Field;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.paperengine.core.Component;
import com.paperengine.core.Editor;
import com.paperengine.core.Scene;

public class FloatFieldEditor extends FieldEditor<Float> {
	
	protected Label labelName;
	protected Text textFieldValue;
	protected String name;

	public FloatFieldEditor(Composite parent, Component component, Field field) {
		super(parent, component, field);
		
		name = field.getName();
		labelName = new Label(this, SWT.NONE);
		labelName.setText(name + ": ");
		textFieldValue = new Text(this, SWT.NONE);
		textFieldValue.setText(getText());
		
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
