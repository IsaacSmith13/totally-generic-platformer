package com.isaactsmith.platformer.handler;

import java.awt.Point;
import java.util.List;

import com.isaactsmith.platformer.obj.Tile;
import com.isaactsmith.platformer.obj.unit.PlayerUnit;
import com.isaactsmith.platformer.obj.unit.Unit;

public class UnitHandler {

	private List<Tile> terrain;
	private PlayerUnit player;
	private static double xOffset = 0;
	private static double yOffset = 0;

	public UnitHandler(PlayerUnit player, List<Tile> terrain) {
		this.player = player;
		this.terrain = terrain;
	}

	public void scroll(FrameHandler screen) {
		int xDiff = (int) player.getX() - screen.getSize().width;
		if (xDiff > -200) {
			for (Tile tile : terrain) {
				tile.setX(tile.getX() + (xDiff / 50));
			}
			player.setX(player.getX() + (xDiff / 50));
		}
	}

	public void tick(Unit unit) {
		moveUnit(unit);
	}

	private void moveUnit(Unit unit) {

		int unitX = (int) (unit.getX() + xOffset);
		int unitY = (int) (unit.getY() + yOffset);

		boolean willCollideRight = false;
		boolean willCollideLeft = false;

		for (Tile tile : terrain) {
			int width = tile.getWidth();
			int height = tile.getHeight();
			// Left side of tile collision
			if (CollisionHandler.determineIfUnitInTile(new Point(unitX + width + 2, unitY), tile)
					|| CollisionHandler.determineIfUnitInTile(new Point(unitX + width + 2, unitY + height - 1), tile)) {
				willCollideRight = true;
			}
			// Right side of tile collision
			if (CollisionHandler.determineIfUnitInTile(new Point(unitX, unitY + 2), tile)
					|| CollisionHandler.determineIfUnitInTile(new Point(unitX, unitY + height - 1), tile)) {
				willCollideLeft = true;
			}
			// Bottom side of tile collision
			if (CollisionHandler.determineIfUnitInTile(new Point(unitX + 1, unitY), tile)
					|| CollisionHandler.determineIfUnitInTile(new Point(unitX + width - 2, unitY), tile)) {
				unit.setJumping(false);
			}
			// Top side of tile collision
			if (CollisionHandler.determineIfUnitInTile(new Point(unitX + 2, unitY + height + 1), tile)
					|| CollisionHandler.determineIfUnitInTile(new Point(unitX + width - 2, unitY + height + 1), tile)) {
				unit.setY(tile.getY() - height - yOffset);
				unit.setFalling(false);
				unit.setTopCollision(true);
			} else {
				if (!unit.isTopCollision() && !unit.isJumping()) {
					unit.setFalling(true);
				}
			}
		}
		unit.setTopCollision(false);

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

		if (unit.isRight() && !willCollideRight) {
			xOffset += unit.getMoveSpeed();
		}
		if (unit.isLeft() && !willCollideLeft) {
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