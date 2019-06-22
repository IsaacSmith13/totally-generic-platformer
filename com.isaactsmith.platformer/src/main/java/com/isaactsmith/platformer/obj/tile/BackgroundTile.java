package com.isaactsmith.platformer.obj.tile;

import com.isaactsmith.platformer.obj.Obj;

public class BackgroundTile extends Tile {

	public BackgroundTile(int x, int y, int id) {
		super(x, y, id);
		// Make background terrain the size of the image scaled to the window size
		setWidth((int) (Obj.GLOBAL_SIZE * (Math.ceil((getImage().getWidth() * SCALAR) / (double) Obj.GLOBAL_SIZE))));
		setHeight((int) (Obj.GLOBAL_SIZE * (Math.ceil((getImage().getHeight() * SCALAR) / (double) Obj.GLOBAL_SIZE))));
	}
}
