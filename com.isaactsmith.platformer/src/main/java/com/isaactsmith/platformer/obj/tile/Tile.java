package com.isaactsmith.platformer.obj.tile;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.isaactsmith.platformer.handler.FrameHandler;
import com.isaactsmith.platformer.handler.TickHandler;
import com.isaactsmith.platformer.level.ImageLoader;
import com.isaactsmith.platformer.obj.Obj;

public class Tile extends Obj {

	private final Rectangle tileAsRect;
	private final int id;

	public Tile(int x, int y, int id) {
		super(x, y, ImageLoader.getTileImageById(id));
		tileAsRect = new Rectangle(x, y, getWidth(), getHeight());
		this.id = id;
	}

	@Override
	public void paint(Graphics g) {

		int x = (int) (getX() - TickHandler.getXOffset());
		int y = (int) (getY());
		int width = getWidth();
		int height = getHeight();
		if (x <= FrameHandler.WINDOW_WIDTH + (width * 2) && (x - 1) >= -width * 2
				&& y <= FrameHandler.WINDOW_HEIGHT + (height * 2) && (y - 1) >= -height * 2) {

			g.drawImage(getImage(), x, y, width, height, null);
		}
	}

	@Override
	public Rectangle getRect() {
		return tileAsRect;
	}

	public int getId() {
		return id;
	}
}