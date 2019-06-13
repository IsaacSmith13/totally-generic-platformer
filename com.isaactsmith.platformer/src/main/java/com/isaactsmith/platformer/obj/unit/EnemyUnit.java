package com.isaactsmith.platformer.obj.unit;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.isaactsmith.platformer.handler.FrameHandler;
import com.isaactsmith.platformer.handler.UnitHandler;

public abstract class EnemyUnit extends Unit {

	private boolean isActive = false;
	private int startX;
	private int startY;

	public EnemyUnit(int x, int y, BufferedImage[] images) {
		super(x, y, images);
		this.startX = x;
		this.startY = y;
	}

	public EnemyUnit(int x, int y, BufferedImage[] images, double moveSpeed) {
		this(x, y, images);
		setMoveSpeed(moveSpeed);
	}

	@Override
	public void paint(Graphics g) {
		int x = (int) Math.round(getX() - UnitHandler.getXOffset());
		int y = (int) Math.round(getY());
		if (x <= FrameHandler.WINDOW_WIDTH + (GLOBAL_SIZE * 2) && (x - 1) >= -GLOBAL_SIZE * 2
				&& y <= FrameHandler.WINDOW_HEIGHT + (GLOBAL_SIZE * 2) && (y - 1) >= -GLOBAL_SIZE * 2) {
			setActive(true);
			g.drawImage(getImageToRender(), x, y, getHeight(), getWidth(), null);
		}
	}

	public void die() {
		setY(-1000);
		setActive(false);
	}

	@Override
	public void walk() {
		int randomNum = (int) (Math.random() * 1000);
		if (randomNum < 2) {
			setRight(false);
			setLeft(true);
		} else if (randomNum < 5) {
			setLeft(false);
			setRight(true);
		} else if (randomNum < 20) {
			jump();
		}
		if (isRight() && !willCollideRight()) {
			setX(getX() + getMoveSpeed());
		}
		if (isLeft() && !willCollideLeft()) {
			setX(getX() - getMoveSpeed());
		}
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}

	public void reset() {
		setLocation(startX, startY);
	}
}
