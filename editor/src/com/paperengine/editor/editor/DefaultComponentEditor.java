package com.paperengine.editor.editor;

import java.util.List;

import org.eclipse.swt.widgets.Composite;

import com.paperengine.core.Component;
import com.paperengine.editor.editor.accessor.FieldAccessor;
import com.paperengine.editor.editor.accessor.MethodAccessor;
import com.paperengine.editor.editor.field.FieldEditor;

public class DefaultComponentEditor extends ComponentEditor<Component> {

	
	public DefaultComponentEditor(Composite parent, Component component) {
		super(parent, component);
		
		List<FieldAccessor> fieldAccessors = FieldAccessor.getForObject(component);
		for (FieldAccessor accessor : fieldAccessors) {
			FieldEditor<?> editor = FieldEditor.create(this, accessor);
			if (editor != null) {
				editors.add(editor);
			}
		}
		
		List<MethodAccessor> methodAccessors = MethodAccessor.getForObject(component);
		for (MethodAccessor accessor : methodAccessors) {
			FieldEditor<?> editor = FieldEditor.create(this, accessor);
			if (editor != null) {
				editors.add(editor);
			}
		}
	}
}
