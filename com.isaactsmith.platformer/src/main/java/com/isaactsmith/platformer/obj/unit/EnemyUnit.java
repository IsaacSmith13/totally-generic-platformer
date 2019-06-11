package com.isaactsmith.platformer.obj.unit;

import java.awt.image.BufferedImage;

public abstract class EnemyUnit extends Unit {
	
	private static final boolean isEnemy = true;

	public EnemyUnit(int x, int y, BufferedImage[] images) {
		super(x, y, images);
	}

	public static boolean isEnemy() {
		return isEnemy;
	}
	
	@Override
	public void walk() {
		if (isRight() && !willCollideRight()) {
			setX(getX() + getMoveSpeed());
		}
		if (isLeft() && !willCollideLeft()) {
			setX(getX() - getMoveSpeed());
		}
	}
}
