package com.isaactsmith.platformer.level;

import com.isaactsmith.platformer.obj.Tile;

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
