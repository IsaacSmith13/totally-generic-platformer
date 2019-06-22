package com.isaactsmith.platformer.obj.unit;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.isaactsmith.platformer.handler.FrameHandler;
import com.isaactsmith.platformer.handler.TickHandler;

public abstract class EnemyUnit extends Unit {

	private static final int AVG_TICKS_BETWEEN_JUMPS = 1000;
	private static final int DEATH_DELAY_MS = 250;
	private final int startX;
	private final int startY;
	private double timeOfDeath;
	private boolean isDying = false;

	public EnemyUnit(int x, int y, BufferedImage[] images) {
		super(x, y, images);
		this.startX = x;
		this.startY = y;
	}

	public EnemyUnit(int x, int y, BufferedImage[] images, double moveSpeed) {
		this(x, y, images);
		setMoveSpeed(moveSpeed * SCALAR);
	}

	public EnemyUnit(int x, int y, BufferedImage[] images, double moveSpeed, double jumpSpeed) {
		this(x, y, images, moveSpeed);
		setJumpSpeed(jumpSpeed * SCALAR);
	}

	@Override
	public void paint(Graphics g) {
		int x = (int) Math.round(getX() - TickHandler.getXOffset());
		int y = (int) Math.round(getY());
		int maxX = FrameHandler.WINDOW_WIDTH + GLOBAL_SIZE;
		int maxY = FrameHandler.WINDOW_HEIGHT + GLOBAL_SIZE;
		int min = -GLOBAL_SIZE;
		if (x <= maxX && (x - 1) >= min && y <= maxY && (y - 1) >= min) {
			g.drawImage(getImageToRender(), x - getPaintPadding(), y - getPaintPadding(),
					getHeight() + getPaintPadding() * 2, getWidth() + getPaintPadding() * 2, null);
			if (!isActive() && x <= maxX + (min * 3)) {
				setActive(true);
			}
		}
	}

	public void die() {
		if (System.currentTimeMillis() > timeOfDeath + DEATH_DELAY_MS || timeOfDeath == -1) {
			setY(-1000);
			setActive(false);
			timeOfDeath = -1;
			setDying(false);
		}
	}

	public void handleDeath() {
		timeOfDeath = System.currentTimeMillis();
		setDying(true);
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

	public boolean isDying() {
		return isDying;
	}

	public void setDying(boolean isDying) {
		this.isDying = isDying;
	}
}
