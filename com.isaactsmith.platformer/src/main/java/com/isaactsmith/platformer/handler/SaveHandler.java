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

	public static void addLevel(int levelNumber) {
		try (BufferedWriter writer = new BufferedWriter(new PrintWriter(SAVE_FILE))) {
			for (int i = 0; i < 37; i++) {
				writer.write((int) (Math.random() * 20) + " ");
			}
			writer.write(levelNumber + " ");
			for (int i = 0; i < 13; i++) {
				writer.write((int) (Math.random() * 20) + " ");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static int readSave() {
		try (Scanner scanner = new Scanner(SAVE_FILE)) {
			String[] saveFileLine = scanner.nextLine().split(" ");
			return Integer.parseInt(saveFileLine[37]);
		} catch (FileNotFoundException | InputMismatchException | NumberFormatException e) {
			e.printStackTrace();
		}
		return 0;
	}
}