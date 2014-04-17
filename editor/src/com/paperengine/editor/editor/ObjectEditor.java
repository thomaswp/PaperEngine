package com.paperengine.editor.editor;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.paperengine.core.Component;
import com.paperengine.core.GameObject;
import com.paperengine.core.Scene;

public class ObjectEditor extends JPanel {
	private static final long serialVersionUID = 1L;
	
	JLabel labelName;
	List<ComponentEditor<?>> editors = new ArrayList<ComponentEditor<?>>();
	
	public ObjectEditor() {
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);
		
		labelName = new JLabel();
	}
	
	public void loadObject(GameObject object) {
		removeAll();
		
		if (object == null) {
			labelName.setText("");
			add(labelName);
		} else {
			labelName.setText(object.name());
			add(labelName);
			
			editors.clear();
			for (Component component : object.components()) {
				ComponentEditor<?> editor = ComponentEditor.create(component);
				add(editor);
				editors.add(editor);
			}
		}
		
		this.setVisible(false);
		this.setVisible(true);
	}

	public void update(Scene scene) {
		for (ComponentEditor<?> editor : editors) {
			editor.update(scene);
		}
	}

}
