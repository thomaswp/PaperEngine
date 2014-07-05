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
import com.paperengine.core.physics.RendererCircleCollider;
import com.paperengine.core.render.BackgroundRenderer;
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
		obj.addComponent(new RendererCircleCollider());
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
		
		GameObject platform = new GameObject();
		platform.setName("Ground");
		renderer = new ShapeRenderer();
		renderer.set(ShapeType.Rectangle, 1000, 50, Colors.GRAY, Colors.DARK_GRAY, 2, 0);
		renderer.setCentered(true);
		platform.addComponent(renderer);
		PhysicsBody body = new PhysicsBody();
		body.bodyType = BodyType.STATIC;
		platform.addComponent(body);
		platform.addComponent(new RendererBoxCollider());
		platform.transform().position.set(0, 200);
		
		GameObject background = new GameObject();
		background.setName("Background");
		background.transform().depth = -10;
		background.transform().position.y = 45;
		
		GameObject sky = new GameObject();
		sky.setName("Sky");
		BackgroundRenderer bgRenderer = new BackgroundRenderer();
		bgRenderer.setImage(PlayN.assets().getImage("graphics/backgrounds/sky.png"));
		bgRenderer.repeatLeft = bgRenderer.repeatRight = bgRenderer.repeatUp = true;
		sky.addComponent(bgRenderer);
		background.addChild(sky);
		
		GameObject ground = new GameObject();
		ground.setName("Ground");
		bgRenderer = new BackgroundRenderer();
		bgRenderer.setImage(PlayN.assets().getImage("graphics/foregrounds/ground.png"));
		bgRenderer.repeatLeft = bgRenderer.repeatRight = true;
		ground.addComponent(bgRenderer);
		background.addChild(ground);
		
		GameObject mountains = new GameObject();
		mountains.setName("Mountains");
		mountains.transform().position.y = -256;
		bgRenderer = new BackgroundRenderer();
		bgRenderer.setImage(PlayN.assets().getImage("graphics/midgrounds/mountain.png"));
		bgRenderer.repeatLeft = bgRenderer.repeatRight = true;
		mountains.addComponent(bgRenderer);
		background.addChild(mountains);
		
		GameObject trees = new GameObject();
		trees.setName("Trees");
		trees.transform().position.y = -195;
		bgRenderer = new BackgroundRenderer();
		bgRenderer.setImage(PlayN.assets().getImage("graphics/midgrounds/trees.png"));
		bgRenderer.repeatLeft = bgRenderer.repeatRight = true;
		trees.addComponent(bgRenderer);
		background.addChild(trees);
		
		addGameObject(camera);
		addGameObject(obj);
		addGameObject(fireball);
		addGameObject(platform);
		addGameObject(background);
	}
}
