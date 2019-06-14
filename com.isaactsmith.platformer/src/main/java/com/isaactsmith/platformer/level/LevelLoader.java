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
			int width = Integer.parseInt(reader.readLine());
			int height = Integer.parseInt(reader.readLine());
			tiles = new Tile[height][width];
			for (int y = 0; y < height * 2; y++) {
				String line = reader.readLine();
				String[] tileIDs = line.split(",");
				for (int x = 0; x < width; x++) {
					int id = Integer.parseInt(tileIDs[x]);
					if (id == -1) {
						continue;
					}
					if (id < 20) {
						tiles[y][x] = new Tile(x * size, y * size, id);
					} else if (id < 40) {
						makeEnemy(x * size, y * size, id);
					} else {
						terrain.add(new Tile(x * size, (y / 2) * size, id, true));
					}
				}
			}
//			readForegroundObjects(reader);
//			readBackgroundObjects(reader);

		} catch (NumberFormatException | IOException e) {
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