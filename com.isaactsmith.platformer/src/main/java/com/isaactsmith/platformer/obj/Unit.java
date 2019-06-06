package com.isaactsmith.platformer.obj;

import java.awt.image.BufferedImage;

public abstract class Unit extends ForegroundObj {
	
	private boolean isAlive;

	public Unit(int x, int y, BufferedImage image) {
		super(x, y, image);
		isAlive = true;
	}
	
	abstract void jump();
	
	abstract void move(int direction);

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
}
