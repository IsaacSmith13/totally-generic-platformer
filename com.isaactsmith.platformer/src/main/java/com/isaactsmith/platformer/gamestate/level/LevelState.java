package com.isaactsmith.platformer.gamestate.level;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import com.isaactsmith.platformer.gamestate.GameState;
import com.isaactsmith.platformer.handler.FrameHandler;
import com.isaactsmith.platformer.handler.GameStateHandler;
import com.isaactsmith.platformer.handler.UnitHandler;
import com.isaactsmith.platformer.levelReadWrite.LevelReader;
import com.isaactsmith.platformer.levelReadWrite.LevelWriter;
import com.isaactsmith.platformer.obj.Tile;
import com.isaactsmith.platformer.obj.unit.PlayerUnit;

public abstract class LevelState extends GameState {

	private String levelFilePath;
	private PlayerUnit player;
	private List<Tile> terrain;
	private UnitHandler unitHandler;

	public LevelState(GameStateHandler gameStateHandler, String levelFilePath) {
		super(gameStateHandler);
		this.levelFilePath = levelFilePath;
		init();
	}

	@Override
	public void init() {
		// Call LevelWriter to "randomly" generate a level and write it to the file at
		// levelFilePath
		LevelWriter.writeLevel(levelFilePath);
		// Call LevelReader to read the file and generate tiles, enemies, and a player
		// at the specified locations
		LevelReader levelReader = new LevelReader();
		levelReader.loadLevel(levelFilePath);
		player = levelReader.getPlayer();
		terrain = levelReader.getTerrain();
		// enemies = levelReader.getEnemies();
		unitHandler = new UnitHandler(terrain);
	}

	@Override
	public void tick() {
		unitHandler.tick(player);
		// foreach enemy, tick
	}

	@Override
	public void paint(Graphics g) {
		try {
			g.setColor(new Color(135, 206, 235));
			g.fillRect(0, 0, FrameHandler.WINDOW_WIDTH, FrameHandler.WINDOW_HEIGHT);
			
			for (int i = 0, terrainSize = terrain.size(); i < terrainSize; i++) {
				terrain.get(i).paint(g);
			}
			// for (EnemyUnit enemy : enemies) {
			// enemy.paint(g);
			// }
			player.paint(g);
		} catch (Exception e) {
			// Do nothing, sometimes list of tiles not complete in the first frame or two of
			// the game
		}
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
