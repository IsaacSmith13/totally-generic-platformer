package com.isaactsmith.platformer.obj;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

public abstract class Unit extends Obj {

	private boolean isAlive;
	private boolean isJumping = false;
	private boolean isFalling = false;
	private double xVelocity = 0;
	private double yVelocity = 0;

	public Unit(int x, int y, BufferedImage image) {
		super(x, y, image);
		isAlive = true;
	}

	abstract void jump();

	abstract void move(int direction);

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public void handleFalling(List<Tile> terrain) {
		boolean inAir = true;
		for (Tile tile : terrain) {
			if (isOn(tile)) {
				inAir = false;
				break;
			}
		}
		if (inAir) {
			if (!isJumping()) {
				setFalling(true);
				setYVelocity(getYVelocity() + .2);
			}
		} else {
			setJumping(false);
			setFalling(false);
			setYVelocity(0);
		}
	}

	public void tick() {
		if (isFalling && !isJumping) {
			setY((int) Math.round(getY() + getYVelocity()));
		}
		setX((int) Math.round(getX() + getXVelocity()));
	}

	private boolean isOn(Obj object) {
		double xDifference = getX() - object.getX();
		double yDifference = getY() - object.getY();

		if ((xDifference > -getWidth() && xDifference < object.getWidth()) && yDifference > -getHeight()
				&& yDifference < (getHeight() / 10) + 1) {
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

	public boolean isJumping() {
		return isJumping;
	}

	public void setJumping(boolean isJumping) {
		this.isJumping = isJumping;
	}

	public double getYVelocity() {
		return yVelocity;
	}

	public void setYVelocity(double yVelocity) {
		this.yVelocity = Math.min(yVelocity, getHeight() / 10);
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
