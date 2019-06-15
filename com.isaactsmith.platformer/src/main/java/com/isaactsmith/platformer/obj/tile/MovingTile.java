package com.isaactsmith.platformer.obj.tile;

import com.isaactsmith.platformer.handler.UnitHandler;

public class MovingTile extends Tile {

	private int leftLimit;
	private int rightLimit;
	private int moveSpeed = 1;

	public MovingTile(int x, int y, int id, int rightLimit) {
		super(x, y, id);
		leftLimit = x;
		this.rightLimit = rightLimit;
	}

	public MovingTile(int x, int y, int id, int rightLimit, int moveSpeed) {
		this(x, y, id, rightLimit);
		this.moveSpeed = moveSpeed;
	}

	public void tick() {
		if (getX() + getWidth() - UnitHandler.getXOffset() >= rightLimit - UnitHandler.getXOffset() && moveSpeed != 1) {
			moveSpeed *= -1;
		}

		if (getX() - UnitHandler.getXOffset() <= -leftLimit - UnitHandler.getXOffset() && moveSpeed != 1) {
			moveSpeed *= -1;
		}

		setX(getX() + moveSpeed);
	}
}
