package com.isaactsmith.platformer.level;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import com.isaactsmith.platformer.obj.Obj;
import com.isaactsmith.platformer.obj.Tile;
import com.isaactsmith.platformer.obj.unit.PlayerUnit;

public class LevelLoader {

	private static final String[] DIRECTIONS = { "Right", "Left" };
	private static final String PLAYER_IMG = "images/Player";
	private String levelpath;
	private int width;
	private int height;
	private Tile[][] terrain;
	private PlayerUnit player;

	public LevelLoader(String levelPath) {
		this.levelpath = levelPath;
		player = makePlayer();
		readLevel();
	}

	public void readLevel() {
		try (BufferedReader reader = new BufferedReader(new FileReader(levelpath))) {
			int width = Integer.parseInt(reader.readLine());
			int height = Integer.parseInt(reader.readLine());

			terrain = new Tile[height][width];

			for (int y = 0; y < height; y++) {
				String line = reader.readLine();
				String[] tiles = line.split(",");
				for (int x = 0; x < width; x++) {
					int id = Integer.parseInt(tiles[x]);
					if (id < 20) {
						terrain[y][x] = new Tile(x * Obj.GLOBAL_SIZE, y * Obj.GLOBAL_SIZE, id, true);
					} else {
						// TODO make enemy if id > 20
					}
				}
			}

		} catch ( NumberFormatException  | IOException e) {
			System.out.println("Error in level file");
			e.printStackTrace();
		}
	}

	public PlayerUnit makePlayer() {
		BufferedImage[] images = new BufferedImage[6];
		// Makes an array with <imageName>Right0, <imageName>Right1, <imageName>Right2,
		// <imageName>Left0, etc.
		// There are three images in the walking animation, and two sides a unit can
		// face
		int imageNumber = 0;
		for (int j = 0; j < DIRECTIONS.length; j++) {
			for (int i = 0; i < 3; i++) {
				// Math to make each image enter the array in a different index
				images[imageNumber++] = ImageLoader.getBufferedImage(PLAYER_IMG + DIRECTIONS[j] + i + ".png");
			}
		}
		return new PlayerUnit(images);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Tile[][] getTerrain() {
		return terrain;
	}

	public PlayerUnit getPlayer() {
		return player;
	}
}