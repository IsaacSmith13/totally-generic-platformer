package com.isaactsmith.platformer.obj.unit;

import java.awt.image.BufferedImage;

public class PlayerUnit extends Unit {

	private static final int LEFT = 0;
	private static final int RIGHT = 1;
	private static final int STOP = 2;

	public PlayerUnit(int x, int y, BufferedImage[] images) {
		super(x, y, images);
	}

	@Override
	public void jump() {
		setYVelocity(-8);
		setY((int) getY() - 4);
	}

	@Override
	public void move(int direction) {
		switch(direction) {
		case(LEFT):
			setXVelocity(-3);
			break;
		case(RIGHT):
			setXVelocity(3);
			break;
		case(STOP):
			setXVelocity(0);
		}
	}
}