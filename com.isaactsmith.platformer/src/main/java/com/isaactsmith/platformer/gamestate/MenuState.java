package com.isaactsmith.platformer.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import com.isaactsmith.platformer.handler.FrameHandler;
import com.isaactsmith.platformer.handler.GameStateHandler;
import com.isaactsmith.platformer.obj.Obj;

public class MenuState extends GameState {

	private static final String[] options = { "Start", "Levels", "Quit", "Full Screen", "Windowed" };
	private int currentSelection = 0;

	public MenuState(GameStateHandler gameStateHandler) {
		super(gameStateHandler);
	}

	@Override
	public void tick() {
	}

	@Override
	public void paint(Graphics g) {
		int windowWidth = FrameHandler.getWindowWidth();
		int windowHeight = FrameHandler.getWindowHeight();

		g.setColor(new Color(135, 206, 235));
		g.fillRect(0, 0, windowWidth, windowHeight);

		for (int i = 0; i < options.length; i++) {
			if (i == currentSelection) {
				g.setColor(new Color(242, 2, 190));
			} else {
				g.setColor(Color.BLACK);
			}
			g.setFont(new Font("helvetica", Font.BOLD, (int) (50 * Obj.SCALAR)));
			g.drawString(options[i], (int) (windowWidth / 2.3),
					(windowHeight / options.length) + i * (windowHeight / options.length / 2));
		}
	}

	@Override
	public void keyPressed(int e) {
		switch (e) {
		case (KeyEvent.VK_DOWN):
			e = KeyEvent.VK_S;
		case (KeyEvent.VK_S):
			currentSelection = currentSelection >= options.length - 1 ? 0 : ++currentSelection;
			break;
		case (KeyEvent.VK_UP):
			e = KeyEvent.VK_W;
		case (KeyEvent.VK_W):
			currentSelection = currentSelection < 1 ? options.length - 1 : --currentSelection;
			break;
		case (KeyEvent.VK_SPACE):
			e = KeyEvent.VK_ENTER;
		case (KeyEvent.VK_ENTER):
			switch (currentSelection) {
			case (0):
				gameStateHandler.loadLevel(1);
				break;
			case (1):
				break;
			// TODO level selector
			case (2):
				System.exit(0);
				break;
			case (3):
				FrameHandler.setFullScreen();
				break;
			case (4):
				FrameHandler.setWindowed();
				break;
			default:
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