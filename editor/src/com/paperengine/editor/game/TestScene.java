package com.paperengine.editor.game;

import playn.core.PlayN;
import tripleplay.util.Colors;

import com.paperengine.core.GameObject;
import com.paperengine.core.Scene;
import com.paperengine.core.camera.Camera;
import com.paperengine.core.render.ImageRenderer;
import com.paperengine.core.render.ShapeRenderer;
import com.paperengine.core.render.ShapeRenderer.ShapeType;
import com.paperengine.core.test.Circulator;

public class TestScene extends Scene {
	private static final long serialVersionUID = 1L;

	public TestScene() {
		GameObject camera = new GameObject();
		camera.addComponent(new Camera());
		camera.camera().isMainCamera = true;
		camera.setName("Camera");
		
		GameObject obj = new GameObject();
		ShapeRenderer renderer = new ShapeRenderer();
		renderer.set(ShapeType.Circle, 100, 100, Colors.BLUE, Colors.WHITE, 4, 0);
		renderer.setCentered(true);
		obj.addComponent(renderer);
		obj.addComponent(new Circulator());
		obj.setName("Circle");
		
		GameObject child = new GameObject();
		ShapeRenderer childRenderer = new ShapeRenderer();
		childRenderer.set(ShapeType.Rectangle, 50, 50, Colors.RED, Colors.WHITE, 2, 0);
		childRenderer.setCentered(true);
		child.addComponent(childRenderer);
		child.addComponent(new Circulator());
		child.setName("Square");
		
		obj.addChild(child);;
		
		GameObject fireball = new GameObject();
		fireball.setName("Fireball");
		ImageRenderer fireballRenderer = new ImageRenderer();
		fireballRenderer.setImage(PlayN.assets().getImage("graphics/objects/fireball.png"));
		fireballRenderer.setCentered(true);
		fireball.addComponent(fireballRenderer);
		fireball.transform().position.set(-100, -100);
		
		addGameObject(camera);
		addGameObject(obj);
		addGameObject(fireball);
	}
}
