package com.isaactsmith.platformer.obj;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.isaactsmith.platformer.handler.UnitHandler;

public class Tile extends Obj {

	private Rectangle tileAsRect;

	public Tile(int x, int y, BufferedImage image) {
		super(x, y, image);
		tileAsRect = new Rectangle(x, y, getWidth(), getHeight());
	}

	@Override
	public void paint(Graphics g) {
		int x = (int) (getX() - UnitHandler.getXOffset());
		int y = (int) (getY() - UnitHandler.getYOffset());
		g.drawImage(getImage(), x, y, getWidth(), getHeight(), null);
	}

	public Rectangle getTileAsRect() {
		return tileAsRect;
	}
}