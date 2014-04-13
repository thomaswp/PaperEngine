package com.paperengine.core;

import static playn.core.PlayN.*;
import playn.core.CanvasImage;
import playn.core.Game;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Mouse.ButtonEvent;
import playn.core.Mouse.Listener;
import playn.core.Mouse.MotionEvent;
import playn.core.Mouse.WheelEvent;
import tripleplay.util.Colors;

public class PaperGame extends Game.Default {

	ImageLayer testImage;
	
	public PaperGame() {
		super(16); // call update every 33ms (30 times per second)
	}

	@Override
	public void init() {
		// create and add background image layer
		Image bgImage = assets().getImage("images/bg.png");
		ImageLayer bgLayer = graphics().createImageLayer(bgImage);
		bgLayer.setSize(graphics().width(), graphics().height());
		graphics().rootLayer().add(bgLayer);
		
		CanvasImage canvasImage = graphics().createImage(100, 100);
		canvasImage.canvas().setFillColor(Colors.BLUE);
		canvasImage.canvas().fillCircle(50, 50, 50);
		testImage = graphics().createImageLayer(canvasImage);
		testImage.setDepth(10);
		graphics().rootLayer().add(testImage);

		mouse().setListener(new Listener() {
			@Override
			public void onMouseWheelScroll(WheelEvent event) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onMouseUp(ButtonEvent event) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onMouseMove(MotionEvent event) {
				testImage.setTranslation(event.x(), event.y());
			}
			
			@Override
			public void onMouseDown(ButtonEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public void update(int delta) {
		
	}

	@Override
	public void paint(float alpha) {
		// the background automatically paints itself, so no need to do anything here!
	}
}
