package com.paperengine.core.editor;

import static playn.core.PlayN.graphics;
import static playn.core.PlayN.log;

import java.util.HashMap;

import playn.core.Color;
import playn.core.GroupLayer;
import playn.core.ImmediateLayer;
import playn.core.Layer;
import playn.core.Mouse.ButtonEvent;
import playn.core.Mouse.Listener;
import playn.core.Mouse.MotionEvent;
import playn.core.Mouse.WheelEvent;
import playn.core.StockInternalTransform;
import playn.core.Surface;
import playn.core.util.Clock.Source;
import pythagoras.f.Point;
import tripleplay.util.Colors;

import com.paperengine.core.Component;
import com.paperengine.core.Editor;
import com.paperengine.core.GameObject;
import com.paperengine.core.Handler;
import com.paperengine.core.Handler.Postable;
import com.paperengine.core.Scene;
import com.paperengine.core.Transform;
import com.paperengine.core.render.Renderer;
import com.paperengine.core.render.SizedRenderer;

public class EditorLayer implements Listener, Postable {

	private static EditorLayer instance;

	private GroupLayer layer;
	private GroupLayer sceneLayer;
	
	private boolean mouseDown;
	private Transform editorTransform = new Transform();
	private GameObject selectedObject;
	private SelectedListener selectedListener;
	
	private ImmediateLayer selectionLayer;
	
	private Scene scene;
	
	private HashMap<Layer, Component> layerMap = new HashMap<Layer, Component>();
	
	private Handler handler = new Handler();
	private Point tempPoint1 = new Point(), tempPoint2 = new Point();
	private StockInternalTransform tempTransform = new StockInternalTransform();
	
	public static EditorLayer get() {
		return instance;
	}
	
	public GroupLayer layer() {
		return layer;
	}
	
	public GameObject selectedObject() {
		return selectedObject;
	}
	
	public void setSelectedObject(GameObject object) {
		this.selectedObject = object;
	}
	
	public Scene scene() {
		return scene;
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	public void setSelectedListener(SelectedListener listener) {
		this.selectedListener = listener;
	}
	
	public EditorLayer() {
		instance = this;
		
		layer = graphics().createGroupLayer();
		layer.setDepth(10);
		
		sceneLayer = graphics().createGroupLayer();
		layer.add(sceneLayer);
		
		selectionLayer = graphics().createImmediateLayer(new playn.core.ImmediateLayer.Renderer() {
			@Override
			public void render(Surface surface) {
				paintSelection(surface);
			}
		});
		layer.add(selectionLayer);
	}
	
	public void registerLayer(Layer layer, Component component) {
		layerMap.put(layer, component);
	}
	
	public void reset() {
		layerMap.clear();
		scene = null;
	}

	public void update(int delta) {
		handler.update();
	}

	public void paint(Source clock) {

	}
	
	private void paintSelection(Surface surface) {
		if (selectedObject != null) {
			Renderer renderer = selectedObject.renderer();
			if (renderer != null && renderer instanceof SizedRenderer) {
				surface.save();
				getFullTransform(renderer.layer(), tempTransform);
				surface.transform(tempTransform.m00, tempTransform.m01, tempTransform.m10, 
						tempTransform.m11, tempTransform.tx, tempTransform.ty);
				SizedRenderer sizedRenderer = (SizedRenderer) renderer;
				float width = sizedRenderer.width(), height = sizedRenderer.height();
				float left = sizedRenderer.originX() - width / 2;
				float top = sizedRenderer.originY() - height / 2;
				surface.setFillColor(Color.argb(75, 0, 0, 255));
				surface.fillRect(left, top, width, height);
				surface.restore();
				
				float[] corners = new float[] {
						left, top,
						left + width, top,
						left + width, top + height,
						left, top + height
				};

				surface.setFillColor(Colors.BLUE);
				for (int i = 0; i < 4; i++) {
					tempPoint1.set(corners[i * 2], corners[i * 2 + 1]); 
					int j = (i + 1) % 4;
					tempPoint2.set(corners[j * 2], corners[j * 2 + 1]);
					tempTransform.transform(tempPoint1, tempPoint1);
					tempTransform.transform(tempPoint2, tempPoint2);
					surface.drawLine(tempPoint1.x, tempPoint1.y, tempPoint2.x, tempPoint2.y, 2);
				}
			}
		}
	}
	
	private void getFullTransform(Layer layer, StockInternalTransform transform) {
		getFullTransform(layer, transform, true);
	}
	
	private void getFullTransform(Layer layer, StockInternalTransform transform, boolean reset) {
		if (reset) transform.setTransform(1, 0, 0, 1, 0, 0); 
		if (layer.parent() != null) {
			getFullTransform(layer.parent(), transform, false);
		}
		transform.concatenate(layer.transform(), layer.originX(), layer.originY());
	}

	public void setSceneTransform(Layer layer) {
		pythagoras.f.Transform transform = layer.transform();
		sceneLayer.setOrigin(layer.originX(), layer.originY());
		sceneLayer.setTranslation(transform.tx(), transform.ty());
		sceneLayer.setRotation(transform.rotation());
		sceneLayer.setScale(transform.scaleX(), transform.scaleY());
	}
	
	@Override
	public void onMouseDown(ButtonEvent event) {
		if (!Editor.updateEditor()) return;
		Point p = Layer.Util.screenToLayer(sceneLayer, event.x(), event.y());
		Layer hit = scene.hitTest(p);
		if (hit != null) {
			Component selected = layerMap.get(hit);
			if (selected == null) {
				log().warn("Unregistered Layer: " + hit);
			} else {
				selectedObject = selected.gameObject();
				if (selectedListener != null) {
					selectedListener.onSelected(selectedObject);
				}
			}
		}
		mouseDown = true;
	}

	@Override
	public void onMouseUp(ButtonEvent event) {
		if (!Editor.updateEditor()) return;
		mouseDown = false;
	}

	@Override
	public void onMouseMove(MotionEvent event) {
		if (!Editor.updateEditor()) return;
		if (mouseDown) {
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
	
	public Point mouseToGame(float x, float y) {
		Point point = new Point();
		point.x = (x - graphics().width() / 2) / editorTransform.scaleX + editorTransform.position.x;
		point.y = (y - graphics().height() / 2) / editorTransform.scaleY + editorTransform.position.y;
		return point;
	}
	
	public Point gameToMouse(float x, float y) {
		Point point = new Point();
		point.x = (x - editorTransform.position.x) * editorTransform.scaleX + graphics().width() / 2;
		point.y = (y - editorTransform.position.y) * editorTransform.scaleY + graphics().height() / 2;
		return point;
	}

	public Transform editorTransform() {
		return editorTransform;
	}
	
	public interface SelectedListener {
		void onSelected(GameObject selected);
	}

	@Override
	public Handler handler() {
		return handler;
	}
}
