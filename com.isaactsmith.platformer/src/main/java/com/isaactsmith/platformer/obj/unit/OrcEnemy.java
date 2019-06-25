package com.isaactsmith.platformer.obj.unit;

import java.awt.image.BufferedImage;

import com.isaactsmith.platformer.handler.FrameHandler;
import com.isaactsmith.platformer.handler.TickHandler;

public class OrcEnemy extends EnemyUnit {

	private static final double defaultMovespeed = 1.5;
	private static final int defaultJumpSpeed = 7;
	private boolean willFallRight;
	private boolean willFallLeft;

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

	@Override
	public void walk() {
		// If the unit is not moving, go towards the player
		if (!isRight() && !isLeft()) {
			if (TickHandler.getXOffset() + (FrameHandler.getWindowWidth() / 2) > getX()) {
				setRight(true);
			} else {
				setLeft(true);
			}
			// Otherwise move / turn around depending on if the unit will collide or not
		} else if (isRight()) {
			if (!willCollideRight() && !willFallRight) {
				setX(getX() + getMoveSpeed());
			} else {
				setRight(false);
				setLeft(true);
			}
		} else if (isLeft()) {
			if (!willCollideLeft() && !willFallLeft) {
				setX(getX() - getMoveSpeed());
			} else {
				setLeft(false);
				setRight(true);
			}
		}
	}
}
