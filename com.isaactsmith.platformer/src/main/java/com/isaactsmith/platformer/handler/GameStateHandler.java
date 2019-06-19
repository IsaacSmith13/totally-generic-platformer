package com.isaactsmith.platformer.handler;

import java.awt.Graphics;
import java.util.Stack;

import javax.swing.SwingWorker;

import com.isaactsmith.platformer.gamestate.GameState;
import com.isaactsmith.platformer.gamestate.LevelState;
import com.isaactsmith.platformer.gamestate.MenuState;

public class GameStateHandler {

	private static final int MENU_LEVEL_NUMBER = 0;
	private Stack<GameState> gameStates = new Stack<GameState>();

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
				if (levelNumber != MENU_LEVEL_NUMBER) {
					gameStates.pop();
					gameStates.push(new LevelState(GameStateHandler.this, levelNumber));
				} else {
					gameStates.pop();
					gameStates.push(new MenuState(GameStateHandler.this));
					Thread.sleep(3000);
				}
				FrameHandler.setLoading(false);
				return null;
			}
		};
		worker.execute();
	}
}