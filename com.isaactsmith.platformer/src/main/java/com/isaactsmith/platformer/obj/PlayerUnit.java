package com.isaactsmith.platformer.obj;

import java.awt.image.BufferedImage;

public class PlayerUnit extends Unit {

	private static final int LEFT = 0;
	private static final int RIGHT = 1;
	private static final int STOP = 2;

	public PlayerUnit(int x, int y, BufferedImage image) {
		super(x, y, image);
	}

	@Override
	public void jump() {
		setYVelocity(-8);
		setJumping(true);
		setY((int) getY() - 4);
	}

	@Override
	public void move(int direction) {
		switch(direction) {
		case(LEFT):
			setXVelocity(3);
			break;
		case(RIGHT):
			setXVelocity(-3);
			break;
		case(STOP):
			setXVelocity(0);
		}
	}
}
