package com.paperengine.editor.game;

import java.util.ArrayList;
import java.util.List;

import com.paperengine.core.Component;
import com.paperengine.core.GameObject;
import com.paperengine.core.Scene;
import com.paperengine.core.Transform;
import com.paperengine.editor.editor.accessor.Accessor;
import com.paperengine.editor.editor.accessor.FieldAccessor;
import com.paperengine.editor.editor.accessor.MethodAccessor;

public class SceneSerializer {

	public static Scene copy(Scene scene) {
		
		final Scene s = new Scene();
		traverse(scene, new Traverser() {
			protected List<GameObject> parents = new ArrayList<GameObject>();
			protected Component currentComponent;
			
			protected GameObject gameObject() {
				return parents.size() == 0 ? null : parents.get(0);
			}

			
			public void startGameObject(GameObject object) {
				GameObject parent = gameObject();
				GameObject copy = object.shallowCopy();
				if (parent == null) {
					s.addGameObject(copy);
				} else {
					parent.addChild(copy);
				}
				
				parents.add(0, copy);
				currentComponent = null;
			}
			
			public void endGameObject() {
				parents.remove(0);
			}
			
			public void startComponent(Component component) {
				try {
					if (component instanceof Transform) {
						currentComponent = gameObject().transform();
					} else {
						currentComponent = component.getClass().getConstructor().newInstance();
						gameObject().addComponent(currentComponent);
					}
				} catch (Exception e) {
					System.out.println("Missing no-args constructor on " + component.getClass().getName());
					e.printStackTrace();
				}
			}
			
			public void startAccessor(Accessor accessor) {
				accessor.copyForObject(currentComponent).set(accessor.get());
			}
			
		});
		return s;
	}

	private static void traverse(Scene scene, Traverser traverser) {
		traverser.startScene(scene);
		for (GameObject object : scene.gameObjects()) {
			traverse(object, traverser);
		}
	}

	private static void traverse(GameObject object, Traverser traverser) {
		traverser.startGameObject(object);
		for (Component component : object.components()) {
			traverse(component, traverser);
		}
		for (GameObject child : object.children()) {
			traverse(child, traverser);
		}
		traverser.endGameObject();
	}

	private static void traverse(Component component, Traverser traverser) {
		traverser.startComponent(component);
		for (Accessor accessor : FieldAccessor.getForObject(component)) {
			traverser.startAccessor(accessor);
		}
		for (Accessor accessor : MethodAccessor.getForObject(component)) {
			traverser.startAccessor(accessor);
		}
	}
	

	private static abstract class Traverser {

		void startScene(Scene scene) { };
		void startAccessor(Accessor accessor) { };
		void startComponent(Component component) { };
		void endGameObject() { };
		void startGameObject(GameObject object) { };
		
	}
}
