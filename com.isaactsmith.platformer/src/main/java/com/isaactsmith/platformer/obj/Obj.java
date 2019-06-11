package com.isaactsmith.platformer.obj;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.isaactsmith.platformer.handler.UnitHandler;

public abstract class Obj {

	public static final int GLOBAL_SIZE = 32;
	private double x;
	private double y;
	private BufferedImage image;
	private BufferedImage[] images;
	private boolean isForeground;
	private int width;
	private int height;

	public Obj(double x, int y, BufferedImage image) {
		this.x = x;
		this.y = y;
		this.image = image;
		width = GLOBAL_SIZE;
		height = GLOBAL_SIZE;
	}

	public Obj(int x, int y, BufferedImage[] images) {
		this.x = x;
		this.y = y;
		this.images = images;
		width = GLOBAL_SIZE;
		height = GLOBAL_SIZE;
	}

	public abstract void paint(Graphics g);

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getXOffset() {
		return (int) (x + UnitHandler.getXOffset());
	}

	public int getYOffset() {
		return (int) (y + UnitHandler.getYOffset());
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

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public BufferedImage[] getImages() {
		return images;
	}
}