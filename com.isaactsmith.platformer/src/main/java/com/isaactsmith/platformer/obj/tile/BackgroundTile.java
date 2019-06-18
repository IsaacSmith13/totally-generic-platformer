package com.isaactsmith.platformer.obj.tile;

import com.isaactsmith.platformer.obj.Obj;

public class BackgroundTile extends Tile {

	public BackgroundTile(int x, int y, int id) {
		super(x, y, id);
		setWidth((int)(Obj.GLOBAL_SIZE * (Math.ceil(getImage().getWidth() / (double)Obj.GLOBAL_SIZE))));
		setHeight((int)(Obj.GLOBAL_SIZE * (Math.ceil(getImage().getHeight() / (double)Obj.GLOBAL_SIZE))));
	}
}
