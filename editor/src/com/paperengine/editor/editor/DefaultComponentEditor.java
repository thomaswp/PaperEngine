package com.paperengine.editor.editor;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.paperengine.core.Component;
import com.paperengine.editor.editor.accessor.Accessor;
import com.paperengine.editor.editor.accessor.FieldAccessor;
import com.paperengine.editor.editor.accessor.MethodAccessor;
import com.paperengine.editor.editor.field.FieldEditor;

public class DefaultComponentEditor extends ComponentEditor<Component> {

	
	public DefaultComponentEditor(Composite parent, Component component) {
		super(parent, component);
		
		List<FieldAccessor> fieldAccessors = FieldAccessor.getForObject(component);
		for (FieldAccessor accessor : fieldAccessors) {
			addEditor(accessor);
		}
		
		List<MethodAccessor> methodAccessors = MethodAccessor.getForObject(component);
		for (MethodAccessor accessor : methodAccessors) {
			addEditor(accessor);
		}
	}

	private void addEditor(Accessor accessor) {
		Label label = new Label(this, SWT.NONE);
		label.setText(humanReadableField(accessor.name()));
		FieldEditor<?> editor = FieldEditor.create(this, accessor);
		if (editor != null) {
			editors.add(editor);
		} else {
			label.dispose();
		}
	}
	
	public static String humanReadableField(String name) {
		String hName = "";
		boolean capitalize = true;
		for (char c : name.toCharArray()) {
			if (!Character.isLowerCase(c) && hName.length() > 0) {
				hName += " " + c;
			} else if (capitalize) {
				hName += Character.toUpperCase(c);
				capitalize = false;
			} else {
				hName += c;
				capitalize = false;
			}
		}
		return hName;
	}
}
