package com.paperengine.editor.game;

import tripleplay.util.Colors;

import com.paperengine.core.GameObject;
import com.paperengine.core.Scene;
import com.paperengine.core.camera.Camera;
import com.paperengine.core.render.ShapeRenderer;
import com.paperengine.core.render.ShapeRenderer.ShapeType;

public class TestScene extends Scene {
	
	public TestScene() {
		GameObject camera = new GameObject();
		camera.addComponent(new Camera());
		camera.camera().isMainCamera = true;
		
		GameObject obj = new GameObject();
		ShapeRenderer renderer = new ShapeRenderer(ShapeType.Circle, 100, 100, Colors.BLUE, Colors.WHITE, 4, 0);
		renderer.setOrigin(50, 50);
		obj.addComponent(renderer);
		
		
		addGameObject(camera);
		addGameObject(obj);
	}
}
