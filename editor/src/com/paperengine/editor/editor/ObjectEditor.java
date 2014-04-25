package com.paperengine.editor.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.paperengine.core.Component;
import com.paperengine.core.GameObject;
import com.paperengine.core.Scene;

public class ObjectEditor extends Composite {
	
	Label labelName;
	List<ComponentEditor<?>> editors = new ArrayList<ComponentEditor<?>>();
	
	public ObjectEditor(Composite parent, int flags) {
		super(parent, flags);
		
		labelName = new Label(this, SWT.NONE);
	}
	
	public void loadObject(GameObject object) {
		for (ComponentEditor<?> editor : editors) {
			editor.dispose();
		}
		
		if (object == null) {
			labelName.setText("");
		} else {
			labelName.setText(object.name());
			
			for (ComponentEditor<?> editor : editors) {
				editor.dispose();
			}
			editors.clear();
			for (Component component : object.components()) {
				ComponentEditor<?> editor = ComponentEditor.create(this, component);
				editors.add(editor);
			}
		}
		
		this.layout();
	}

	public void update(Scene scene) {
		for (ComponentEditor<?> editor : editors) {
			editor.update(scene);
		}
	}

}
