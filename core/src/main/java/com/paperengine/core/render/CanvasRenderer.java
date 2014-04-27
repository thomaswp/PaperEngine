package com.paperengine.core.render;

import static playn.core.PlayN.graphics;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Layer;

public abstract class CanvasRenderer extends SizedRenderer {

	private ImageLayer layer;
	
	@Override
	public Layer layer() {
		return layer;
	}
	
	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		refreshImage();
	}
	
	protected abstract Image createImage(float width, float height);
	
	public CanvasRenderer() {
		layer = graphics().createImageLayer();
	}
	
	
	public void refreshImage() {
		layer.setImage(createImage(width, height));
	}
}
