package com.isaactsmith.platformer.obj;

import java.awt.image.BufferedImage;

public abstract class Obj {
	private int x;
	private int y;
	private BufferedImage image;
	private boolean isForeground;

	public Obj(int x, int y, BufferedImage image) {
		this.x = x;
		this.y = y;
		this.setImage(image);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public boolean isForeground() {
		return isForeground;
	}

	public void setForeground(boolean isForeground) {
		this.isForeground = isForeground;
	}
}