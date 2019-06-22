package com.isaactsmith.platformer.obj;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.isaactsmith.platformer.handler.FrameHandler;

public abstract class Obj {

	private static int globalSize = (int) (FrameHandler.getWindowHeight() / 22.5);
	private static double scalar = globalSize / 32.0;
	private final BufferedImage image;
	private final BufferedImage[] images;
	private double x;
	private double y;
	private int width;
	private int height;

	public Obj(double x, int y, BufferedImage image) {
		this.x = x;
		this.y = y;
		this.image = image;
		width = globalSize;
		height = globalSize;
		images = null;
	}

	public Obj(int x, int y, BufferedImage[] images) {
		this.x = x;
		this.y = y;
		this.images = images;
		width = globalSize;
		height = globalSize;
		image = null;
	}

	public static void reScale() {
		globalSize = (int) (FrameHandler.getWindowHeight() / 22.5);
		scalar = globalSize / 32.0;
	}

	public abstract void paint(Graphics g);

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Rectangle getRect() {
		return new Rectangle((int) getX(), (int) getY(), getWidth(), getHeight());
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

	public BufferedImage getImage() {
		return image;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public BufferedImage[] getImages() {
		return images;
	}

	public static int getGlobalSize() {
		return globalSize;
	}

	public static double getScalar() {
		return scalar;
	}
}