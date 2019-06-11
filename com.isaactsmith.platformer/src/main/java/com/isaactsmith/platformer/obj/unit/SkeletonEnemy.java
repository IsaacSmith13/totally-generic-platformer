package com.isaactsmith.platformer.obj.unit;

import java.awt.image.BufferedImage;
import java.util.List;

public class SkeletonEnemy extends EnemyUnit {

	public SkeletonEnemy(int x, int y, BufferedImage[] images, List<EnemyUnit> enemies) {
		super(x, y, images, enemies);
	}
}
