package com.paperengine.core.render;

import playn.core.Canvas;
import playn.core.CanvasImage;
import playn.core.Image;
import playn.core.PlayN;

public class ShapeRenderer extends CanvasRenderer {

	public enum ShapeType {
		Circle,
		Rectangle,
		RoundRectangle,
	}
	
	private ShapeType shapeType;
	private int color;
	private int borderColor;
	private float borderWidth;
	private float padding;
	
	public ShapeType shapeType() {
		return shapeType;
	}
	
	public void setShapeType(ShapeType shapeType) {
		this.shapeType = shapeType;
		refreshImage();
	}
	
	public int color() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
		refreshImage();
	}

	public int borderColor() {
		return borderColor;
	}

	public void setBorderColor(int borderColor) {
		this.borderColor = borderColor;
		refreshImage();
	}

	public float borderWidth() {
		return borderWidth;
	}

	public void setBorderWidth(float borderWidth) {
		this.borderWidth = borderWidth;
		refreshImage();
	}

	public float padding() {
		return padding;
	}

	public void setPadding(float padding) {
		this.padding = padding;
		refreshImage();
	}

	public ShapeRenderer(ShapeType shapeType, float width, float height, int color, int borderColor,
			float borderWidth, float padding) {
		super(width, height);
		this.shapeType = shapeType;
		this.color = color;
		this.borderColor = borderColor;
		this.borderWidth = borderWidth;
		this.padding = padding;
		refreshImage();
	}

	@Override
	protected Image createImage(float width, float height) {
		if (shapeType == null) return null;
		
		CanvasImage image = PlayN.graphics().createImage(width, height);
		Canvas canvas = image.canvas();
		
		float w = width - 2 * padding, h = height - 2 * padding;
		float minWH = Math.min(w, h);
		float roundRectRadius = h / 10;
		
		canvas.setFillColor(color);
		switch (shapeType) {
		case Circle: canvas.fillCircle(width / 2, height / 2, minWH / 2); break;
		case Rectangle: canvas.fillRect(padding, padding, w, h); break;
		case RoundRectangle: canvas.fillRoundRect(
				padding, padding, w, h, roundRectRadius); break;
		}
		
		canvas.setStrokeColor(borderColor);
		canvas.setStrokeWidth(borderWidth);
		switch (shapeType) {
		case Circle: canvas.strokeCircle(w / 2, h / 2, minWH / 2 - borderWidth / 2); break;
		case Rectangle: canvas.strokeRect(padding, padding, w - borderWidth / 2, h - borderWidth / 2); break;
		case RoundRectangle: canvas.strokeRoundRect(
				padding, padding, w, h, roundRectRadius); break;
		}
		
		return image;
	}

}
