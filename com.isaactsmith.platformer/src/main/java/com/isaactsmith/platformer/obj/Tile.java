package com.isaactsmith.platformer.obj;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.isaactsmith.platformer.handler.FrameHandler;
import com.isaactsmith.platformer.handler.UnitHandler;
import com.isaactsmith.platformer.level.ImageLoader;

public class Tile extends Obj {

	private Rectangle tileAsRect;
	private int id;
	private String type;
	private boolean isPassable = false;

	public Tile(int x, int y, int id) {
		super(x, y, ImageLoader.getTileImageById(id));
		tileAsRect = new Rectangle(x, y, getWidth(), getHeight());
		this.setId(id);
		if (id == 2) {
			isPassable = true;
		}
	}

	@Override
	public void paint(Graphics g) {

		int x = (int) (getX() - UnitHandler.getXOffset());
		int y = (int) (getY());

		if (x <= FrameHandler.WINDOW_WIDTH + (GLOBAL_SIZE * 2) && (x - 1) >= -GLOBAL_SIZE * 2
				&& y <= FrameHandler.WINDOW_HEIGHT + (GLOBAL_SIZE * 2) && (y - 1) >= -GLOBAL_SIZE * 2) {

			g.drawImage(getImage(), x, y, getWidth(), getHeight(), null);
		}
	}

	@Override
	public Rectangle getRect() {
		return tileAsRect;
	}

	public boolean isPassable() {
		return isPassable;
	}

	public void setPassable(boolean isPassable) {
		this.isPassable = isPassable;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}