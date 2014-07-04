package com.paperengine.core;

import static playn.core.PlayN.graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import playn.core.GroupLayer;
import playn.core.Layer;
import playn.core.util.Clock;

import com.paperengine.core.camera.Camera;
import com.paperengine.core.physics.PhysicsBody;
import com.paperengine.core.render.Renderer;

public final class GameObject implements IUpdatable {
	
	private List<GameObject> children = new ArrayList<GameObject>();
	private List<Component> components = new ArrayList<Component>();
	private GameObject parent;
	private boolean enabled = true;
	private Transform transform;
	private Renderer renderer;
	private PhysicsBody physicsBody;
	private Camera camera;
	private String name;
	private Scene scene;
	private int id;
	
	private GroupLayer layer;
	
	public int id() {
		return id;
	}

	protected Scene scene() {
		return scene;
	}
	
	protected void setScene(Scene scene) {
		this.scene = scene;
		this.id = scene.registerGameObject(this);
		for (GameObject child : children) {
			child.setScene(scene);
		}
	}
	
	public String name() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public GameObject parent() {
		return parent;
	}
	
	public Iterable<GameObject> children() {
		return children;
	}

	public Iterable<Component> components() {
		return components;
	}
	
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

	public PhysicsBody physicsBody() {
		return physicsBody;
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
		name = getClass().getSimpleName();
	}
	
	@Override
	public void init() {
		for (Component component : components) {
			component.init();
		}
		for (GameObject child : children) {
			child.init();
		}
	}
	
	public void addComponent(Component component) {
		if (component == null) return;
		if (components.contains(component)) {
			throw new PaperEngineException("GameObject already contains Component " + component);
		}
		components.add(component);
		component.gameObject = this;
		if (component instanceof Renderer) {
			setRenderer((Renderer) component);
		} else if (component instanceof Camera) {
			camera = (Camera) component;
		} else if (component instanceof PhysicsBody) {
			physicsBody = (PhysicsBody) component;
		}
		sortComponents();
	}
	
	private void sortComponents() {
		List<Component> freeNodes = new ArrayList<Component>();
		List<Component> sorted = new ArrayList<Component>();
		HashMap<Component, List<Component>> lessThan = new HashMap<Component, List<Component>>();
		HashMap<Component, List<Component>> greaterThan = new HashMap<Component, List<Component>>();
		for (Component component : components) {
			lessThan.put(component, new ArrayList<Component>());
			greaterThan.put(component, new ArrayList<Component>());
		}
		for (Component component : components) {
			boolean free = true;
			List<Component> gt = greaterThan.get(component);
			for (Component pair : components) {
				if (component == pair) continue;
				if (gt.contains(component)) {
					free = false;
					continue;
				}
				if (component.compareTo(pair) > 0 || pair.compareTo(component) < 0) {
					free = false;
					greaterThan.get(component).add(pair);
					lessThan.get(pair).add(component);
				}
			}
			if (free) freeNodes.add(component);
		}
		while (freeNodes.size() > 0) {
			Component tail = freeNodes.remove(0);
			sorted.add(tail);
			List<Component> outgoing = lessThan.get(tail);
			for (int i = 0; i < outgoing.size(); i++) {
				Component head = outgoing.remove(i--);
				List<Component> incoming = greaterThan.get(head);
				incoming.remove(tail);
				if (incoming.size() == 0) {
					freeNodes.add(head);
				}
			}
		}
		this.components.clear();
		this.components.addAll(sorted);
	}
	
	public boolean removeComponent(Component component) {
		if (component == transform) {
			throw new PaperEngineException("Cannot remove transform");
		} else if (component == renderer) {
			setRenderer(null);
		} else if (component == physicsBody) {
			physicsBody = null;
		} else if (component == camera) {
			camera = null;
		}
		return components.remove(component);
	}
	
	public void addChild(GameObject child) {
		children.add(child);
		child.parent = this;
		layer.add(child.layer());
		if (scene != null) child.setScene(scene);
	}
	
	public boolean removeChild(GameObject child) {
		if (children.remove(child)) {
			child.parent = null;
			layer.remove(child.layer());
			return true;
		}
		return false;
	}
	
	public Iterable<Component> getComponents() {
		return components;
	}
	
	public void update(float delta) {
		for (Component component : components) {
			component.update(delta);
		}
		for (GameObject child : children) {
			if (child.enabled) child.update(delta);
		}
	}
	
	public void paint(Clock clock) {
		for (Component component : components) {
			component.paint(clock);
		}
		transform.setLayer(layer);
		for (GameObject child : children) {
			if (child.enabled) child.paint(clock);
		}
	}

	@Override
	public void updateEditor(float delta) {
		for (Component component : components) {
			component.updateEditor(delta);
		}
		for (GameObject child : children) {
			child.updateEditor(delta);
		}
	}

	@Override
	public void paintEditor(Clock clock) {
		for (Component component : components) {
			component.paintEditor(clock);
		}
		for (GameObject child : children) {
			child.paintEditor(clock);
		}
		transform.setLayer(layer);
	}
	
	public GameObject shallowCopy() {
		GameObject copy = new GameObject();
		copy.enabled = enabled;
		copy.name = name;
		return copy;
	}
}
