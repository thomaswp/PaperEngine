package com.paperengine.editor.game;

import org.jbox2d.dynamics.BodyType;

import playn.core.PlayN;
import tripleplay.util.Colors;

import com.paperengine.core.GameObject;
import com.paperengine.core.Scene;
import com.paperengine.core.camera.Camera;
import com.paperengine.core.physics.BoxCollider;
import com.paperengine.core.physics.PhysicsBody;
import com.paperengine.core.physics.RendererBoxCollider;
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
//		obj.addComponent(new Circulator());
		obj.addComponent(new PhysicsBody());
		obj.addComponent(new RendererBoxCollider());
		obj.setName("Circle");
		
		GameObject child = new GameObject();
		ShapeRenderer childRenderer = new ShapeRenderer();
		childRenderer.set(ShapeType.Rectangle, 50, 50, Colors.RED, Colors.WHITE, 2, 0);
		childRenderer.setCentered(true);
		child.addComponent(childRenderer);
		child.addComponent(new Circulator());
		child.setName("Square");
		
		obj.addChild(child);
		
		GameObject fireball = new GameObject();
		fireball.setName("Fireball");
		ImageRenderer fireballRenderer = new ImageRenderer();
		fireballRenderer.setImage(PlayN.assets().getImage("graphics/objects/fireball.png"));
		fireballRenderer.setCentered(true);
		fireball.addComponent(fireballRenderer);
		fireball.transform().position.set(-100, -100);
		fireball.addComponent(new PhysicsBody());
		BoxCollider collider = new BoxCollider();
		collider.width = 32; collider.height = 32;
		collider.originX = 16; collider.originY = 16;
		fireball.addComponent(collider);
		
		GameObject ground = new GameObject();
		ground.setName("Ground");
		renderer = new ShapeRenderer();
		renderer.set(ShapeType.Rectangle, 1000, 50, Colors.GRAY, Colors.DARK_GRAY, 2, 0);
		renderer.setCentered(true);
		ground.addComponent(renderer);
		PhysicsBody body = new PhysicsBody();
		body.bodyType = BodyType.STATIC;
		ground.addComponent(body);
		ground.addComponent(new RendererBoxCollider());
		ground.transform().position.set(0, 200);
		
		addGameObject(camera);
		addGameObject(obj);
		addGameObject(fireball);
		addGameObject(ground);
	}
}
