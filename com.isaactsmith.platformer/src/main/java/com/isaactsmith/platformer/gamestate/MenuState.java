package com.isaactsmith.platformer.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import com.isaactsmith.platformer.handler.CenteredStringHandler;
import com.isaactsmith.platformer.handler.FrameHandler;
import com.isaactsmith.platformer.handler.GameStateHandler;
import com.isaactsmith.platformer.handler.SaveHandler;
import com.isaactsmith.platformer.obj.Obj;

public class MenuState extends GameState {

	private static final String[] MAIN_OPTIONS = { "Start", "Levels", "Quit", "Full Screen", "Windowed" };
	private static final String[] PAUSE_OPTIONS = { "Resume", "Main Menu", "Quit" };
	private String[] LEVEL_OPTIONS;
	private int currentSelection = 0;
	private String currentMenu = "main";

	public MenuState(GameStateHandler gameStateHandler) {
		super(gameStateHandler);
	}

	public MenuState(GameStateHandler gameStateHandler, boolean isPauseMenu) {
		this(gameStateHandler);
		if (isPauseMenu) {
			currentMenu = "pause";
		}
	}

	@Override
	public void tick() {
	}

	@Override
	public void paint(Graphics g) {
		// Paint blue background
		g.setColor(new Color(135, 206, 235));
		g.fillRect(0, 0, FrameHandler.getWindowWidth(), FrameHandler.getWindowHeight());

		switch (currentMenu) {
		case ("main"):
			paintMenu(g, MAIN_OPTIONS);
			break;
		case ("levels"):
			paintMenu(g, LEVEL_OPTIONS);
			break;
		case ("pause"):
			paintMenu(g, PAUSE_OPTIONS);
		}
	}

	private void paintMenu(Graphics g, String[] options) {
		for (int i = 0; i < options.length; i++) {
			if (i == currentSelection) {
				g.setColor(new Color(242, 2, 190));
			} else {
				g.setColor(Color.BLACK);
			}
			// Determines a rectangle along the middle of the screen
			Rectangle drawSpace = new Rectangle(0,
					FrameHandler.getWindowHeight() / (options.length * 2) + (i * Obj.getGlobalSize() * 4),
					FrameHandler.getWindowWidth(), Obj.getGlobalSize() * 2);
			CenteredStringHandler.drawCenteredString(g, options[i], drawSpace,
					new Font("helvetica", Font.BOLD, (int) (50 * Obj.getScalar())));
		}
	}

	

	@Override
	public void keyPressed(int e) {
		int optionsLength = getOptionsLength();
		switch (e) {
		case (KeyEvent.VK_DOWN):
			e = KeyEvent.VK_S;
		case (KeyEvent.VK_S):
			currentSelection = currentSelection >= optionsLength - 1 ? 0 : ++currentSelection;
			break;
		case (KeyEvent.VK_UP):
			e = KeyEvent.VK_W;
		case (KeyEvent.VK_W):
			currentSelection = currentSelection < 1 ? optionsLength - 1 : --currentSelection;
			break;
		case (KeyEvent.VK_SPACE):
			e = KeyEvent.VK_ENTER;
		case (KeyEvent.VK_ENTER):
			switch (currentMenu) {
			case ("main"):
				selectOption(currentSelection);
				break;
			case ("levels"):
				selectLevel(currentSelection);
				break;
			case ("pause"):
				selectPause(currentSelection);
				break;
			default:
				break;
			}
		case (KeyEvent.VK_ESCAPE):
			if (currentMenu == "pause") {
				gameStateHandler.unpause();
			}
			break;
		default:
			break;
		}
	}

	private int getOptionsLength() {
		switch (currentMenu) {
		case ("main"):
			return MAIN_OPTIONS.length;
		case ("levels"):
			return LEVEL_OPTIONS.length;
		case ("pause"):
			return PAUSE_OPTIONS.length;
		default:
			return 0;
		}
	}

	private void selectPause(int currentSelection) {
		switch (currentSelection) {
		case (0):
			gameStateHandler.unpause();
			break;
		case (1):
			gameStateHandler.mainMenu();
			break;
		case (2):
			System.exit(0);
			break;
		default:
			break;
		}
	}

	private void selectLevel(int currentSelection) {
		// If the selection is not the last option, load the selected level number
		if (currentSelection < LEVEL_OPTIONS.length - 1) {
			gameStateHandler.loadLevel(currentSelection + 1);
			// Otherwise go back to the menu
		} else {
			currentMenu = "main";
		}
	}

	private void selectOption(int currentSelection) {
		switch (currentSelection) {
		case (0):
			gameStateHandler.loadLevel(1);
			break;
		case (1):
			levelSelector();
			break;
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
	}

	private void levelSelector() {
		int highestLevel = SaveHandler.readSave();
		LEVEL_OPTIONS = new String[highestLevel + 1];
		LEVEL_OPTIONS[LEVEL_OPTIONS.length - 1] = "Back";
		for (int i = 0; i < highestLevel; i++) {
			LEVEL_OPTIONS[i] = "Level " + (i + 1);
		}
		currentMenu = "levels";
	}

	@Override
	public void keyReleased(int e) {

	}

	public String getCurrentMenu() {
		return currentMenu;
	}

	public void setCurrentMenu(String currentMenu) {
		this.currentMenu = currentMenu;
		currentSelection = 0;
	}
}