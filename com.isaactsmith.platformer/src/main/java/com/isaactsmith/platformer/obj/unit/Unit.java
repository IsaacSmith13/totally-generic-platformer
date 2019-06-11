package com.isaactsmith.platformer.obj.unit;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.isaactsmith.platformer.obj.Obj;

public abstract class Unit extends Obj {

	// terminal velocity = unit height / terminal velocity pixels per frame
	private static final int TERMINAL_VELOCITY = 8;
	private static final int FRAMES_PER_IMAGE_CHANGE = 120;
	private static final double jumpSpeed = 8;
	private double currentJumpSpeed = jumpSpeed;
	private boolean isJumping = false;
	private double moveSpeed = 2.5;
	private boolean isFalling = false;
	private boolean isAlive = true;
	private double yVelocity = 0;
	private int imageCounter = 0;
	private int lastImage = 0;
	private boolean left = false;
	private boolean right = false;
	private boolean willCollideTop = false;
	private boolean willCollideRight = false;
	private boolean willCollideLeft = false;

	public Unit(int x, int y, BufferedImage[] images) {
		super(x, y, images);
	}
	
	public Unit(int x, int y, BufferedImage[] images, double moveSpeed) {
		super(x, y, images);
		this.moveSpeed = moveSpeed;
	}

	@Override
	public void paint(Graphics g) {
		// selects a default standing image facing the last moved direction to use if
		// not moving
		int imageToRender = ((lastImage) / 3) * 3;

		if (right) {
			if (isJumping || isFalling) {
				imageToRender = 1;
			} else {
				imageToRender = imageCounter / FRAMES_PER_IMAGE_CHANGE;
			}
		} else if (left) {
			if (isJumping || isFalling) {
				imageToRender = 4;
			} else {
				imageToRender = imageCounter / FRAMES_PER_IMAGE_CHANGE + 3;
			}
		}

		g.drawImage(getImages()[imageToRender], (int) Math.round(getX()), (int) Math.round(getY()) + 1, getWidth(),
				getHeight(), null);
		if (++imageCounter / FRAMES_PER_IMAGE_CHANGE == 3) {
			imageCounter = 0;
		}
		lastImage = imageToRender;
	}

	public void jump() {
		if (!isJumping && !isFalling) {
			setJumping(true);
		}
	}

	public void move(String direction) {
		switch (direction) {
		case ("left"):
			setLeft(true);
			break;
		case ("right"):
			setRight(true);
			break;
		case ("stop"):
			setLeft(false);
			setRight(false);
			break;
		default:
			break;
		}
	}

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

	public boolean isFalling() {
		return isFalling;
	}

	public void setFalling(boolean isFalling) {
		this.isFalling = isFalling;
	}

	public boolean isJumping() {
		return isJumping;
	}

	public void setJumping(boolean isJumping) {
		this.isJumping = isJumping;
	}

	public double getCurrentJumpSpeed() {
		return currentJumpSpeed;
	}

	public void setCurrentJumpSpeed(double currentJumpSpeed) {
		this.currentJumpSpeed = currentJumpSpeed;
	}

	public double getJumpspeed() {
		return jumpSpeed;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public double getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(int moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

	public boolean willCollideTop() {
		return willCollideTop;
	}

	public void setCollideTop(boolean topCollision) {
		this.willCollideTop = topCollision;
	}

	public boolean willCollideRight() {
		return willCollideRight;
	}

	public void setCollideRight(boolean willCollideRight) {
		this.willCollideRight = willCollideRight;
	}

	public boolean willCollideLeft() {
		return willCollideLeft;
	}

	public void setCollideLeft(boolean willCollideLeft) {
		this.willCollideLeft = willCollideLeft;
	}

	public void resetCollision() {
		willCollideTop = false;
		willCollideLeft = false;
		willCollideRight = false;
	}

	public double getTerminalVelocity() {
		return getHeight() / TERMINAL_VELOCITY;
	}
}