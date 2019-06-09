package com.isaactsmith.platformer.handler;

import java.util.List;

import com.isaactsmith.platformer.obj.Tile;
import com.isaactsmith.platformer.obj.unit.Unit;

public class UnitHandler {

	private List<Tile> terrain;

	public UnitHandler(List<Tile> terrain) {
		this.terrain = terrain;
	}

	public void tick(Unit unit) {

		unit.setFalling(determineIsFalling(unit));

		moveUnit(unit);
	}

	private boolean determineIsFalling(Unit unit) {
		for (Tile tile : terrain) {
			if (unit.isOn(tile)) {
				return false;
			}
		}
		return true;
	}

	private void moveUnit(Unit unit) {

		int newY = (int) unit.getY();
		int newX = (int) Math.round(unit.getX() + unit.getXVelocity());

		// If falling, increase y velocity and make unit fall
		if (unit.isFalling()) {
			unit.setYVelocity(unit.getYVelocity() + .2);
			newY = (int) Math.round(unit.getY() + unit.getYVelocity());
			// Otherwise reset y velocity
		} else {
			unit.setYVelocity(0);
		}

		int incrementYIfInTile = determineCollision(newX, newY, unit);

		if (!unit.willCollideX() || incrementYIfInTile != 0) {
			unit.setX(newX);
		}
		if (!unit.willCollideY()) {
			unit.setY(newY + incrementYIfInTile);
		} else {
			unit.setY(((int) unit.getY()) + incrementYIfInTile);
		}
	}

	private int determineCollision(int newX, int newY, Unit unit) {

		int oldX = (int) unit.getX();
		int oldY = (int) unit.getY();

		unit.setWillCollideX(false);
		unit.setWillCollideY(false);

		int incrementY = 0;
		for (Tile tile : terrain) {
			if (unit.isCollidingWith(newX, oldY, tile)) {
				unit.setWillCollideX(true);
			}
			if (unit.isCollidingWith(oldX, newY, tile) && (int) unit.getYVelocity() >= 0) {
				unit.setWillCollideY(true);
			}
		}
		for (Tile tile : terrain) {
			if (incrementY == 0 && (unit.getYVelocity() == 0 || (unit.willCollideY() && unit.willCollideX()))) {
				incrementY += handleInsideTile(newX, newY, unit, tile);
			}
		}
		return incrementY;
	}

	private int handleInsideTile(int x, int y, Unit unit, Tile tile) {

		int xDiff = (int) (x - tile.getX());
		int yDiff = (int) (y - tile.getY());
		int height = unit.getHeight();
		double yVelocity = unit.getYVelocity();

		if (yDiff < -(height / 2) && (xDiff > -unit.getWidth() && xDiff < tile.getWidth())) {
			return (int) Math.min(yVelocity, -2);
		} else if ((yDiff >= -(height / 2) && yDiff < height) && yVelocity > 0
				&& (xDiff > -unit.getWidth() && xDiff < tile.getWidth())) {
			return yDiff;
		} else {
			return 0;
		}
	}
}