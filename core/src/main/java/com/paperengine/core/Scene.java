package com.paperengine.core;

import static playn.core.PlayN.graphics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import playn.core.GroupLayer;
import playn.core.Layer;
import playn.core.Mouse.ButtonEvent;
import playn.core.Mouse.Listener;
import playn.core.Mouse.MotionEvent;
import playn.core.Mouse.WheelEvent;
import playn.core.util.Clock;
import pythagoras.f.Point;

import com.paperengine.core.camera.Camera;
import com.paperengine.core.editor.EditorLayer;

public class Scene implements IUpdatable, Listener, Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<GameObject> gameObjects = new ArrayList<GameObject>();
	private List<GameObject> allGameObjects = new ArrayList<GameObject>();
	private GroupLayer layer; 
	private int nextObjectId = 0;
	
	private boolean editorMouseDown;
	private Transform editorTransform = new Transform();
	private Layer editorSelectedLayer;
	
	protected boolean isEditorSelectedLayer(Layer layer) {
		return layer == editorSelectedLayer;
	}
	
	public Layer layer() {
		return layer;
	}
	
	public Iterable<GameObject> gameObjects() {
		return gameObjects;
	}
	
	public Scene() {
		layer = graphics().createGroupLayer();
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
			transform = editorTransform;
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

	@Override
	public void onMouseDown(ButtonEvent event) {
		if (!Editor.updateEditor()) return;
		Point p = Layer.Util.screenToLayer(layer, event.x(), event.y());
		editorSelectedLayer = layer.hitTest(p);
		editorMouseDown = true;
	}

	@Override
	public void onMouseUp(ButtonEvent event) {
		if (!Editor.updateEditor()) return;
		editorMouseDown = false;
	}

	@Override
	public void onMouseMove(MotionEvent event) {
		if (!Editor.updateEditor()) return;
		if (editorMouseDown) {
			editorTransform.position.x -= event.dx() / editorTransform.scaleX;
			editorTransform.position.y -= event.dy() / editorTransform.scaleY;
		}
	}

	@Override
	public void onMouseWheelScroll(WheelEvent event) {
		if (!Editor.updateEditor()) return;
		Point mouse = mouseToGame(event.x(), event.y());
		editorTransform.scaleX *= Math.pow(1.1, -event.velocity());
		editorTransform.scaleY = editorTransform.scaleX;
		Point newMouse = mouseToGame(event.x(), event.y());
		editorTransform.position.addLocal(mouse.x - newMouse.x, mouse.y - newMouse.y);
	}
	
	private Point mouseToGame(float x, float y) {
		Point point = new Point();
		point.x = (x - graphics().width() / 2) / editorTransform.scaleX + editorTransform.position.x;
		point.y = (y - graphics().height() / 2) / editorTransform.scaleY + editorTransform.position.y;
		return point;
	}
	
	private Point gameToMouse(float x, float y) {
		Point point = new Point();
		point.x = (x - editorTransform.position.x) * editorTransform.scaleX + graphics().width() / 2;
		point.y = (y - editorTransform.position.y) * editorTransform.scaleY + graphics().height() / 2;
		return point;
	}
}
