package com.isaactsmith.platformer.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import com.isaactsmith.platformer.handler.FrameHandler;
import com.isaactsmith.platformer.handler.GameStateHandler;
import com.isaactsmith.platformer.handler.UnitHandler;
import com.isaactsmith.platformer.level.LevelLoader;
import com.isaactsmith.platformer.obj.Tile;
import com.isaactsmith.platformer.obj.unit.EnemyUnit;
import com.isaactsmith.platformer.obj.unit.PlayerUnit;

public class LevelState extends GameState {

	private LevelLoader currentLevel;
	private Tile[][] terrain;
	private List<EnemyUnit> enemies;
	private PlayerUnit player;
	private UnitHandler unitHandler;

	public LevelState(GameStateHandler gameStateHandler, String levelFilePath) {
		super(gameStateHandler);
		currentLevel = new LevelLoader(levelFilePath);
		terrain = currentLevel.getTerrain();
		enemies = currentLevel.getEnemies();
		player = currentLevel.getPlayer();
		unitHandler = new UnitHandler(terrain);
	}

	@Override
	public void tick() {
		for (int i = 0; i < enemies.size(); i++) {
			unitHandler.tick(enemies.get(i));
		}
		unitHandler.tick(player);
	}

	@Override
	public void paint(Graphics g) {
		// Paint background
		g.setColor(new Color(135, 206, 235));
		g.fillRect(0, 0, FrameHandler.WINDOW_WIDTH, FrameHandler.WINDOW_HEIGHT);
		// Paint tiles
		for (int y = 0, height = terrain.length; y < height; y++) {
			for (int x = 0, width = terrain[y].length; x < width; x++) {
				if (terrain[y][x] != null) {
					terrain[y][x].paint(g);
				}
			}
		}
		try {
			// Paint lives
			g.setColor(Color.BLACK);
			g.setFont(new Font("helvetica", Font.PLAIN, 16));
			g.drawString("Lives: ", (int) (FrameHandler.WINDOW_WIDTH / 2 - 50), 25);
			for (int i = player.getLives(); i > 0; i--) {
				g.drawImage(player.getImages()[0], (int) (FrameHandler.WINDOW_WIDTH / 2 + (i * 12) - 15), 10, 16, 16,
						null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Paint enemies
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).paint(g);
		}
		// Paint player
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