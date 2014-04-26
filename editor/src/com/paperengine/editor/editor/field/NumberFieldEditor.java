package com.paperengine.editor.editor.field;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.paperengine.core.Editor;
import com.paperengine.editor.editor.accessor.Accessor;

public abstract class NumberFieldEditor<T> extends FieldEditor<T> {

	protected Label labelName;
	protected Text textValue;
	private T lastValue;
	
	protected abstract String getText();
	protected abstract boolean setText(String value);

	public NumberFieldEditor(Composite parent, Accessor accessor) {
		super(parent, accessor);
		setLayout(new RowLayout(SWT.HORIZONTAL));
		
		String name = humanReadableField(accessor.name());
		labelName = new Label(this, SWT.NONE);
		labelName.setText(name + ": ");
		textValue = new Text(this, SWT.BORDER);
		textValue.setText(getText());
		
		textValue.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent event) {
				if (setText(textValue.getText())) {
					lastValue = getValue();
				}
			}
		});
		
		textValue.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (!setText(textValue.getText())) {
					setValue(lastValue);
				}
			}
			
			@Override
			public void focusGained(FocusEvent arg0) { 
				lastValue = getValue();
			}
		});
	}

	@Override
	public void updateField() {
		textValue.setEnabled(!Editor.playing);
		textValue.setText(getText());
	}
	

}
