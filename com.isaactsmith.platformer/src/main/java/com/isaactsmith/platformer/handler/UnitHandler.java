package com.isaactsmith.platformer.handler;

import com.isaactsmith.platformer.obj.Obj;
import com.isaactsmith.platformer.obj.Tile;
import com.isaactsmith.platformer.obj.unit.Unit;

public class UnitHandler {

	private static double xOffset = 0;
	private CollisionHandler collisionHandler;

	public UnitHandler(Tile[][] terrain) {
		collisionHandler = new CollisionHandler(terrain);
	}

	public void tick(Unit unit) {
		moveUnit(unit);
	}

	private void moveUnit(Unit unit) {

		collisionHandler.handleTileCollision(unit);

		handleJumping(unit);

		handleFalling(unit);

		handleWalking(unit);

	}

	public void handleJumping(Unit unit) {
		if (unit.isJumping()) {
			double currentJumpSpeed = unit.getCurrentJumpSpeed();
			unit.setY(unit.getY() - currentJumpSpeed);
			unit.setCurrentJumpSpeed(currentJumpSpeed - .2);

			if (unit.getCurrentJumpSpeed() <= 0.1) {
				unit.setCurrentJumpSpeed(unit.getJumpspeed());
				unit.setJumping(false);
				unit.setFalling(true);
			}
		}
	}

	public void handleFalling(Unit unit) {
		if (unit.isFalling()) {
			double currentYVelocity = unit.getYVelocity();
			unit.setY(unit.getY() + currentYVelocity);
			unit.setYVelocity(currentYVelocity + .2);
		} else {
			unit.setYVelocity(0);
		}
		handleDeathByFalling(unit);
	}

	private void handleDeathByFalling(Unit unit) {
		if (unit.getY() > FrameHandler.WINDOW_HEIGHT) {
			if ((int) xOffset > 0) {
				xOffset -= 2;
			} else if ((int) xOffset < 0) {
				xOffset += 2;
			} else {
				unit.setLocation((FrameHandler.WINDOW_WIDTH / 2) - (Obj.GLOBAL_SIZE / 2),
						(FrameHandler.WINDOW_HEIGHT / 2) - (Obj.GLOBAL_SIZE / 2));
			}
		}
	}

	public void handleWalking(Unit unit) {
		unit.walk();
	}

	public static double getXOffset() {
		return xOffset;
	}

	public static void setXOffset(double xOffset) {
		UnitHandler.xOffset = xOffset;
	}
}