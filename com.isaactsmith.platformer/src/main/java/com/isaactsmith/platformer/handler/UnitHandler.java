package com.isaactsmith.platformer.handler;

import java.util.List;

import com.isaactsmith.platformer.obj.Tile;
import com.isaactsmith.platformer.obj.unit.Unit;

public abstract class UnitHandler {

	public static void tick(Unit unit, List<Tile> terrain) {
		
		unit.setFalling(determineIsFalling(unit, terrain));

		moveUnit(unit);
	}

	private static boolean determineIsFalling(Unit unit, List<Tile> terrain) {
		for (Tile tile : terrain) {
			if (unit.isOn(tile)) {
				return false;
			}
		}
		return true;
	}

	private static void moveUnit(Unit unit) {
		
		// Set x location based on x velocity
		unit.setX((int) Math.round(unit.getX() + unit.getXVelocity()));

		// If falling, increase y velocity and make unit fall
		if (unit.isFalling()) {
			unit.setYVelocity(unit.getYVelocity() + .2);
			unit.setY((int) Math.round(unit.getY() + unit.getYVelocity()));
		// Otherwise reset y velocity to 0
		} else {
			unit.setYVelocity(0);
		}
	}
}