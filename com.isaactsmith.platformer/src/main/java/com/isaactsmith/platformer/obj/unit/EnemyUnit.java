package com.isaactsmith.platformer.obj.unit;

import java.awt.image.BufferedImage;
import java.util.List;

public abstract class EnemyUnit extends Unit {
	
	private static final boolean isEnemy = true;
	private List<EnemyUnit> enemies;

	public EnemyUnit(int x, int y, BufferedImage[] images, List<EnemyUnit> enemies) {
		super(x, y, images);
		this.enemies = enemies;
	}
	
	public EnemyUnit(int x, int y, BufferedImage[] images, List<EnemyUnit> enemies, double moveSpeed) {
		this(x, y, images, enemies);
		setMoveSpeed(moveSpeed);
	}

	public static boolean isEnemy() {
		return isEnemy;
	}
	
	public void die() {
		enemies.remove(this);
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
