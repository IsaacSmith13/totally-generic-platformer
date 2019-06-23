package com.isaactsmith.platformer.obj.tile;

public class BackgroundTile extends Tile {

	public BackgroundTile(int x, int y, int id) {
		super(x, y, id);
		// Make background terrain the size of the image scaled to the window size
		setWidth((int) (getGlobalSize() * (Math.ceil((getImage().getWidth() * getScalar()) / getGlobalSize()))));
		setHeight((int) (getGlobalSize() * (Math.ceil((getImage().getHeight() * getScalar()) / getGlobalSize()))));
	}
}
