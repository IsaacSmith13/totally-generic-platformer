package com.isaactsmith.platformer.handler;

import java.awt.Graphics;
import java.util.Stack;

import javax.swing.SwingWorker;

import com.isaactsmith.platformer.gamestate.GameState;
import com.isaactsmith.platformer.gamestate.LevelState;
import com.isaactsmith.platformer.gamestate.MenuState;
import com.isaactsmith.platformer.obj.unit.PlayerUnit;

public class GameStateHandler {

	private static final int MENU_LEVEL_NUMBER = 0;
	private static final int MENU_LOADING_TIME = 3000;
	private final Stack<GameState> gameStates = new Stack<GameState>();

	public GameStateHandler() {
		gameStates.push(new MenuState(this));
		gameStates.push(new MenuState(this));
	}

	public void tick() {
		gameStates.peek().tick();
	}

	public void paint(Graphics g) {
		gameStates.peek().paint(g);
	}

	public void keyPressed(int e) {
		if (!FrameHandler.isLoading()) {
			gameStates.peek().keyPressed(e);
		}
	}

	public void keyReleased(int e) {
		if (!FrameHandler.isLoading()) {
			gameStates.peek().keyReleased(e);
		}
	}

	public Stack<GameState> getGameStates() {
		return gameStates;
	}

	public void loadLevel(int levelNumber) {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				FrameHandler.setLevelNumber(levelNumber);
				FrameHandler.setLoading(true);
				// Load the next level and save progress
				if (levelNumber != MENU_LEVEL_NUMBER) {
					if (gameStates.size() > 2) {
						gameStates.pop();
					}
					if (levelNumber > 1) {
						SaveHandler.writeSave(levelNumber);
					}
					gameStates.push(new LevelState(GameStateHandler.this, levelNumber));
					// If the level to load is the menu, load a menu instead
				} else {
					gameStates.pop();
					gameStates.push(new MenuState(GameStateHandler.this));
					Thread.sleep(MENU_LOADING_TIME);
				}
				FrameHandler.setLoading(false);
				return null;
			}
		};
		worker.execute();
	}

	public void pause() {
		// Only pause if currently in a level
		if (gameStates.peek() instanceof LevelState) {
			gameStates.push(new MenuState(this));
			((MenuState) gameStates.peek()).setCurrentMenu("pause");
		}
	}

	public void unpause() {
		// Only remove the current gameState if the game is still in the pause menu
		if (gameStates.peek() instanceof MenuState && ((MenuState) gameStates.peek()).getCurrentMenu().equals("pause")) {
			gameStates.pop();
		}
	}

	public void restartLevel() {
		if (gameStates.peek() instanceof MenuState && ((MenuState) gameStates.peek()).getCurrentMenu().equals("pause")) {
			gameStates.pop();
			PlayerUnit.setLives(3);
			int levelNumber = ((LevelState) gameStates.peek()).getCurrentLevelNumber();
			loadLevel(levelNumber);
		}
	}

	public void mainMenu() {
		gameStates.pop();
		gameStates.pop();
		((MenuState) gameStates.peek()).setCurrentMenu("main");
	}
}