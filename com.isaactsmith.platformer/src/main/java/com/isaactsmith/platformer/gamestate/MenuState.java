package com.isaactsmith.platformer.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import com.isaactsmith.platformer.handler.FrameHandler;
import com.isaactsmith.platformer.handler.GameStateHandler;

public class MenuState extends GameState {

	String[] options = { "Start", "Level Selection", "Quit" };
	private int currentSelection = 0;

	public MenuState(GameStateHandler gameStateHandler) {
		super(gameStateHandler);
	}

	@Override
	public void init() {

	}

	@Override
	public void tick() {

	}

	@Override
	public void paint(Graphics g) {
		for (int i = 0; i < options.length; i++) {
			if (i == currentSelection) {
				g.setColor(new Color(242, 2, 190));
			} else {
				g.setColor(Color.BLACK);
			}
			g.setFont(new Font("ArialBlack", Font.PLAIN, 60));

			g.drawString(options[i], FrameHandler.WINDOW_WIDTH / 2 - 60, 150 + i * 150);
		}

		g.setColor(new Color(135, 206, 235));
		g.fillRect(0, 0, FrameHandler.WINDOW_WIDTH, FrameHandler.WINDOW_HEIGHT);
	}

	@Override
	public void keyPressed(int e) {
		switch (e) {
		case (KeyEvent.VK_DOWN):
			currentSelection = currentSelection >= options.length - 1 ? 0 : ++currentSelection;
			break;
		case (KeyEvent.VK_S):
			currentSelection = currentSelection >= options.length - 1 ? 0 : ++currentSelection;
			break;
		case (KeyEvent.VK_UP):
			currentSelection = currentSelection < 1 ? options.length - 1 : --currentSelection;
			break;
		case (KeyEvent.VK_W):
			currentSelection = currentSelection < 1 ? options.length - 1 : --currentSelection;
			break;
		case (KeyEvent.VK_ENTER):
			switch(currentSelection) {
			case(0):
				// todo play
				break;
			case(1):
				// todo level selector
			case(2):
				System.exit(0);
			}
		default:
			break;
		}
	}

	@Override
	public void keyReleased(int e) {

	}

}