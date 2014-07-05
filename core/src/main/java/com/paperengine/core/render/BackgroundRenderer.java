package com.paperengine.core.render;

import static playn.core.PlayN.graphics;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Clock;
import pythagoras.f.Point;
import pythagoras.util.NoninvertibleTransformException;

public class BackgroundRenderer extends Renderer {

	public boolean repeatLeft, repeatRight, repeatUp, repeatDown;
	
	private ImageLayer layer;
	private Point tempPoint1 = new Point(), tempPoint2 = new Point();
	
	@Override
	public Layer layer() {
		return layer;
	}
	
	public Image image() {
		return layer.image();
	}
	
	public void setImage(Image image) {
		layer.setImage(image);
		image.setRepeat(true, true);
	}


	public BackgroundRenderer() {
		layer = PlayN.graphics().createImageLayer();
	}
	
	@Override
	public void paint(Clock clock) {
		super.paint(clock);
		if (layer.image() == null || !layer.image().isReady()) return;
		tempPoint1.set(0, 0);
		tempPoint2.set(graphics().width(), graphics().height());
		try {
			Layer.Util.screenToLayer(gameObject().layer(), tempPoint1, tempPoint1);
			Layer.Util.screenToLayer(gameObject().layer(), tempPoint2, tempPoint2);
		} catch (NoninvertibleTransformException e) {
			return;
		}
		
		float imageWidth = layer.image().width();
		float imageHeight = layer.image().height();
		
		int left = repeatLeft ? (int) Math.floor(tempPoint1.x /  imageWidth) : 0;
		int right = repeatRight ? (int) Math.ceil(tempPoint2.x / imageWidth) : 
			(repeatLeft ? 0 : 1);
		int top = repeatUp? (int) Math.floor(tempPoint1.y /  imageHeight) : 0;
		int bot = repeatDown ? (int) Math.ceil(tempPoint2.y / imageHeight) : 
			(repeatUp ? 0 : 1);
		
		
		layer.setTranslation(left * imageWidth, top * imageHeight);
		layer.setSize((right - left) * imageWidth, (bot - top) * imageHeight);
	}
	
	@Override
	public boolean draggableInEditor() {
		return false;
	}
	
	@Override
	public void paintEditor(Clock clock) {
		super.paintEditor(clock);
		paint(clock);
	}
}
