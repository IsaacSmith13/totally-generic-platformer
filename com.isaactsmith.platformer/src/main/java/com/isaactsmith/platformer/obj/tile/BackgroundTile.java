package com.isaactsmith.platformer.obj.tile;

public class BackgroundTile extends Tile {

	public BackgroundTile(int x, int y, int id) {
		super(x, y, id);
	}

	@Override
	public int getWidth() {
		return getImage().getWidth();
	}

	@Override
	public int getHeight() {
		return getImage().getHeight();
	}
}
