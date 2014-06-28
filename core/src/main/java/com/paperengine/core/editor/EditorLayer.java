package com.paperengine.core.editor;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.log;

import java.util.HashMap;

import playn.core.Color;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.ImmediateLayer;
import playn.core.Layer;
import playn.core.Mouse.ButtonEvent;
import playn.core.Mouse.Listener;
import playn.core.Mouse.MotionEvent;
import playn.core.Mouse.WheelEvent;
import playn.core.StockInternalTransform;
import playn.core.Surface;
import playn.core.util.Callback;
import playn.core.util.Clock.Source;
import pythagoras.f.Point;
import pythagoras.util.NoninvertibleTransformException;
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
	
	private boolean draggingCamera;
	private Transform editorTransform = new Transform();
	private GameObject selectedObject;
	private SelectedListener selectedListener;
	
	private ImmediateLayer selectionLayer;
	private GroupLayer selectedLayer;
	private ImageLayer arrowX, arrowY;
	private boolean draggingX, draggingY;
	private Point startDragScreen, startDragObject;
	
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
		
		selectedLayer = graphics().createGroupLayer();
		layer.add(selectedLayer);
		Image arrow = assets().getImage("editor/arrow.png");
		arrowX = graphics().createImageLayer(arrow);
		arrowX.setTint(Color.argb(255, 255, 30, 30));
		selectedLayer.add(arrowX);
		arrowY = graphics().createImageLayer(arrow);
		arrowY.setTint(Color.argb(255, 30, 220, 30));
		arrowY.setRotation(-(float)Math.PI / 2);
		selectedLayer.add(arrowY);
		arrow.addCallback(new Callback<Image>() {
			@Override
			public void onSuccess(Image result) {
				arrowX.setOrigin(-1, result.height() / 2);
				arrowY.setOrigin(-1, result.height() / 2);
			}

			@Override
			public void onFailure(Throwable cause) {
				cause.printStackTrace();
			}
		});
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
		selectedLayer.setVisible(selectedObject != null);
		selectedLayer.setTy(100);
		if (selectedObject != null) {
			getFullTransform(selectedObject.layer(), tempTransform);
			selectedLayer.setTranslation(tempTransform.tx(), tempTransform.ty());
			try {
				selectedLayer.setRotation(tempTransform.rotation());
			} catch (NoninvertibleTransformException e) {
				selectedLayer.setRotation(0);
			}
		}
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
		
		if (getLayerHit(arrowX, event) == arrowX) {
			startDragSelected(event, true, false);
			return;
		} else if (getLayerHit(arrowY, event) == arrowY) {
			startDragSelected(event, false, true);
			return;
		}
		
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
				startDragSelected(event, true, true);
				return;
			}
		}
		
		draggingCamera = true;
	}
	
	private void startDragSelected(ButtonEvent event, boolean x, boolean y) {
		draggingX = x;
		draggingY = y;
		startDragScreen = new Point(event.x(), event.y());
		startDragObject = new Point(selectedObject.x(), selectedObject.y());
	}
	
	private Layer getLayerHit(Layer parent, ButtonEvent event) {
		tempPoint1.set(event.x(), event.y());
		Point p = Layer.Util.screenToLayer(parent, tempPoint1, tempPoint1);
		return parent.hitTest(p);
	}

	@Override
	public void onMouseUp(ButtonEvent event) {
		draggingCamera = false;
		draggingX = false; draggingY = false;
		if (!Editor.updateEditor()) return;
	}

	@Override
	public void onMouseMove(MotionEvent event) {
		if (!Editor.updateEditor()) return;
		if (draggingCamera) {
			editorTransform.position.x -= event.dx() / editorTransform.scaleX;
			editorTransform.position.y -= event.dy() / editorTransform.scaleY;
		} else if (selectedObject != null && (draggingX || draggingY)) {
			Transform transform = selectedObject.transform();
			getFullTransform(selectedObject.layer().parent(), tempTransform);
			tempPoint1.set(event.x() - startDragScreen.x, event.y() - startDragScreen.y);
			
			if (!draggingX || !draggingY) {
				tempPoint2.set(draggingX ? 1 : 0, draggingY ? 1 : 0);
				try {
					tempPoint2.rotate(tempTransform.rotation(), tempPoint2);
				} catch (NoninvertibleTransformException e) { }
				tempPoint2.rotate(selectedObject.rotation(), tempPoint2);
				float dot = tempPoint1.x * tempPoint2.x + tempPoint1.y * tempPoint2.y;
				tempPoint2.mult(dot, tempPoint1);
			}
			
			tempTransform.setTranslation(0, 0);
			tempTransform.inverseTransform(tempPoint1, tempPoint1);

			transform.position.x = startDragObject.x + tempPoint1.x;
			transform.position.y = startDragObject.y + tempPoint1.y;
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
