package com.paperengine.editor.editor.field;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.paperengine.editor.editor.accessor.Accessor;

public class ColorFieldEditor extends FieldEditor<Integer> {

	private int color;
	private Label colorLabel;
	
	public ColorFieldEditor(Composite parent, Accessor accessor) {
		super(parent, accessor);
		
	}

	@Override
	protected Integer getUI() {
		return color;
	}

	@Override
	protected void setEnabledLocal(boolean enabled) {
		colorLabel.setEnabled(enabled);
	}

}
