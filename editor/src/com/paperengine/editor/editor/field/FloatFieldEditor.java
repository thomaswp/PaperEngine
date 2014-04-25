package com.paperengine.editor.editor.field;

import org.eclipse.swt.widgets.Composite;

public class FloatFieldEditor extends NumberFieldEditor<Float> {
	
	public FloatFieldEditor(Composite parent, Accessor accessor) {
		super(parent, accessor);
	}

	@Override
	protected String getText() {
		return String.format("%.04f", getValue());
	}

	@Override
	protected boolean setText(String value) {
		try {
			float v = Float.parseFloat(value);
			setValue(v);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
