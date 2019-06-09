package com.isaactsmith.platformer.obj.unit;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.isaactsmith.platformer.obj.Obj;

public abstract class Unit extends Obj {

	// terminal velocity = unit height / terminal velocity pixels per frame
	private static final int TERMINAL_VELOCITY = 8;
	private static final int FRAMES_PER_IMAGE_CHANGE = 120;
	private boolean isAlive = true;
	private boolean isFalling = false;
	private double xVelocity = 0;
	private double yVelocity = 0;
	private int imageCounter = 0;
	private int lastImage = 0;
	private boolean willCollideX = false;
	private boolean willCollideY = false;

	public Unit(int x, int y, BufferedImage[] images) {
		super(x, y, images);
	}

	public boolean isOn(Obj object) {
		int xDifference = (int) (getX() - object.getX());
		int yDifference = (int) (getY() - object.getY());

		if ((xDifference > -getWidth() && xDifference < object.getWidth()) && yDifference > -getHeight()
				&& yDifference < (getHeight() / TERMINAL_VELOCITY) + 1) {
			return true;
		}
		return false;
	}

	public boolean isCollidingWith(int x, int y, Obj object) {
		double xDiff = x - object.getX();
		double yDiff = y - object.getY();

		if ((xDiff > -getWidth() && xDiff < object.getWidth()) && yDiff > -getHeight() && yDiff < object.getHeight()) {
			return true;
		}
		return false;
	}

	public boolean isCollidingWith(Obj object) {
		return isCollidingWith((int) getX(), (int) getY(), object);
	}

	@Override
	public void paint(Graphics g) {
		int velocity = (int) getXVelocity();
		// Turns into 0 or 1 depending on whether or not the unit was last moving right
		// or left
		int imageToRender = ((lastImage) / 3) * 3;

		if (velocity > 0) {
			imageToRender = imageCounter / FRAMES_PER_IMAGE_CHANGE;
		} else if (velocity < 0) {
			imageToRender = imageCounter / FRAMES_PER_IMAGE_CHANGE + 3;
		}

		g.drawImage(getImages()[imageToRender], (int) Math.round(getX()), (int) Math.round(getY()), getWidth(),
				getHeight(), null);
		if (imageCounter / FRAMES_PER_IMAGE_CHANGE == 2) {
			imageCounter = 0;
		} else {
			imageCounter++;
		}
		lastImage = imageToRender;
	}

	abstract void jump();

	abstract void move(int direction);

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public double getYVelocity() {
		return yVelocity;
	}

	public void setYVelocity(double yVelocity) {
		this.yVelocity = Math.min(yVelocity, getHeight() / TERMINAL_VELOCITY);
	}

	public double getXVelocity() {
		return xVelocity;
	}

	public void setXVelocity(double xVelocity) {
		this.xVelocity = xVelocity;
	}

	public boolean isFalling() {
		return isFalling;
	}

	public void setFalling(boolean isFalling) {
		this.isFalling = isFalling;
	}

	public boolean willCollideX() {
		return willCollideX;
	}

	public void setWillCollideX(boolean willCollideX) {
		this.willCollideX = willCollideX;
	}

	public boolean willCollideY() {
		return willCollideY;
	}

	public void setWillCollideY(boolean willCollideY) {
		this.willCollideY = willCollideY;
	}
}
