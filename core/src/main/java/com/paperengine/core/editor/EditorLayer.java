package com.paperengine.core.editor;

import static playn.core.PlayN.graphics;
import playn.core.CanvasImage;
import playn.core.GroupLayer;
import playn.core.ImageLayer;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Clock.Source;
import pythagoras.f.Transform;
import tripleplay.util.Colors;

public class EditorLayer {

	private static EditorLayer instance;
	
	private ImageLayer[] cornerLayers = new ImageLayer[4];

	private GroupLayer layer;
	private GroupLayer sceneLayer;
	
	public static EditorLayer get() {
		return instance;
	}
	
	public GroupLayer layer() {
		return layer;
	}
	
	public EditorLayer() {
		instance = this;
		
		layer = graphics().createGroupLayer();
		layer.setDepth(10);
		
		sceneLayer = graphics().createGroupLayer();
		layer.add(sceneLayer);
		
		for (int i = 0; i < cornerLayers .length; i++) {
			float radius = 3;
			CanvasImage image = PlayN.graphics().createImage(radius * 2, radius * 2);
			image.canvas().setFillColor(Colors.GRAY);
			image.canvas().fillCircle(radius, radius, radius);
			ImageLayer layer = graphics().createImageLayer(image);
			layer.setOrigin(image.width() / 2, image.height() / 2);
			cornerLayers[i] = layer;
			sceneLayer.add(layer);
		}
	}
	
	public void reset() {
		
	}

	public void update(int delta) {
		
	}

	public void paint(Source clock) {
		for (int i = 0; i < cornerLayers .length; i++) {
			cornerLayers[i].setScale(1f / sceneLayer.scaleX());
		}
	}

	public void setSceneTransform(Layer layer) {
		Transform transform = layer.transform();
		sceneLayer.setOrigin(layer.originX(), layer.originY());
		sceneLayer.setTranslation(transform.tx(), transform.ty());
		sceneLayer.setRotation(transform.rotation());
		sceneLayer.setScale(transform.scaleX(), transform.scaleY());
	}
}
