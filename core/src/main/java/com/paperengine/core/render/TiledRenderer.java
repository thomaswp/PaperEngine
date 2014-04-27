package com.paperengine.core.render;

import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Layer;
import playn.core.PlayN;

public class TiledRenderer extends Renderer {

private ImageLayer layer;
	
	@Override
	public Layer layer() {
		return layer;
	}
	
	public Image image() {
		return layer.image();
	}
	
	public void setImage(Image image) {
		layer.setImage(image);
	}
	
	public TiledRenderer() {
		layer = PlayN.graphics().createImageLayer();
	}

}
