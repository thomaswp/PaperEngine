package com.paperengine.editor.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.paperengine.core.Component;
import com.paperengine.core.Editor;
import com.paperengine.core.GameObject;
import com.paperengine.core.Scene;

public class ObjectEditor extends Composite {
	
	Text textName;
	List<ComponentEditor<?>> editors = new ArrayList<ComponentEditor<?>>();
	GameObject object;
	
	public ObjectEditor(Composite parent, int flags) {
		super(parent, flags);
		
		textName = new Text(this, SWT.BORDER);
	}
	
	public void loadObject(GameObject object) {
		for (ComponentEditor<?> editor : editors) {
			editor.dispose();
		}
		editors.clear();
		this.object = object;
		
		if (object == null) {
			textName.setText("");
		} else {
			textName.setText(object.name());
			for (Component component : object.components()) {
				ComponentEditor<?> editor = ComponentEditor.create(this, component);
				editors.add(editor);
			}
		}
		
		this.layout();
	}

	public void update(Scene scene) {
		textName.setEnabled(!Editor.playing);
		textName.setVisible(object != null);
		for (ComponentEditor<?> editor : editors) {
			editor.update(scene);
		}
	}

}
