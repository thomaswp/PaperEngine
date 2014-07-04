package com.paperengine.core.render;

import static playn.core.PlayN.graphics;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Clock;
import pythagoras.f.Point;

public class BackgroundRenderer extends Renderer {

	private ImageLayer layer;
	private boolean repeatX, repeatY;
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
		refreshRepeat();
	}
	
	public boolean repeatX() {
		return repeatX;
	}

	public void setRepeatX(boolean repeatX) {
		this.repeatX = repeatX;
		refreshRepeat();
	}

	public boolean repeatY() {
		return repeatY;
	}

	public void setRepeatY(boolean repeatY) {
		this.repeatY = repeatY;
		refreshRepeat();
	}

	public BackgroundRenderer() {
		layer = PlayN.graphics().createImageLayer();
	}

	private void refreshRepeat() {
		if (layer.image() != null) layer.image().setRepeat(repeatX, repeatY);
	}
	
	@Override
	public void paint(Clock clock) {
		super.paint(clock);
		if (layer.image() == null || !layer.image().isReady()) return;
		tempPoint1.set(0, 0);
		tempPoint2.set(graphics().width(), graphics().height());
		Layer.Util.screenToLayer(gameObject().layer(), tempPoint1, tempPoint1);
		Layer.Util.screenToLayer(gameObject().layer(), tempPoint2, tempPoint2);
		float localSW = tempPoint2.x - tempPoint1.x;
		float localSH = tempPoint2.y - tempPoint1.y;
		layer.setWidth(repeatX ? localSW : layer.image().width());
		layer.setHeight(repeatY ? localSH : layer.image().height());
		layer.setTranslation(tempPoint1.x, tempPoint1.y);
//		layer.setOrigin(tempPoint1.x, tempPoint1.y);
		layer.setOrigin(10, 10);
	}
	
	@Override
	public void paintEditor(Clock clock) {
		super.paintEditor(clock);
		paint(clock);
	}
}
