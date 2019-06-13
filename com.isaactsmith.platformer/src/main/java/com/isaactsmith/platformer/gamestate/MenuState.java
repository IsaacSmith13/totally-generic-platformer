package com.isaactsmith.platformer.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Stack;

import com.isaactsmith.platformer.handler.FrameHandler;
import com.isaactsmith.platformer.handler.GameStateHandler;

public class MenuState extends GameState {

	private static final String level1Path = "Level1.level";

	String[] options = { "Start", "Levels", "Quit" };
	private int currentSelection = 0;

	public MenuState(GameStateHandler gameStateHandler) {
		super(gameStateHandler);
	}

	@Override
	public void tick() {
	}

	@Override
	public void paint(Graphics g) {

		g.setColor(new Color(135, 206, 235));
		g.fillRect(0, 0, FrameHandler.WINDOW_WIDTH, FrameHandler.WINDOW_HEIGHT);

		for (int i = 0; i < options.length; i++) {
			if (i == currentSelection) {
				g.setColor(new Color(242, 2, 190));
			} else {
				g.setColor(Color.BLACK);
			}
			g.setFont(new Font("helvetica", Font.BOLD, 50));
			g.drawString(options[i], (int) (FrameHandler.WINDOW_WIDTH / 2.3), 200 + i * 150);
		}
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
			switch (currentSelection) {
			case (0):
				Stack<GameState> gameStates = gameStateHandler.getGameStates();
				gameStates.push(new LevelState(gameStateHandler, level1Path));
				break;
			case (1):
				break;
			// todo level selector
			case (2):
				System.exit(0);
				break;
			}
		default:
			break;
		}
	}

	@Override
	public void keyReleased(int e) {

	}
}