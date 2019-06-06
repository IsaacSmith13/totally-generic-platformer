package com.isaactsmith.platformer.obj;

import java.awt.image.BufferedImage;

public class Player extends Unit {

	public Player(int x, int y, BufferedImage image) {
		super(x, y, image);
	}

	@Override
	void jump() {
		// TODO jump method - player
		// Should jump on spacebar press
	}

	@Override
	void move(int direction) {
		// TODO move method - player
		// Should move with W, A, S or D
		// Remove parameter if it is decided to determine key presses here
	}

}
