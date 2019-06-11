package com.isaactsmith.platformer.gamestate;

import java.awt.Color;
import java.awt.Graphics;

import com.isaactsmith.platformer.handler.FrameHandler;
import com.isaactsmith.platformer.handler.GameStateHandler;
import com.isaactsmith.platformer.handler.UnitHandler;
import com.isaactsmith.platformer.level.LevelLoader;
import com.isaactsmith.platformer.obj.Tile;
import com.isaactsmith.platformer.obj.unit.PlayerUnit;

public class LevelState extends GameState {

	private LevelLoader currentLevel;
	private Tile[][] terrain;
	private PlayerUnit player;
	private UnitHandler unitHandler;
	private String levelFilePath;

	public LevelState(GameStateHandler gameStateHandler, String levelFilePath) {
		super(gameStateHandler);
		this.levelFilePath = levelFilePath;
		init();
	}

	@Override
	public void init() {
		currentLevel = new LevelLoader(levelFilePath);
		terrain = currentLevel.getTerrain();
		player = currentLevel.getPlayer();
		unitHandler = new UnitHandler(terrain);
	}

	@Override
	public void tick() {
		unitHandler.tick(player);
		// foreach enemy, tick
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(new Color(135, 206, 235));
		g.fillRect(0, 0, FrameHandler.WINDOW_WIDTH, FrameHandler.WINDOW_HEIGHT);

		for (int y = 0, height = terrain.length; y < height; y++) {
			for (int x = 0, width = terrain[y].length; x < width; x++) {
				if(terrain[y][x] != null) {
					terrain[y][x].paint(g);
				}
			}
		}

		player.paint(g);
	}

	@Override
	public void keyPressed(int e) {
		player.keyPressed(e);
	}

	@Override
	public void keyReleased(int e) {
		player.keyReleased(e);
	}
}