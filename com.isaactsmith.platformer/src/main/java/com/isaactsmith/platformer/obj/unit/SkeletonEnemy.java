package com.isaactsmith.platformer.obj.unit;

import java.awt.image.BufferedImage;

public class SkeletonEnemy extends EnemyUnit {
	
	private static final double defaultMovespeed = 1;
	private static final double defaultJumpSpeed = 6;

	public SkeletonEnemy(int x, int y, BufferedImage[] images) {
		super(x, y, images, defaultMovespeed, defaultJumpSpeed);
	}

	public SkeletonEnemy(int x, int y, BufferedImage[] images, double moveSpeed) {
		super(x, y, images, moveSpeed, defaultJumpSpeed);
	}

	public SkeletonEnemy(int x, int y, BufferedImage[] images, double moveSpeed, double jumpSpeed) {
		super(x, y, images, moveSpeed, jumpSpeed);
	}
}
