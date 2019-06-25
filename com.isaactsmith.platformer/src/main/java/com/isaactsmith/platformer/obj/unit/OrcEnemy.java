package com.isaactsmith.platformer.obj.unit;

import java.awt.image.BufferedImage;

public class OrcEnemy extends EnemyUnit {
	
	private static final double defaultMovespeed = 1.5;
	private static final int defaultJumpSpeed = 7;
	
	public OrcEnemy(int x, int y, BufferedImage[] images) {
		super(x, y, images, defaultMovespeed, defaultJumpSpeed);
		this.setSmart(true);
	}

	public OrcEnemy(int x, int y, BufferedImage[] images, double moveSpeed) {
		super(x, y, images, moveSpeed, defaultJumpSpeed);
		this.setSmart(true);
	}

	public OrcEnemy(int x, int y, BufferedImage[] images, double moveSpeed, double jumpSpeed) {
		super(x, y, images, moveSpeed, jumpSpeed);
		this.setSmart(true);
	}
}
