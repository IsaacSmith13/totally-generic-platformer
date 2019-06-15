package com.isaactsmith.platformer.obj.unit;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.isaactsmith.platformer.obj.Obj;

public abstract class Unit extends Obj {

	// Terminal velocity = unit height / terminal velocity pixels per frame
	private static final int TERMINAL_VELOCITY = 8;
	// For calculating which unit image to display
	private static final int FRAMES_PER_IMAGE_CHANGE = 120;
	private static final int ANIMATION_FRAME_COUNT = 3;
	private static final int JUMPING_RIGHT_IMG = 1;
	private static final int JUMPING_LEFT_IMG = 4;
	private int imageCounter = 0;
	private int lastImage = 0;
	// Jump and move speeds
	private double jumpSpeed = 8;
	private double currentJumpSpeed = jumpSpeed;
	private boolean isJumping = false;
	private double moveSpeed = 2.5;
	// Collision handlers
	private boolean isFalling = false;
	private boolean isFallingHandled = false;
	private boolean willCollideTop = false;
	private boolean willCollideRight = false;
	private boolean willCollideLeft = false;
	// Movement
	private boolean left = false;
	private boolean right = false;
	private double yVelocity = 0;
	
	private boolean isActive = false;

	public Unit(int x, int y, BufferedImage[] images) {
		super(x, y, images);
	}

	public Unit(int x, int y, BufferedImage[] images, double moveSpeed) {
		super(x, y, images);
		this.moveSpeed = moveSpeed;
	}

	@Override
	public abstract void paint(Graphics g);

	public BufferedImage getImageToRender() {
		// selects a default standing image facing the last moved direction to use if
		// not moving
		int imageToRender = ((lastImage) / ANIMATION_FRAME_COUNT) * ANIMATION_FRAME_COUNT;

		if (right) {
			if (isJumping || isFalling) {
				imageToRender = JUMPING_RIGHT_IMG;
			} else {
				imageToRender = imageCounter / FRAMES_PER_IMAGE_CHANGE;
			}
		} else if (left) {
			if (isJumping || isFalling) {
				imageToRender = JUMPING_LEFT_IMG;
			} else {
				imageToRender = imageCounter / FRAMES_PER_IMAGE_CHANGE + ANIMATION_FRAME_COUNT;
			}
		}

		if (++imageCounter / FRAMES_PER_IMAGE_CHANGE == ANIMATION_FRAME_COUNT) {
			imageCounter = 0;
		}
		lastImage = imageToRender;
		return getImages()[imageToRender];
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

	public abstract void walk();

	public boolean isActive() {
		return isActive;
	}
	
	public void setActive(boolean isActive) {
		this.isActive = isActive;
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
		currentJumpSpeed = jumpSpeed;
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

	public void setMoveSpeed(double moveSpeed) {
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
		isFallingHandled = false;
	}

	public double getTerminalVelocity() {
		return getHeight() / TERMINAL_VELOCITY;
	}

	public abstract void die();

	public boolean isFallingHandled() {
		return isFallingHandled;
	}

	public void setFallingHandled(boolean isFallingHandled) {
		this.isFallingHandled = isFallingHandled;
	}

	public void setJumpSpeed(double jumpSpeed) {
		this.jumpSpeed = jumpSpeed;
	}
}