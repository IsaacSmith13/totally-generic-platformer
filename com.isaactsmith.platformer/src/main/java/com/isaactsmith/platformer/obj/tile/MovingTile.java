package com.isaactsmith.platformer.obj.tile;

import java.awt.Rectangle;

public class MovingTile extends PassableTile {

	private final int leftLimit;
	private final int rightLimit;
	private int moveSpeed;

	public MovingTile(int x, int y, int id, int width, int rightLimit, int moveSpeed) {
		super(x, y, id);
		leftLimit = x;
		setWidth(width * getGlobalSize());
		this.rightLimit = x + (rightLimit * getGlobalSize());
		this.moveSpeed = (int) (moveSpeed * getScalar());
	}

	public void reset() {
		setX(leftLimit);
	}

	@Override
	public Rectangle getRect() {
		return new Rectangle((int) getX(), (int) getY(), getWidth(), getHeight());
	}

	public int getLeftLimit() {
		return leftLimit;
	}

	public int getRightLimit() {
		return rightLimit;
	}

	public int getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(int moveSpeed) {
		this.moveSpeed = moveSpeed;
	}
}