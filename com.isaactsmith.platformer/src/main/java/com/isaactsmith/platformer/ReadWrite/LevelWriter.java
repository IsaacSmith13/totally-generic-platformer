package com.isaactsmith.platformer.ReadWrite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class LevelWriter {

	public static void writeLevel(String filePath) {
		try(PrintWriter writer = new PrintWriter(new File(filePath))) {
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
