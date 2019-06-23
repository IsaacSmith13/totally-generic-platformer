package com.isaactsmith.platformer.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public abstract class SaveHandler {
	
	private static final File SAVE_FILE = new File("savefile.save");

	public static void addLevel(int levelNumber) {
		try (BufferedWriter writer = new BufferedWriter(new PrintWriter(SAVE_FILE))) {
			writer.write("" + levelNumber);
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}

	public static int readSave() {
		try (Scanner scanner = new Scanner(SAVE_FILE)) {
			return scanner.nextInt();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}
}