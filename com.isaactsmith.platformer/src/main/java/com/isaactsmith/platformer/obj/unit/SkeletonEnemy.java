package com.isaactsmith.platformer.obj.unit;

import java.awt.image.BufferedImage;

public class SkeletonEnemy extends EnemyUnit {

	public SkeletonEnemy(int x, int y, BufferedImage[] images) {
		super(x, y, images);
	}

	public SkeletonEnemy(int x, int y, BufferedImage[] images, double moveSpeed) {
		super(x, y, images, moveSpeed);
	}

	public SkeletonEnemy(int x, int y, BufferedImage[] images, double moveSpeed, double jumpSpeed) {
		super(x, y, images, moveSpeed, jumpSpeed);
	}
}
