package com.isaactsmith.platformer.obj.unit;

import java.awt.image.BufferedImage;

public class OrcEnemy extends EnemyUnit {

	private static final double defaultMovespeed = 1;

	public OrcEnemy(int x, int y, BufferedImage[] images) {
		super(x, y, images, defaultMovespeed);
		setSmart(true);
		setCanJump(false);
	}

	public OrcEnemy(int x, int y, BufferedImage[] images, double moveSpeed) {
		super(x, y, images, moveSpeed);
		setSmart(true);
		setCanJump(false);
	}
}
