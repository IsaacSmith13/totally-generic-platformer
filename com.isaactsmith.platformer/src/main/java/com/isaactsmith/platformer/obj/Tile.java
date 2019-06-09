package com.isaactsmith.platformer.obj;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile extends Obj {

	public Tile(int x, int y, BufferedImage image) {
		super(x, y, image);
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(getImage(), (int) Math.round(getX()), (int) Math.round(getY()), getWidth(), getHeight(), null);
	}	
}