package com.isaactsmith.platformer.obj;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Obj {
	private double x;
	private int y;
	private BufferedImage image;
	private boolean isForeground;
	private int width;
	private int height;

	public Obj(double x, int y, BufferedImage image) {
		this.x = x;
		this.y = y;
		this.setImage(image);
		this.width = 32;
		this.height = 32;
	}

	public Obj(double x, int y, BufferedImage image, int width, int height) {
		this(x, y, image);
		this.width = width;
		this.height = height;
	}
	
	public void paint(Graphics g) {
		g.drawImage(getImage(), (int) Math.round(getX()), (int) Math.round(getY()), getWidth(), getHeight(), null);
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

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}