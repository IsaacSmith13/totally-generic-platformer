package com.isaactsmith.platformer.obj;

import java.awt.image.BufferedImage;

public class ForegroundObj extends Obj {

	private static final boolean isForeground = true;
	
	private final int width;
	private final int height;
	
	public ForegroundObj(int x, int y, BufferedImage image) {
		super(x, y, image);
		this.width = 16;
		this.height = 16;
	}
	
	public ForegroundObj(int x, int y, BufferedImage image, int width, int height) {
		super(x, y, image);
		this.width = width;
		this.height = height;
	}
}
