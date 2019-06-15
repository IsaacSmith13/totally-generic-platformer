package com.isaactsmith.platformer.obj.tile;

public class MovingTile extends Tile {
	
	private int xRange = 0;
	private int yRange = 0;

	public MovingTile(int x, int y, int id) {
		super(x, y, id);
	}

	public int getxRange() {
		return xRange;
	}

	public void setxRange(int xRange) {
		this.xRange = xRange;
	}

	public int getyRange() {
		return yRange;
	}

	public void setyRange(int yRange) {
		this.yRange = yRange;
	}
}
