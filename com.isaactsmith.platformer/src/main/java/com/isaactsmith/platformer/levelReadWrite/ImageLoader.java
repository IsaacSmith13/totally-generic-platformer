package com.isaactsmith.platformer.levelReadWrite;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ImageLoader {
	
	private static Map<String, BufferedImage> images = new HashMap<String, BufferedImage>();
	
	public static BufferedImage getBufferedImage(String filePath) {
		BufferedImage image = images.get(filePath);
		if (image == null) {
			try {
				image = ImageIO.read(ImageLoader.class.getClassLoader().getResource(filePath));
			} catch (IOException e) {
				System.out.println("Level file contains invalid image path");
				e.printStackTrace();
			}
		}
		return image;
	}
}
