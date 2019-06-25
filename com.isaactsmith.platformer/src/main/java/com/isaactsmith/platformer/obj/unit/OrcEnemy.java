package com.isaactsmith.platformer.obj.unit;

import java.awt.image.BufferedImage;

public class OrcEnemy extends EnemyUnit {
	
	public OrcEnemy(int x, int y, BufferedImage[] images) {
		super(x, y, images);
		this.setSmart(true);
	}

	public OrcEnemy(int x, int y, BufferedImage[] images, double moveSpeed) {
		super(x, y, images, moveSpeed);
		this.setSmart(true);
	}

	public OrcEnemy(int x, int y, BufferedImage[] images, double moveSpeed, double jumpSpeed) {
		super(x, y, images, moveSpeed, jumpSpeed);
		this.setSmart(true);
	}
}
