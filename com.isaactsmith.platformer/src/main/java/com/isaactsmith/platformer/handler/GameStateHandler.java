package com.isaactsmith.platformer.handler;

import java.awt.Graphics;
import java.util.Stack;

import com.isaactsmith.platformer.gamestate.GameState;
import com.isaactsmith.platformer.gamestate.MenuState;

public class GameStateHandler {

	private Stack<GameState> gameStates = new Stack<GameState>();

	public GameStateHandler() {
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
}