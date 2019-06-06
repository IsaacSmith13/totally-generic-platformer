package com.isaactsmith.platformer.obj;

import java.awt.image.BufferedImage;

public abstract class Enemy extends Unit {
	
	private static final boolean isEnemy = true;

	public Enemy(int x, int y, BufferedImage image) {
		super(x, y, image);
	}

	public static boolean isEnemy() {
		return isEnemy;
	}
}
