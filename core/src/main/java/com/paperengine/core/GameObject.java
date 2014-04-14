package com.paperengine.core;

import java.util.ArrayList;
import java.util.List;

import static playn.core.PlayN.*;
import playn.core.GroupLayer;
import playn.core.Layer;
import playn.core.util.Clock;
import pythagoras.f.Point;

import com.paperengine.core.camera.Camera;
import com.paperengine.core.render.Renderer;

public class GameObject implements IUpdatable {
	
	private List<Component> components = new ArrayList<Component>();
	private boolean enabled = true;
	private Transform transform;
	private Renderer renderer;
	private Camera camera;
	
	private GroupLayer layer;
	
	public Transform transform() {
		return transform;
	}
	
	public Layer layer() {
		return layer;
	}
	
	public Renderer renderer() {
		return renderer;
	}

	private void setRenderer(Renderer component) {
		if (this.renderer != null) layer.remove(renderer.layer());
		this.renderer = component;
		layer.add(renderer.layer());
	}
	
	public Camera camera() {
		return camera;
	}
	
	public boolean enabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		layer.setVisible(this.enabled);
	}
	
	public float x() {
		return transform.position.x;
	}
	
	public float y() {
		return transform.position.y;
	}
	
	public float rotation() {
		return transform.rotation;
	}
	
	public float depth() {
		return transform.depth;
	}

	public float scaleX() {
		return transform.scaleX;
	}
	
	public float scaleY() {
		return transform.scaleY;
	}
	
	public GameObject() {
		components.add(transform = new Transform());
		layer = graphics().createGroupLayer();
	}
	
	public void addComponent(Component component) {
		if (component == null) return;
		this.components.add(component);
		component.gameObject = this;
		if (component instanceof Renderer) {
			setRenderer((Renderer) component);
		} else if (component instanceof Camera) {
			camera = (Camera) component;
		}
	}
	
	public boolean removeComponent(Component component) {
		if (component == transform) {
			throw new PaperEngineException("Cannot remove transform");
		} else if (component == renderer) {
			setRenderer(null);
		}
		return components.remove(component);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Component> T getCompoment() {
		for (Component component : components) {
			try {
				return (T) component;
			} catch (Exception e) { }
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Component> List<T> getCompoments() {
		List<T> list = new ArrayList<T>();
		for (Component component : components) {
			try {
				list.add((T) component);
			} catch (Exception e) { }
		}
		return list;
	}
	
	public void update(float delta) {
		for (Component component : components) {
			component.update(delta);
		}
	}
	
	public void paint(Clock clock) {
		for (Component component : components) {
			component.paint(clock);
		}
		updateTransform();
	}

	@Override
	public void updateEditor(float delta) {
		for (Component component : components) {
			component.updateEditor(delta);
		}
	}

	@Override
	public void paintEditor(Clock clock) {
		for (Component component : components) {
			component.paintEditor(clock);
		}
		updateTransform();
	}
	
	private void updateTransform() {
		Point position = transform.position;
		layer.setTranslation(position.x, position.y);
		layer.setRotation(transform.rotation);
		layer.setScaleX(transform.scaleX);
		layer.setScaleY(transform.scaleY);
		
	}
}
