package com.isaactsmith.platformer.obj.unit;

import java.awt.image.BufferedImage;

import com.isaactsmith.platformer.obj.Obj;

public abstract class Unit extends Obj {

	// terminal velocity = unit height / terminal velocity pixels per frame
	private static final int TERMINAL_VELOCITY = 8;
	private boolean isAlive = true;
	private boolean isFalling = false;
	private double xVelocity = 0;
	private double yVelocity = 0;

	public Unit(int x, int y, BufferedImage image) {
		super(x, y, image);
	}

	abstract void jump();

	abstract void move(int direction);

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public boolean isOn(Obj object) {
		double xDifference = getX() - object.getX();
		double yDifference = getY() - object.getY();

		if ((xDifference > -getWidth() && xDifference < object.getWidth()) && yDifference > -getHeight()
				&& yDifference < (getHeight() / TERMINAL_VELOCITY) + 1) {
			return true;
		}
		return false;
	}

	public boolean isCollidingWith(Obj object) {
		double xDifference = getX() - object.getX();
		double yDifference = getY() - object.getY();

		if ((xDifference > -getWidth() && xDifference < object.getWidth()) && yDifference > -getHeight()
				&& yDifference < object.getHeight()) {
			return true;
		}
		return false;
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
}
