package com.isaactsmith.platformer.handler;

import java.awt.Point;
import java.util.List;

import com.isaactsmith.platformer.obj.Tile;
import com.isaactsmith.platformer.obj.unit.Unit;

public class CollisionHandler {

	private List<Tile> terrain;

	public CollisionHandler(List<Tile> terrain) {
		this.terrain = terrain;
	}

	public void handleTileCollision(Unit unit) {
		boolean willCollideRight = false;
		boolean willCollideLeft = false;
		int unitX = (int) (unit.getX() + UnitHandler.getXOffset());
		int unitY = (int) (unit.getY() + UnitHandler.getYOffset());

		for (Tile tile : terrain) {
			int width = tile.getWidth();
			int height = tile.getHeight();
			if (!tile.isPassable()) {
				// Left side of tile collision
				if (determineIfInSideOfTile(new Point(unitX + width + 2, unitY), tile)
						|| determineIfInSideOfTile(new Point(unitX + width + 2, unitY + height - 1), tile)) {
					willCollideRight = true;
				}
				// Right side of tile collision
				if (determineIfInSideOfTile(new Point(unitX - 2, unitY + 2), tile)
						|| determineIfInSideOfTile(new Point(unitX - 2, unitY + height - 1), tile)) {
					willCollideLeft = true;
				}
				// Bottom side of tile collision
				if (determineIfInSideOfTile(new Point(unitX + 1, unitY), tile)
						|| determineIfInSideOfTile(new Point(unitX + width - 1, unitY), tile)) {
					unit.setJumping(false);
				}
			}
			// Top side of tile collision
			if (determineIfInSideOfTile(new Point(unitX, unitY + height), tile)
					|| determineIfInSideOfTile(new Point(unitX + width, unitY + height), tile)) {
				unit.setFalling(false);
				unit.setCollideTop(true);
			} else {
				if (!unit.willCollideTop() && !unit.isJumping()) {
					unit.setFalling(true);
				} else {
					unit.setFalling(false);
				}
			}
		}

		unit.setCollideTop(false);
		unit.setCollideRight(willCollideRight);
		unit.setCollideLeft(willCollideLeft);
	}

	private boolean determineIfInSideOfTile(Point pointInObj, Tile tile) {
		return tile.getTileAsRect().contains(pointInObj);
	}

}