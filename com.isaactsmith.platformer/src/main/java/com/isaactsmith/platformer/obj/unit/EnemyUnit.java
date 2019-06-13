package com.isaactsmith.platformer.obj.unit;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import com.isaactsmith.platformer.handler.FrameHandler;
import com.isaactsmith.platformer.handler.UnitHandler;

public abstract class EnemyUnit extends Unit {

	private List<EnemyUnit> enemies;
	private boolean isActive = false;

	public EnemyUnit(int x, int y, BufferedImage[] images, List<EnemyUnit> enemies) {
		super(x, y, images);
		this.enemies = enemies;
	}

	public EnemyUnit(int x, int y, BufferedImage[] images, List<EnemyUnit> enemies, double moveSpeed) {
		this(x, y, images, enemies);
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
		enemies.remove(this);
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
}
