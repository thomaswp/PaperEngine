package com.paperengine.editor.game;

import tripleplay.util.Colors;

import com.paperengine.core.Circulator;
import com.paperengine.core.GameObject;
import com.paperengine.core.Scene;
import com.paperengine.core.camera.Camera;
import com.paperengine.core.render.ShapeRenderer;
import com.paperengine.core.render.ShapeRenderer.ShapeType;

public class TestScene extends Scene {
	private static final long serialVersionUID = 1L;

	public TestScene() {
		GameObject camera = new GameObject();
		camera.addComponent(new Camera());
		camera.camera().isMainCamera = true;
		camera.setName("Camera");
		
		GameObject obj = new GameObject();
		ShapeRenderer renderer = new ShapeRenderer(ShapeType.Circle, 100, 100, Colors.BLUE, Colors.WHITE, 4, 0);
		renderer.setOrigin(50, 50);
		obj.addComponent(renderer);
		obj.addComponent(new Circulator());
		obj.setName("Circle");
		
		GameObject child = new GameObject();
		ShapeRenderer childRenderer = new ShapeRenderer(ShapeType.Rectangle, 50, 50, Colors.RED, Colors.WHITE, 2, 0);
		childRenderer.setOrigin(25, 25);
		child.addComponent(childRenderer);
		child.addComponent(new Circulator());
		child.setName("Square");
		
		obj.addChild(child);;
		
		addGameObject(camera);
		addGameObject(obj);
	}
}
