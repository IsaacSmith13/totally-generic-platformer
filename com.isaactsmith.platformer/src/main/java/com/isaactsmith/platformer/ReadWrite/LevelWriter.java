package com.isaactsmith.platformer.ReadWrite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class LevelWriter {

	private static final String NORMAL_BLOCK = "com/isaactsmith/platformer/images/NormalBlock.png";
	private static final String PLAYER = "com/isaactsmith/platformer/images/Player";

	public static void writeLevel(String levelPath) {
		try (PrintWriter writer = new PrintWriter(new File(levelPath))) {
			writer.println("player,50,50," + PLAYER);
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 200; j++) {
					writer.println("tile," + j * 32 + "," + ((i * 100) + 100) + "," + NORMAL_BLOCK);
					if (Math.round(Math.random() * 3) == 3) {
						j += 8;
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
