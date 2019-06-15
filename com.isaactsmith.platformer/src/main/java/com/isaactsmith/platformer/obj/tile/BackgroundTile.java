package com.isaactsmith.platformer.obj.tile;

public class BackgroundTile extends Tile {

	public BackgroundTile(int x, int y, int id) {
		super(x, y, id);
		setWidth(getImage().getWidth());
		setHeight(getImage().getHeight());
	}
}
