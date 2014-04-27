package com.paperengine.core.render;

import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Callback;

public class ImageRenderer extends SizedRenderer {

	private ImageLayer layer;
	private boolean lockSize = true;
	
	@Override
	public Layer layer() {
		return layer;
	}
	
	public Image image() {
		return layer.image();
	}
	
	public boolean lockSizeToImage() {
		return lockSize;
	}
	
	public void setLockSizeToImage(boolean lockSize) {
		this.lockSize = lockSize;
		resize();
	}

	@Override
	public void setSize(float width, float height) {
		setSizeImpl(width, height);
		lockSize = false;
	}
	
	protected void setSizeImpl(float width, float height) {
		super.setSize(width, height);
		layer.setSize(width, height);
	}
	
	public void setImage(Image image) {
		layer.setImage(image);
		image.addCallback(new Callback<Image>() {
			@Override
			public void onSuccess(Image result) {
				setSizeImpl(result.width(), result.height());
			}

			@Override
			public void onFailure(Throwable cause) { }
		});
	}
	
	public ImageRenderer() {
		layer = PlayN.graphics().createImageLayer();
	}
	
	private void resize() {
		if (lockSize && layer.image() != null && layer.image().isReady()) {
			setSizeImpl(layer.image().width(), layer.image().height());
		}
	}
}
