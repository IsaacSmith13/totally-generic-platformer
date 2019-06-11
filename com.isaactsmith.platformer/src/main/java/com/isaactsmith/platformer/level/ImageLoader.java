package com.isaactsmith.platformer.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ImageLoader {

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

	public static BufferedImage getImageById(int id, boolean isTile) {
		if (isTile) {
			switch (id) {
			case (0):
				return getBufferedImage("SolidBlock");
			case (1): 
				return getBufferedImage("PassableBlock");
			default:
				return null;
			}
		}
		// TODO implement non-tile id image loading
		return null;
	}
}
