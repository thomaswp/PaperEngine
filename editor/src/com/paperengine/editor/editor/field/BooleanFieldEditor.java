package com.paperengine.editor.editor.field;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.paperengine.core.Editor;
import com.paperengine.editor.editor.accessor.Accessor;

public class BooleanFieldEditor extends FieldEditor<Boolean> {

	private Button buttonValue;
	
	public BooleanFieldEditor(Composite parent, Accessor accessor) {
		super(parent, accessor);
		setLayout(new RowLayout(SWT.HORIZONTAL));
		
		buttonValue = new Button(this, SWT.CHECK);
		buttonValue.setText(humanReadableField(accessor.name()));
		
		buttonValue.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setValue(buttonValue.getSelection());
			}
		});
		
		updateField();
	}
	
	@Override
	public void updateField() {
		buttonValue.setEnabled(!Editor.playing);
		buttonValue.setSelection(getValue());
	}

}
