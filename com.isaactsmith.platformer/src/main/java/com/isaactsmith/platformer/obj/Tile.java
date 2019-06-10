package com.isaactsmith.platformer.obj;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.isaactsmith.platformer.handler.FrameHandler;
import com.isaactsmith.platformer.handler.UnitHandler;

public class Tile extends Obj {

	private Rectangle tileAsRect;
	private boolean isPassable = false;

	public Tile(int x, int y, BufferedImage image) {
		super(x, y, image);
		tileAsRect = new Rectangle(x, y, getWidth(), getHeight());
	}

	public Tile(int x, int y, BufferedImage image, boolean isPassable) {
		this(x, y, image);
		this.setPassable(isPassable);
	}

	@Override
	public void paint(Graphics g) {

		int x = (int) (getX() - UnitHandler.getXOffset());
		int y = (int) (getY() - UnitHandler.getYOffset());

		if (x <= FrameHandler.WINDOW_WIDTH + (GLOBAL_DIAMETER * 2) && (x - 1) >= -GLOBAL_DIAMETER * 2
				&& y <= FrameHandler.WINDOW_HEIGHT + (GLOBAL_DIAMETER * 2) && (y - 1) >= -GLOBAL_DIAMETER * 2) {

			g.drawImage(getImage(), x, y, getWidth(), getHeight(), null);
		}
	}

	public Rectangle getTileAsRect() {
		return tileAsRect;
	}

	public boolean isPassable() {
		return isPassable;
	}

	public void setPassable(boolean isPassable) {
		this.isPassable = isPassable;
	}
}