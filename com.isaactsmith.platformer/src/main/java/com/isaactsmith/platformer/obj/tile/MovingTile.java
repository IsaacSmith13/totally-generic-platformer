package com.isaactsmith.platformer.obj.tile;

import java.awt.Rectangle;

import com.isaactsmith.platformer.obj.Obj;

public class MovingTile extends PassableTile {

	private final int leftLimit;
	private final int rightLimit;
	private int moveSpeed;

	public MovingTile(int x, int y, int id, int width, int rightLimit, int moveSpeed) {
		super(x, y, id);
		leftLimit = x;
		setWidth(width);
		this.rightLimit = x + (rightLimit * Obj.GLOBAL_SIZE);
		this.moveSpeed = moveSpeed;
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
