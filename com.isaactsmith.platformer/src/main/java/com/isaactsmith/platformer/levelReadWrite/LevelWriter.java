package com.isaactsmith.platformer.levelReadWrite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import com.isaactsmith.platformer.handler.FrameHandler;

public class LevelWriter {

	private static final String PASSABLE_BLOCK = "com/isaactsmith/platformer/resources/images/PassableBlock.png";
	private static final String SOLID_BLOCK = "com/isaactsmith/platformer/resources/images/SolidBlock.png";
	private static final String PLAYER = "com/isaactsmith/platformer/resources/images/Player";
	private static final int DEFAULT_DIAMETER = FrameHandler.WINDOW_WIDTH / 25;

	public static void writeLevel(String levelPath) {
		try (PrintWriter writer = new PrintWriter(new File("level.level"))) {
			writer.println("player,0,0," + PLAYER);
			for (int i = 0; i < 10; i++) {
				for (int j = 0, k = 0; j < 200; j++, k++) {
					writer.println("tile," + j * DEFAULT_DIAMETER + "," + ((i * -150) + 500) + "," + PASSABLE_BLOCK + ",passable");
					if (Math.round(Math.random() * 3) == 3) {
						j += Math.random() * 15;
					}
					writer.println("tile," + (i * (-DEFAULT_DIAMETER * 5) + 200) + "," + k * 32 + "," + SOLID_BLOCK + ",solid");
					if (Math.round(Math.random() * 3) == 3) {
						k += Math.random() * 15;
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Invalid level file to write to");
			e.printStackTrace();
		}
	}
}
