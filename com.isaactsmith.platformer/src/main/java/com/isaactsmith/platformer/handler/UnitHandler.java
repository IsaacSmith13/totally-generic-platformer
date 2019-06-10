package com.isaactsmith.platformer.handler;

import java.util.List;

import com.isaactsmith.platformer.obj.Tile;
import com.isaactsmith.platformer.obj.unit.Unit;

public class UnitHandler {

	private static double xOffset = 0;
	private static double yOffset = 0;
	private CollisionHandler collisionHandler;

	public UnitHandler(List<Tile> terrain) {
		collisionHandler = new CollisionHandler(terrain);
	}

	public void tick(Unit unit) {
		moveUnit(unit);
	}

	private void moveUnit(Unit unit) {

		collisionHandler.handleTileCollision(unit);

		if (unit.isJumping()) {
			double currentJumpSpeed = unit.getCurrentJumpSpeed();
			yOffset -= currentJumpSpeed;
			unit.setCurrentJumpSpeed(currentJumpSpeed - .2);

			if (unit.getCurrentJumpSpeed() <= 0) {
				unit.setCurrentJumpSpeed(unit.getJumpspeed());
				unit.setJumping(false);
				unit.setFalling(true);
			}
		}

		if (unit.isFalling()) {
			double currentYVelocity = unit.getYVelocity();
			yOffset += currentYVelocity;
			unit.setYVelocity(currentYVelocity + .2);
		}

		if (unit.isRight() && !unit.willCollideRight()) {
			xOffset += unit.getMoveSpeed();
		}
		if (unit.isLeft() && !unit.willCollideLeft()) {
			xOffset -= unit.getMoveSpeed();
		}

		if (!unit.isFalling()) {
			unit.setYVelocity(.2);

		}
	}

	public static double getXOffset() {
		return xOffset;
	}

	public static double getYOffset() {
		return yOffset;
	}
}