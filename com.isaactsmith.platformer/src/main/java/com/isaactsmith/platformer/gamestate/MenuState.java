package com.isaactsmith.platformer.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
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
			// Determines a rectangle along the middle of the screen
			Rectangle drawSpace = new Rectangle(0,
					(int) (windowHeight / (options.length * 2) + (i * Obj.getGlobalSize() * 4)), windowWidth,
					(int) Obj.getGlobalSize() * 2);
			drawCenteredString(g, options[i], drawSpace,
					new Font("helvetica", Font.BOLD, (int) (50 * Obj.getScalar())));
		}
	}

	public static void drawCenteredString(Graphics g, String text, Rectangle drawSpace, Font font) {
		// Get the FontMetrics of the specified font
		FontMetrics metrics = g.getFontMetrics(font);
		int x = drawSpace.x + (drawSpace.width - metrics.stringWidth(text)) / 2;
		// Add the ascent to the Y coordinate, as 2d 0 is the top of the screen
		int y = drawSpace.y + ((drawSpace.height - metrics.getHeight()) / 2) + metrics.getAscent();
		g.setFont(font);
		g.drawString(text, x, y);
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