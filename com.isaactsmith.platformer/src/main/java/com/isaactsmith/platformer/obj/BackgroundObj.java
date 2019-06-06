package com.isaactsmith.platformer.obj;

import java.awt.image.BufferedImage;

public class BackgroundObj extends Obj {
	
	private static final boolean isForeground = false;

	public BackgroundObj(int x, int y, BufferedImage image) {
		super(x, y, image);
	}
}
