package com.paperengine.editor.editor.field;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.paperengine.core.Editor;
import com.paperengine.editor.editor.accessor.Accessor;

public class EnumFieldEditor extends FieldEditor<Enum<?>> {

	protected Label labelName;
	protected Combo comboValue;
	protected Enum<?>[] constants;
	
	@SuppressWarnings("unchecked")
	public EnumFieldEditor(Composite parent, Accessor accessor) {
		super(parent, accessor);
		setLayout(new RowLayout(SWT.HORIZONTAL));
		
		String name = humanReadableField(accessor.name());
		labelName = new Label(this, SWT.NONE);
		labelName.setText(name + ": ");
		
		comboValue = new Combo(this, SWT.NONE);
		constants = ((Class<Enum<?>>) accessor.type()).getEnumConstants();
		String[] values = new String[constants.length];
		for (int i = 0; i < constants.length; i++) {
			values[i] = constants[i].name();
		}
		comboValue.setItems(values);
		comboValue.select(getIndex());
		
		comboValue.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setIndex(comboValue.getText());
			}
		});
	}

	private int getIndex() {
		Enum<?> value = getValue();
		for (int i = 0; i < constants.length; i++) {
			if (value == constants[i]) return i;
		}
		return -1;
	}
	
	private void setIndex(String text) {
		if (text == null) return;
		for (int i = 0; i < constants.length; i++) {
			if (text.equals(constants[i].name())) {
				setValue(constants[i]);
				return;
			}
		}
	}
	
	@Override
	public void updateFieldLocal() {
		comboValue.setEnabled(!Editor.playing);
		comboValue.select(getIndex());
	}
}
