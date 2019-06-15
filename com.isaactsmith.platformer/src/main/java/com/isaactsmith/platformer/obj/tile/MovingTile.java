package com.isaactsmith.platformer.obj.tile;

import java.awt.Rectangle;

import com.isaactsmith.platformer.obj.Obj;

public class MovingTile extends PassableTile {

	private int leftLimit;
	private int rightLimit;
	private int moveSpeed;

	public MovingTile(int x, int y, int id, int width, int rightLimit, int moveSpeed) {
		super(x, y, id);
		leftLimit = x;
		setWidth(width);
		this.rightLimit = x + (rightLimit * Obj.GLOBAL_SIZE);
		this.moveSpeed = moveSpeed;
		System.out.println(leftLimit);
		System.out.println(this.rightLimit);
		System.out.println(width);
	}
	
	@Override
	public Rectangle getRect() {
		return new Rectangle((int) getX(), (int) getY(), getWidth(), getHeight());
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
