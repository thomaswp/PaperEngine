package com.paperengine.editor.editor.field;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import com.paperengine.editor.editor.accessor.Accessor;

public class EnumFieldEditor extends FieldEditor<Enum<?>> {

	protected Combo comboValue;
	protected Enum<?>[] constants;
	
	@SuppressWarnings("unchecked")
	public EnumFieldEditor(Composite parent, Accessor accessor) {
		super(parent, accessor);
		setLayout(new RowLayout(SWT.HORIZONTAL));
		
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
		if (comboValue.isFocusControl()) return;
		comboValue.select(getIndex());
	}

	@Override
	protected Enum<?> getUI() {
		return constants[comboValue.getSelectionIndex()];
	}

	@Override
	protected void setEnabledLocal(boolean enabled) {
		comboValue.setEnabled(enabled);
	}
}
