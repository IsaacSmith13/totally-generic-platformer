package com.isaactsmith.platformer.handler;

import java.awt.Point;

import com.isaactsmith.platformer.obj.Obj;
import com.isaactsmith.platformer.obj.Tile;
import com.isaactsmith.platformer.obj.unit.Unit;

public class CollisionHandler {

	private Tile[][] terrain;

	public CollisionHandler(Tile[][] terrain) {
		this.terrain = terrain;
	}

	public void handleTileCollision(Unit unit) {
		unit.resetCollision();
		int unitX = unit.getXOffset();
		int unitY = unit.getYOffset();
		int size = Obj.GLOBAL_SIZE;

		for (int i = 0; i < terrain.length; i++) {
			for (int j = 0; j < terrain[0].length; j++) {
				Tile tile = terrain[i][j];

				if (!tile.isPassable()) {
					handleRightwardCollision(unit, unitX, unitY, size, tile);
					handleLeftwardCollision(unit, unitX, unitY, size, tile);
					handleUpwardCollision(unit, unitX, unitY, size, tile);
				}
				handleDownwardCollision(unit, unitX, unitY, size, tile);
			}
		}
	}

	private void handleDownwardCollision(Unit unit, int unitX, int unitY, int size, Tile tile) {

		int unitLocationOnTopOfTile = (int) tile.getY() - size;

		if (unitY <= unitLocationOnTopOfTile + unit.getTerminalVelocity() + 1) {
			if (handleOneSideOfCollision(new Point(unitX + 1, unitY + size + 1), tile)
					|| handleOneSideOfCollision(new Point(unitX + size - 1, unitY + size + 1), tile)) {
				unit.setFalling(false);
				unit.setCollideTop(true);
				if (unitY >= (int) tile.getY() - size && !unit.isFalling() && !unit.isJumping()) {
					unit.setY(tile.getY() - UnitHandler.getYOffset() - size - 1);
				}
			} else {
				unit.setFalling(!unit.willCollideTop() && !unit.isJumping());
			}
		}
	}

	private void handleUpwardCollision(Unit unit, int unitX, int unitY, int size, Tile tile) {
		if (unit.getYVelocity() != 0) {
			if (handleOneSideOfCollision(new Point(unitX + 1, unitY), tile)
					|| handleOneSideOfCollision(new Point(unitX + size - 1, unitY), tile)) {
				unit.setJumping(false);
			}
		}
	}

	private void handleLeftwardCollision(Unit unit, int unitX, int unitY, int size, Tile tile) {
		if (unit.isLeft()) {
			if (handleOneSideOfCollision(new Point(unitX - 2, unitY + 2), tile)
					|| handleOneSideOfCollision(new Point(unitX - 2, unitY + size - 1), tile)) {
				unit.setCollideLeft(true);
			}
		}
	}

	private void handleRightwardCollision(Unit unit, int unitX, int unitY, int size, Tile tile) {
		if (unit.isRight()) {
			if (handleOneSideOfCollision(new Point(unitX + size + 3, unitY + 2), tile)
					|| handleOneSideOfCollision(new Point(unitX + size + 3, unitY + size - 1), tile)) {
				unit.setCollideRight(true);
			}
		}
	}

	private boolean handleOneSideOfCollision(Point pointInObj, Tile tile) {
		return tile.getTileAsRect().contains(pointInObj);
	}
}