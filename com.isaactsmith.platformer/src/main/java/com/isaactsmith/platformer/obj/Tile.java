package com.isaactsmith.platformer.obj;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.isaactsmith.platformer.handler.FrameHandler;
import com.isaactsmith.platformer.handler.UnitHandler;
import com.isaactsmith.platformer.level.ImageLoader;

public class Tile extends Obj {

	private Rectangle tileAsRect;
	private int id;
	private String type;
	private boolean isPassable = false;

	public Tile(int x, int y, BufferedImage image) {
		super(x, y, image);
		tileAsRect = new Rectangle(x, y, getWidth(), getHeight());
	}
	
	public Tile(int x, int y, int id) {
		this(x, y, ImageLoader.getImageById(id, true));
		this.setId(id);
	}

	public Tile(int x, int y, BufferedImage image, boolean isPassable) {
		this(x, y, image);
		this.setPassable(isPassable);
	}
	
	public Tile(int x, int y, BufferedImage image, String type, boolean isPassable) {
		this(x, y, image);
		this.setType(type);
		this.setPassable(isPassable);
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

	public Rectangle getTileAsRect() {
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