package com.paperengine.core.render;

import playn.core.ImageLayer;
import playn.core.Layer;
import playn.core.PlayN;

public class ImageRenderer extends Renderer {

	private ImageLayer layer;
	
	@Override
	public Layer layer() {
		return layer;
	}
	
	public ImageRenderer(String path) {
		layer = PlayN.graphics().createImageLayer();
		layer.setImage(PlayN.assets().getImage(path));
	}

}
