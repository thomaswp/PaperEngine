package com.paperengine.editor.editor.field;

import java.lang.reflect.Field;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.paperengine.core.Component;
import com.paperengine.core.Editor;
import com.paperengine.core.Scene;

public class FloatFieldEditor extends FieldEditor<Float> {
	
	protected Label labelName;
	protected Text textFieldValue;

	public FloatFieldEditor(Composite parent, Component component, Field field) {
		super(parent, component, field);
		setLayout(new RowLayout(SWT.HORIZONTAL));
		
		String name = humanReadableField(field.getName());
		labelName = new Label(this, SWT.NONE);
		labelName.setText(name + ": ");
		textFieldValue = new Text(this, SWT.BORDER);
		textFieldValue.setText(getText());
		
	}

	@Override
	public void updateField(Scene scene) {
		super.updateField(scene);
		textFieldValue.setEnabled(!Editor.playing);
		textFieldValue.setText(getText());
	}
	
	private String getText() {
		return String.format("%.04f",getValue());
	}
}
