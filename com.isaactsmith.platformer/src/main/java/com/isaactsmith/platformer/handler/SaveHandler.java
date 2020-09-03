package com.isaactsmith.platformer.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class SaveHandler {

	private static final File SAVE_FILE = new File("savefile.save");

	public static void writeSave(int levelNumber) {
		// If the player has already beat this level or higher, don't rewrite the file
		if (SAVE_FILE.exists() && SAVE_FILE.isFile() && readSave() >= levelNumber) {
			return;
		}
		try (BufferedWriter writer = new BufferedWriter(new PrintWriter(SAVE_FILE))) {
			// Adds some garbage numbers before and after the actual value
			for (int i = 0; i < 37; i++) {
				writer.write((int) (Math.random() * (levelNumber + 10)) + " ");
			}
			writer.write((levelNumber + 6) + " ");
			for (int i = 0; i < 13; i++) {
				writer.write((int) (Math.random() * (levelNumber + 10)) + " ");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int readSave() {
		if (SAVE_FILE.exists() && SAVE_FILE.isFile()) {
			try (Scanner scanner = new Scanner(SAVE_FILE)) {
				if (scanner.hasNextLine()) {
					// Grabs the actual number from the save file
					String[] saveFileLine = scanner.nextLine().split(" ");
					return Integer.parseInt(saveFileLine[37]) - 6;
				}
			} catch (FileNotFoundException | InputMismatchException | NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
}