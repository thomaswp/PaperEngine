package com.paperengine.editor.editor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import com.paperengine.core.Component;
import com.paperengine.core.Scene;
import com.paperengine.editor.editor.field.FieldEditor;

public class DefaultComponentEditor extends ComponentEditor<Component> {
	private static final long serialVersionUID = 1L;

	private List<FieldEditor<?>> editors = new ArrayList<FieldEditor<?>>();
	
	public DefaultComponentEditor(Component component) {
		super(component);
		
		Field[] fields = component.getClass().getFields();
		for (Field field : fields) {
			if ((Modifier.isPublic(field.getModifiers()))) {
				FieldEditor<?> editor = FieldEditor.create(component, field);
				if (editor != null) {
					add(editor);
					editors.add(editor);
				}
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
