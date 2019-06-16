package com.isaactsmith.platformer.handler;

import java.awt.Graphics;
import java.util.Stack;

import javax.swing.SwingWorker;

import com.isaactsmith.platformer.gamestate.GameState;
import com.isaactsmith.platformer.gamestate.LevelState;
import com.isaactsmith.platformer.gamestate.MenuState;

public class GameStateHandler {

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
		gameStates.peek().keyPressed(e);
	}

	public void keyReleased(int e) {
		gameStates.peek().keyReleased(e);
	}

	public Stack<GameState> getGameStates() {
		return gameStates;
	}

	public void loadLevel(int levelNumber) {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				FrameHandler.setLevelNumber(levelNumber);
				gameStates.pop();
				FrameHandler.setLoading(true);
				gameStates.push(new LevelState(GameStateHandler.this, levelNumber));
				FrameHandler.setLoading(false);
				return null;
			}
		};
		worker.execute();
	}
}