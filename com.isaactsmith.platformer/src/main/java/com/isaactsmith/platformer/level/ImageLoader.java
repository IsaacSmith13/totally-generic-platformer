package com.isaactsmith.platformer.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ImageLoader {

	// MAX_PASSABLE_ID = 19
	// MAX_SOLID_ID = 39
	// MAX_MOVING_ID = 49
	// MAX_OTHER_ID = 59
	// MAX_TERRAIN_ID = 79
	// MAX_ENEMY_ID = 98
	// WINNING_ID = 99

	private static final String[] DIRECTIONS = { "Right", "Left" };
	private static Map<String, BufferedImage> images = new HashMap<String, BufferedImage>();

	public static BufferedImage getBufferedImage(String filePath) {
		filePath = "images/" + filePath + ".png";
		BufferedImage image = images.get(filePath);
		if (image == null) {
			try {
				image = ImageIO.read(ImageLoader.class.getClassLoader().getResource(filePath));
			} catch (IOException e) {
				System.out.println("Invalid image path");
				e.printStackTrace();
			}
		}
		return image;
	}

	public static BufferedImage getTileImageById(int id) {
		switch (id) {
		// Passable
		case (0):
			return getBufferedImage("PassableGrassLeft");
		case (1):
			return getBufferedImage("PassableGrassMid");
		case (2):
			return getBufferedImage("PassableGrassRight");
		case (3):
			return getBufferedImage("PassableMushroomLeft");
		case (4):
			return getBufferedImage("PassableMushroomMiddle");
		case (5):
			return getBufferedImage("PassableMushroomRight");
		// Solid
		case (20):
			return getBufferedImage("SolidGrass");
		case (21):
			return getBufferedImage("SolidDirt");
		case (22):
			return getBufferedImage("CactusTop");
		case (23):
			return getBufferedImage("CactusBottom");
		case (24):
			return getBufferedImage("ItemFull");
		case (25):
			return getBufferedImage("ItemEmpty");
		// Moving
		case (40):
			return getBufferedImage("MovingPlatform");
		// Other
		case (50):
			return getBufferedImage("Spikes");
		case (51):
			return getBufferedImage("Ladder");
		// Terrain
		case (60):
			return getBufferedImage("Bush0");
		case (61):
			return getBufferedImage("Bush1");
		case (62):
			return getBufferedImage("SignExit");
		case (63):
			return getBufferedImage("SignLeft");
		case (64):
			return getBufferedImage("SignRight");
		case (65):
			return getBufferedImage("MushroomBottom");
		case (66):
			return getBufferedImage("MushroomMiddle");
		case (67):
			return getBufferedImage("MushroomSmallRed");
		case (68):
			return getBufferedImage("MushroomSmallWhite");
		case (69):
			return getBufferedImage("MushroomLarge");
		// Winning Tile
		case (99):
			return getBufferedImage("WinningTile");
		default:
			return null;
		}
	}

	public static BufferedImage[] getUnitImagesById(int id) {
		switch (id) {
		case (-1):
			return createImagesArray("Player");
		case (80):
			return createImagesArray("Skeleton");
		default:
			return null;
		}
	}

	private static BufferedImage[] createImagesArray(String imagePath) {
		BufferedImage[] images = new BufferedImage[6];
		// Makes an array with <imageName>Right0, <imageName>Right1, <imageName>Right2,
		// <imageName>Left0, etc.
		// There are three images in the walking animation, and two sides a unit can
		// face
		int imageNumber = 0;
		for (int j = 0; j < DIRECTIONS.length; j++) {
			for (int i = 0; i < 3; i++) {
				// Math to make each image enter the array in a different index
				images[imageNumber++] = ImageLoader.getBufferedImage(imagePath + DIRECTIONS[j] + i);
			}
		}
		return images;
	}
}
