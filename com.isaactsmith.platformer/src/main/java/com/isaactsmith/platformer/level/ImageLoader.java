package com.isaactsmith.platformer.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ImageLoader {

	private static Map<String, BufferedImage> images = new HashMap<String, BufferedImage>();
	private static final String[] DIRECTIONS = { "Right", "Left" };
	private static final String PLAYER_IMG = "Player";
	private static final String SKELETON_IMG = "Skeleton";

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
		case (0):
			return getBufferedImage("SolidDirt");
		case (1):
			return getBufferedImage("SolidGrass");
		case (2):
			return getBufferedImage("PassableGrass");
		default:
			return null;
		}
	}

	public static BufferedImage[] getUnitImagesById(int id) {
		switch(id) {
		case(-1):
			return createImagesArray(PLAYER_IMG);
		case(20):
			return createImagesArray(SKELETON_IMG);
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
