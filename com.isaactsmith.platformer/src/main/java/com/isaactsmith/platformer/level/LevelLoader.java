package com.isaactsmith.platformer.level;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.isaactsmith.platformer.obj.Obj;
import com.isaactsmith.platformer.obj.tile.BackgroundTile;
import com.isaactsmith.platformer.obj.tile.MovingTile;
import com.isaactsmith.platformer.obj.tile.PassableTile;
import com.isaactsmith.platformer.obj.tile.Tile;
import com.isaactsmith.platformer.obj.unit.EnemyUnit;
import com.isaactsmith.platformer.obj.unit.OrcEnemy;
import com.isaactsmith.platformer.obj.unit.PlayerUnit;
import com.isaactsmith.platformer.obj.unit.SkeletonEnemy;

public class LevelLoader {

	// Max ids for each type of object
	private static final int MAX_PASSABLE_ID = 19;
	private static final int MAX_SOLID_ID = 39;
	// Max moving id is 49
	@SuppressWarnings("unused")
	private static final int MAX_OTHER_ID = 59;
	private static final int MAX_TERRAIN_ID = 79;
	private static final int MAX_ENEMY_ID = 98;
	private static final int WINNING_ID = 99;
	// Object global size
	private final int size = Obj.getGlobalSize();
	// Current level's filepath
	private final String levelpath;
	// Level objects
	private final List<Tile> terrain = new ArrayList<Tile>();
	private final List<Tile> movingTiles = new ArrayList<Tile>();
	private final List<EnemyUnit> enemies = new ArrayList<EnemyUnit>();
	private final PlayerUnit player = new PlayerUnit(ImageLoader.getUnitImagesById(-1));
	private final List<Tile> winningTiles = new ArrayList<Tile>();
	private Tile[][] tiles;
	// Level size
	private int levelWidth;
	private int levelHeight;

	public LevelLoader(int levelNumber) {
		levelpath = "Level" + levelNumber + ".level";
	}

	public void readLevel() {
		try (InputStream in = getClass().getResourceAsStream(levelpath);
				BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			levelWidth = Integer.parseInt(reader.readLine());
			levelHeight = Integer.parseInt(reader.readLine());
			tiles = new Tile[levelHeight][levelWidth];

			for (int y = 0; y < levelHeight * 2; y++) {
				String line = reader.readLine();
				String[] tileIDs = line.split(",");
				for (int x = 0; x < levelWidth; x++) {
					int tileX = x * size;
					int tileY = y * size;
					if (tileIDs[x].contains("|")) {
						makeMovingTile(tileX, tileY, tileIDs[x]);
						continue;
					}
					int id = Integer.parseInt(tileIDs[x]);
					if (id < 0) {
						continue;
					} else if (id <= MAX_PASSABLE_ID) {
						tiles[y][x] = new PassableTile(tileX, tileY, id);
					} else if (id <= MAX_SOLID_ID) {
						tiles[y][x] = new Tile(tileX, tileY, id);
					} else if (id <= MAX_TERRAIN_ID) {
						terrain.add(new BackgroundTile(tileX, (y - levelHeight) * size, id));
					} else if (id <= MAX_ENEMY_ID) {
						makeEnemy(tileX, tileY, id);
					} else if (id == WINNING_ID) {
						winningTiles.add(new Tile(tileX, tileY, id));
					}
				}
			}
		} catch (NumberFormatException | IOException e) {
			System.out.println("Error in level file");
			e.printStackTrace();
		}
	}

	private void makeMovingTile(int x, int y, String params) {
		// legend: id|width|rightLimit(number of tiles to the right to travel)|moveSpeed
		String[] properties = params.split("\\|");
		movingTiles.add(new MovingTile(x, y, Integer.parseInt(properties[0]), Integer.parseInt(properties[1]),
				Integer.parseInt(properties[2]), Integer.parseInt(properties[3])));
	}

	private void makeEnemy(int x, int y, int id) {
		switch (id) {
			case (80):
				enemies.add(new SkeletonEnemy(x, y, ImageLoader.getUnitImagesById(id)));
				break;
			case (81):
				enemies.add(new OrcEnemy(x, y, ImageLoader.getUnitImagesById(id)));
		}
	}

	public int getLevelWidth() {
		return levelWidth;
	}

	public int getLevelHeight() {
		return levelHeight;
	}

	public List<Tile> getTerrain() {
		return terrain;
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public List<Tile> getMovingTiles() {
		return movingTiles;
	}

	public PlayerUnit getPlayer() {
		return player;
	}

	public List<EnemyUnit> getEnemies() {
		return enemies;
	}

	public List<Tile> getWinningTiles() {
		return winningTiles;
	}
}