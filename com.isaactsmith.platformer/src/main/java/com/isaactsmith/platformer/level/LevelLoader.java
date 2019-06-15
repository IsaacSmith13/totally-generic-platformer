package com.isaactsmith.platformer.level;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.isaactsmith.platformer.obj.Obj;
import com.isaactsmith.platformer.obj.Tile;
import com.isaactsmith.platformer.obj.unit.EnemyUnit;
import com.isaactsmith.platformer.obj.unit.PlayerUnit;
import com.isaactsmith.platformer.obj.unit.SkeletonEnemy;

public class LevelLoader {

	private static final int MAX_PASSABLE_ID = 4;
	private static final int MAX_MOVING_ID = 9;
	private static final int MAX_SOLID_ID = 19;
	private static final int MAX_ENEMY_ID = 39;
	private String levelpath;
	private int width;
	private int height;
	private int size = Obj.GLOBAL_SIZE;
	private Tile[][] tiles;
	private List<Tile> terrain = new ArrayList<Tile>();
	private List<EnemyUnit> enemies = new ArrayList<EnemyUnit>();
	private PlayerUnit player;

	public LevelLoader(String levelPath) {
		this.levelpath = levelPath;
		player = new PlayerUnit(ImageLoader.getUnitImagesById(-1));
		readLevel();
	}

	public void readLevel() {
		try (InputStream in = getClass().getResourceAsStream(levelpath);
				BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			int mapWidth = Integer.parseInt(reader.readLine());
			int mapHeight = Integer.parseInt(reader.readLine());
			tiles = new Tile[mapHeight][mapWidth];
			for (int y = 0; y < mapHeight * 2; y++) {
				String line = reader.readLine();
				String[] tileIDs = line.split(",");
				for (int x = 0; x < mapWidth; x++) {
					int tileX = x * size;
					int tileY = y * size;
					int id = Integer.parseInt(tileIDs[x]);
					if (id < 0) {
						continue;
					} else if (id <= MAX_PASSABLE_ID) {
						tiles[y][x] = new PassableTile(tileX, tileY, id);
					} else if (id <= MAX_MOVING_ID) {
						tiles[y][x] = new MovingTile(tileX, tileY, id);
					} else if (id <= MAX_SOLID_ID) {
						tiles[y][x] = new Tile(x * size, y * size, id);
					} else if (id <= MAX_ENEMY_ID) {
						makeEnemy(x * size, y * size, id);
					} else {
						terrain.add(new BackgroundTile(x * size, (y / 2) * size, id));
					}
				}
			}
//			readForegroundObjects(reader);
//			readBackgroundObjects(reader);

		} catch (NumberFormatException |

				IOException e) {
			System.out.println("Error in level file");
			e.printStackTrace();
		}
	}

	private void makeEnemy(int x, int y, int id) {
		switch (id) {
		case (20):
			enemies.add(new SkeletonEnemy(x, y, ImageLoader.getUnitImagesById(id), 1, 6));
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public List<Tile> getTerrain() {
		return terrain;
	}

	public Tile[][] gettiles() {
		return tiles;
	}

	public PlayerUnit getPlayer() {
		return player;
	}

	public List<EnemyUnit> getEnemies() {
		return enemies;
	}
}