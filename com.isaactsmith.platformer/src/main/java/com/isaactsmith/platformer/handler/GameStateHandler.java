package com.isaactsmith.platformer.handler;

import java.awt.Graphics;
import java.util.Stack;

import com.isaactsmith.platformer.gamestate.GameState;

public class GameStateHandler {

	private Stack<GameState> gameStates = new Stack<GameState>();

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
}
