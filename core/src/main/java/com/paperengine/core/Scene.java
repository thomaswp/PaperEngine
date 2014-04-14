package com.paperengine.core;

import static playn.core.PlayN.graphics;
import static playn.core.PlayN.mouse;

import java.util.ArrayList;
import java.util.List;

import playn.core.GroupLayer;
import playn.core.Layer;
import playn.core.Mouse.ButtonEvent;
import playn.core.Mouse.Listener;
import playn.core.Mouse.MotionEvent;
import playn.core.Mouse.WheelEvent;
import playn.core.util.Clock;

import com.paperengine.core.camera.Camera;

public class Scene implements IUpdatable, Listener {
	private List<GameObject> gameObjects = new ArrayList<GameObject>();
	private GroupLayer layer; 
	
	private boolean editorMouseDown;
	private Transform editorTransform = new Transform();
	
	public Layer layer() {
		return layer;
	}
	
	public Iterable<GameObject> gameObjects() {
		return gameObjects;
	}
	
	public Scene() {
		layer = graphics().createGroupLayer();
		mouse().setListener(this);
	}
	
	public void addGameObject(GameObject gameObject) {
		for (int i = 0; i <= gameObjects.size(); i++) {
			if (i == gameObjects.size() || gameObjects.get(i).depth() > gameObject.depth()) {
				gameObjects.add(i, gameObject);
				break;
			}
		}
		layer.add(gameObject.layer());
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
				
				Camera camera = gameObject.camera();
				if (camera != null && camera.isMainCamera) {
					updateTransform(gameObject.transform());
				}
			}
		}
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
		updateTransform(editorTransform);
	}

	private void updateTransform(Transform transform) {
		layer.setTranslation(graphics().width() / 2, graphics().height() / 2);
		layer.setOrigin(transform.position.x, transform.position.y);
		layer.setScaleX(transform.scaleX);
		layer.setScaleY(transform.scaleY);
		layer.setRotation(transform.rotation);
	}

	@Override
	public void onMouseDown(ButtonEvent event) {
		editorMouseDown = true;
	}

	@Override
	public void onMouseUp(ButtonEvent event) {
		editorMouseDown = false;
	}

	@Override
	public void onMouseMove(MotionEvent event) {
		if (editorMouseDown) {
			editorTransform.position.x -= event.dx() / editorTransform.scaleX;
			editorTransform.position.y -= event.dy() / editorTransform.scaleY;
		}
	}

	@Override
	public void onMouseWheelScroll(WheelEvent event) {
		editorTransform.scaleX *= Math.pow(1.1, -event.velocity());
		editorTransform.scaleY = editorTransform.scaleX;
	}
}
