package com.isaactsmith.platformer.obj;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.isaactsmith.platformer.handler.FrameHandler;
import com.isaactsmith.platformer.handler.UnitHandler;
import com.isaactsmith.platformer.level.ImageLoader;

public class Tile extends Obj {

	private static final int PASSABLE_TILE_ID = 2;
	private Rectangle tileAsRect;
	private int id;
	private String type;
	private boolean isPassable = false;
	private boolean isBackground = false;

	public Tile(int x, int y, int id) {
		super(x, y, ImageLoader.getTileImageById(id));
		tileAsRect = new Rectangle(x, y, getWidth(), getHeight());
		this.id = id;
		if (id == PASSABLE_TILE_ID) {
			isPassable = true;
		}
	}

	public Tile(int x, int y, int id, boolean isBackground) {
		this(x, y, id);
		this.isBackground = isBackground;
	}

	@Override
	public void paint(Graphics g) {

		int x = (int) (getX() - UnitHandler.getXOffset());
		int y = (int) (getY());
		int width = getWidth();
		int height = getHeight();
		if (x <= FrameHandler.WINDOW_WIDTH + (width * 2) && (x - 1) >= -width * 2
				&& y <= FrameHandler.WINDOW_HEIGHT + (height * 2) && (y - 1) >= -height * 2) {

			g.drawImage(getImage(), x, y, getWidth(), getHeight(), null);
		}
	}

	@Override
	public Rectangle getRect() {
		return tileAsRect;
	}

	@Override
	public int getWidth() {
		if (isBackground) {
			return getImage().getWidth();
		}
		return super.getWidth();
	}

	@Override
	public int getHeight() {
		if (isBackground) {
			return getImage().getHeight();
		}
		return super.getHeight();
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

	public boolean isBackground() {
		return isBackground;
	}

	public void setBackground(boolean isBackground) {
		this.isBackground = isBackground;
	}
}