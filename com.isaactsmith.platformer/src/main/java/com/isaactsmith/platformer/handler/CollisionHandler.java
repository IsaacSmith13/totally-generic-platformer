package com.isaactsmith.platformer.handler;

import java.awt.Point;

import com.isaactsmith.platformer.obj.Tile;

public abstract class CollisionHandler {

	public static boolean determineIfUnitInTile(Point pointInObj, Tile tile) {
		return tile.getTileAsRect().contains(pointInObj);
	}
	public static boolean determineIfUnitInTile2(Point pointInObj, Tile tile) {
		return tile.getTileAsRect().contains(pointInObj);
	}
}