package com.paperengine.core;

import java.util.ArrayList;
import java.util.List;

import com.paperengine.core.camera.Camera;

import playn.core.GroupLayer;
import playn.core.Layer;
import playn.core.util.Clock;
import static playn.core.PlayN.*;

public class Scene {
	private List<GameObject> gameObjects = new ArrayList<GameObject>();
	private GroupLayer layer; 
	
	public Layer layer() {
		return layer;
	}
	
	public Iterable<GameObject> gameObjects() {
		return gameObjects;
	}
	
	public Scene() {
		layer = graphics().createGroupLayer();
//		layer.add(graphics().createImageLayer(assets().getImage("images/bg.png")));
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
		layer.setTranslation(graphics().width() / 2, graphics().height() / 2);
		
		for (GameObject gameObject : gameObjects) {
			if (gameObject.enabled()) {
				gameObject.paint(clock);
				if (gameObject.renderer() != null) {
//					System.out.println(gameObject.renderer().layer().parent().parent().parent());
				}
				
				Camera camera = gameObject.camera();
				if (camera != null && camera.isMainCamera) {
					layer.setOrigin(gameObject.x(), gameObject.y());
					layer.setScaleX(gameObject.scaleX());
					layer.setScaleY(gameObject.scaleY());
					layer.setRotation(-camera.rotation);
				}
			}
		}
	}
}
