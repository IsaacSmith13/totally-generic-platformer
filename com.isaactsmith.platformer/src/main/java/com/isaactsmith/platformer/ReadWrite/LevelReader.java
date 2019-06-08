package com.isaactsmith.platformer.ReadWrite;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

import com.isaactsmith.platformer.obj.Tile;
import com.isaactsmith.platformer.obj.unit.EnemyUnit;
import com.isaactsmith.platformer.obj.unit.PlayerUnit;
import com.isaactsmith.platformer.obj.unit.SkeletonEnemy;

public class LevelReader {

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

		try {
			File img = new File(objParams[3]);
			BufferedImage image = ImageIO.read(img);
			createObj(type, x, y, image);
		} catch (IOException e) {
			System.out.println("Level file contains invalid image path");
			e.printStackTrace();
		}
	}

	private void createObj(String type, int x, int y, BufferedImage image) {
		switch (type) {
		case ("tile"):
			terrain.add(new Tile(x, y, image));
			break;
		case ("skeleton"):
			enemies.add(new SkeletonEnemy(x, y, image));
			break;
		case ("player"):
			player = new PlayerUnit(x, y, image);
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