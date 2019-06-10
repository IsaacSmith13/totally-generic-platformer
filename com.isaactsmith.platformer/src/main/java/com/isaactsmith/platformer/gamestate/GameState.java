package com.isaactsmith.platformer.gamestate;

import java.awt.Graphics;

import com.isaactsmith.platformer.handler.GameStateHandler;

public abstract class GameState {
	GameStateHandler gameStateHandler;
	
	public GameState(GameStateHandler gameStateHandler) {
		this.gameStateHandler = gameStateHandler;
		init();
	}
	
	public abstract void init();
	public abstract void tick();
	public abstract void paint(Graphics g);
	public abstract void keyPressed(int e);
	public abstract void keyReleased(int e);
}