package com.paperengine.editor.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.paperengine.core.Component;
import com.paperengine.core.Scene;
import com.paperengine.editor.editor.field.FieldEditor;

public abstract class ComponentEditor<T extends Component> extends Composite {
	
	protected T component;
	protected Label nameLabel;
	protected List<FieldEditor<?>> editors = new ArrayList<FieldEditor<?>>();
	
	public ComponentEditor(Composite parent, T component) {
		super(parent, SWT.BORDER);
		RowLayout layout = new RowLayout(SWT.VERTICAL);
		layout.fill = true;
		layout.spacing = 1;
		setLayout(layout);
		this.component = component;
		
		nameLabel = new Label(this, SWT.NONE);
		nameLabel.setText(component.getClass().getSimpleName());
	}

	public static ComponentEditor<?> create(Composite parent, Component component) {
		return new DefaultComponentEditor(parent, component);
	}

	public void update(Scene scene) {
		FieldEditor<?> modified = null;
		for (FieldEditor<?> editor : editors) {
			if (editor.popModified()) modified = editor;
		}
		for (FieldEditor<?> editor : editors) {
			editor.update(scene);
			if (modified != null && editor != modified) {
				editor.updateField();
			}
		}
	}

}
