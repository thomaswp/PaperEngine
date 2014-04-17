package com.paperengine.editor.editor;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.paperengine.core.Component;
import com.paperengine.core.Scene;

public abstract class ComponentEditor<T extends Component> extends JPanel {
	private static final long serialVersionUID = 1L;
	
	protected T component;
	protected JLabel nameLabel;
	
	public ComponentEditor(T component) {
		this.component = component;
		
		setBorder(BorderFactory.createLineBorder(Color.black));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		nameLabel = new JLabel(component.getClass().getSimpleName());
		add(nameLabel);
	}

	public static ComponentEditor<?> create(Component component) {
		return new DefaultComponentEditor(component);
	}

	public void update(Scene scene) {
		
	}

}
