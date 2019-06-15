package com.isaactsmith.platformer.obj.unit;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.isaactsmith.platformer.handler.FrameHandler;
import com.isaactsmith.platformer.handler.TickHandler;

public abstract class EnemyUnit extends Unit {

	private static final int AVG_TICKS_BETWEEN_JUMPS = 1000;
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

	public EnemyUnit(int x, int y, BufferedImage[] images, double moveSpeed, double jumpSpeed) {
		this(x, y, images, moveSpeed);
		setJumpSpeed(jumpSpeed);
	}

	@Override
	public void paint(Graphics g) {
		int x = (int) Math.round(getX() - TickHandler.getXOffset());
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
		if ((int) (Math.random() * AVG_TICKS_BETWEEN_JUMPS) == 1) {
			jump();
		}
		if (!isRight() && !isLeft()) {
			if (TickHandler.getXOffset() + (FrameHandler.WINDOW_WIDTH / 2) > getX()) {
				setRight(true);
			} else {
				setLeft(true);
			}
		} else if (isRight()) {
			if (!willCollideRight()) {
				setX(getX() + getMoveSpeed());
			} else {
				setRight(false);
				setLeft(true);
			}
		} else if (isLeft()) {
			if (!willCollideLeft()) {
				setX(getX() - getMoveSpeed());
			} else {
				setLeft(false);
				setRight(true);
			}
		}
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
