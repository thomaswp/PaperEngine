package com.paperengine.core;

import static playn.core.PlayN.graphics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import playn.core.GroupLayer;
import playn.core.Layer;
import playn.core.util.Clock;
import pythagoras.f.Point;

import com.paperengine.core.camera.Camera;
import com.paperengine.core.editor.EditorLayer;

public class Scene implements IUpdatable, Serializable {
	private static final long serialVersionUID = 1L;
	
	public final static float PHYSICS_SCALE = 0.1f;
	
	private List<GameObject> gameObjects = new ArrayList<GameObject>();
	private List<GameObject> allGameObjects = new ArrayList<GameObject>();
	private GroupLayer layer; 
	private int nextObjectId = 0;
	private transient World physicsWorld;
	
	public Layer layer() {
		return layer;
	}
	
	public World physicsWorld() {
		return physicsWorld;
	}
	
	public Iterable<GameObject> gameObjects() {
		return gameObjects;
	}
	
	public Scene() {
		layer = graphics().createGroupLayer();
	}

	@Override
	public void init() {
		if (physicsWorld == null) physicsWorld = new World(new Vec2(0, 10));
		for (GameObject object : gameObjects) {
			object.init();
		}
	}
	
	public void addGameObject(GameObject gameObject) {
		if (gameObjects.contains(gameObject)) return;
		for (int i = 0; i <= gameObjects.size(); i++) {
			if (i == gameObjects.size() || gameObjects.get(i).depth() > gameObject.depth()) {
				gameObjects.add(i, gameObject);
				break;
			}
		}
		layer.add(gameObject.layer());
		gameObject.setScene(this);
	}

	protected int registerGameObject(GameObject gameObject) {
		if (allGameObjects.contains(gameObject)) return gameObject.id();
		allGameObjects.add(gameObject);
		return nextObjectId++;
	}
	
	public Layer hitTest(Point point) {
		return layer.hitTest(point);
	}
	
	public GameObject getObjectById(int id) {
		for (GameObject object : allGameObjects) {
			if (object.id() == id) return object;
		}
		return null;
	}
	
	public void update(float delta) {
		for (GameObject gameObject : gameObjects) {
			if (gameObject.enabled()) {
				gameObject.update(delta);
			}
		}
		physicsWorld.step(delta / 1000, 5, 5);
	}
	
	public void paint(Clock clock) {
		for (GameObject gameObject : gameObjects) {
			if (gameObject.enabled()) {
				gameObject.paint(clock);
			}
		}
		updateTransform();
	}
	
	public void updateEditor(float delta) {
		for (GameObject gameObject : gameObjects) {
			gameObject.updateEditor(delta);
		}
	}
	
	public void paintEditor(Clock clock) {
		for (GameObject gameObject : gameObjects) {
			gameObject.paintEditor(clock);
		}
		updateTransform();
		EditorLayer.get().setSceneTransform(layer);
	}

	public void updateTransform() {
		Transform transform = null;
		if (Editor.viewingEditor) {
			transform = EditorLayer.get().editorTransform();
		} else {
			for (GameObject gameObject : gameObjects) {
				if (gameObject.enabled()) {
					Camera camera = gameObject.camera();
					if (camera != null && camera.isMainCamera) {
						transform = gameObject.transform();
					}
				}
			}
		}
		
		if (transform == null) return;
		layer.setTranslation(graphics().width() / 2, graphics().height() / 2);
		layer.setOrigin(transform.position.x, transform.position.y);
		layer.setScaleX(transform.scaleX);
		layer.setScaleY(transform.scaleY);
		layer.setRotation(transform.rotation);
	}
}
