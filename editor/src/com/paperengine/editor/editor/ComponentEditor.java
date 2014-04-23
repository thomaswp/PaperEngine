package com.paperengine.editor.editor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.paperengine.core.Component;
import com.paperengine.core.Scene;

public abstract class ComponentEditor<T extends Component> extends Composite {
	
	protected T component;
	protected Label nameLabel;
	
	public ComponentEditor(Composite parent, T component) {
		super(parent, SWT.NONE);
		this.component = component;
		
		nameLabel = new Label(this, SWT.NONE);
	}

	public static ComponentEditor<?> create(Composite parent, Component component) {
		return new DefaultComponentEditor(parent, component);
	}

	public void update(Scene scene) {
		
	}

}
