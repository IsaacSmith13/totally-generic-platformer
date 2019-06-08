package com.isaactsmith.platformer;

import javax.swing.JFrame;

public class Game {

	private static final String APPLICATION_NAME = "Totally Generic Platformer";

	public static void main(String[] args) {
		// Initialize window
		FrameHandler game = new FrameHandler(new JFrame(APPLICATION_NAME));
		game.run();
	}
}
