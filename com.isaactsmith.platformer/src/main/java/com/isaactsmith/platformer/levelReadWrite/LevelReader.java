package com.isaactsmith.platformer.levelReadWrite;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.isaactsmith.platformer.obj.Tile;
import com.isaactsmith.platformer.obj.unit.EnemyUnit;
import com.isaactsmith.platformer.obj.unit.PlayerUnit;
import com.isaactsmith.platformer.obj.unit.SkeletonEnemy;

public class LevelReader {

	private static final String[] DIRECTIONS = { "Right", "Left" };
	private PlayerUnit player;
	private List<Tile> terrain = new ArrayList<Tile>();
	private List<EnemyUnit> enemies = new ArrayList<EnemyUnit>();

	public void loadLevel(String levelPath) {
		try (Scanner scanner = new Scanner(new File(levelPath))) {
			while (scanner.hasNextLine()) {
				parseParameters(scanner.nextLine().split(","));
			}
		} catch (FileNotFoundException e) {
			System.out.println("Invalid level file");
			e.printStackTrace();
		}
	}

	private void parseParameters(String[] objParams) {

		String type = objParams[0];
		int x = Integer.parseInt(objParams[1]);
		int y = Integer.parseInt(objParams[2]);

		if (type.equals("tile")) {
			createTile(x, y, objParams[3], objParams[4]);
		} else {
			parseUnitImages(type, x, y, objParams[3]);
		}
	}

	private void createTile(int x, int y, String filePath, String tileType) {
		switch (tileType) {
		case ("passable"):
			terrain.add(new Tile(x, y, ImageLoader.getBufferedImage(filePath), true));
			break;

		case ("solid"):
			terrain.add(new Tile(x, y, ImageLoader.getBufferedImage(filePath)));
			break;
		default:
			break;
		}
	}

	private void parseUnitImages(String type, int x, int y, String filePath) {

		BufferedImage[] images = new BufferedImage[6];
		// Makes an array with <imageName>Right0, <imageName>Right1, <imageName>Right2,
		// <imageName>Left0, etc.
		// There are three images in the walking animation, and two sides a unit can
		// face
		int indexNumber = 0;
		for (int j = 0; j < DIRECTIONS.length; j++) {
			for (int i = 0; i < 3; i++) {
				// Math to make each image enter the array in a different index
				images[indexNumber++] = ImageLoader.getBufferedImage(filePath + DIRECTIONS[j] + i + ".png");
			}
		}
		createUnit(type, x, y, images);
	}

	private void createUnit(String type, int x, int y, BufferedImage[] images) {
		switch (type) {
		case ("skeleton"):
			enemies.add(new SkeletonEnemy(x, y, images));
			break;
		case ("player"):
			player = new PlayerUnit(images);
			break;
		default:
			break;
		}
	}

	public PlayerUnit getPlayer() {
		return player;
	}

	public List<Tile> getTerrain() {
		return terrain;
	}

	public List<EnemyUnit> getEnemies() {
		return enemies;
	}
}