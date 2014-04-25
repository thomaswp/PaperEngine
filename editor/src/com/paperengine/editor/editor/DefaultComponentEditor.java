package com.paperengine.editor.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;

import com.paperengine.core.Component;
import com.paperengine.core.Scene;
import com.paperengine.editor.editor.field.FieldAccessor;
import com.paperengine.editor.editor.field.FieldEditor;
import com.paperengine.editor.editor.field.MethodAccessor;

public class DefaultComponentEditor extends ComponentEditor<Component> {

	private List<FieldEditor<?>> editors = new ArrayList<FieldEditor<?>>();
	
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

	@Override
	public void update(Scene scene) {
		for (FieldEditor<?> editor : editors) {
			editor.update(scene);
		}
	}
}
