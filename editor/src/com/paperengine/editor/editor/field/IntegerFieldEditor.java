package com.paperengine.editor.editor.field;

import org.eclipse.swt.widgets.Composite;

public class IntegerFieldEditor extends NumberFieldEditor<Integer> {

	public IntegerFieldEditor(Composite parent, Accessor accessor) {
		super(parent, accessor);
	}

	@Override
	protected String getText() {
		return String.valueOf(getValue());
	}

	@Override
	protected boolean setText(String value) {
		try {
			int v = Integer.parseInt(value);
			setValue(v);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
