package com.isaactsmith.platformer.obj.tile;

import com.isaactsmith.platformer.obj.Obj;

public class MovingTile extends PassableTile {

	private int leftLimit;
	private int rightLimit;
	private int moveSpeed = 1;

	public MovingTile(int x, int y, int id, int width, int rightLimit, int moveSpeed) {
		super(x, y, id);
		leftLimit = x;
		setWidth(width);
		this.rightLimit = x + (rightLimit * Obj.GLOBAL_SIZE);
		this.moveSpeed = moveSpeed;
	}

	public int getLeftLimit() {
		return leftLimit;
	}

	public void setLeftLimit(int leftLimit) {
		this.leftLimit = leftLimit;
	}

	public int getRightLimit() {
		return rightLimit;
	}

	public void setRightLimit(int rightLimit) {
		this.rightLimit = rightLimit;
	}

	public int getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(int moveSpeed) {
		this.moveSpeed = moveSpeed;
	}
}
