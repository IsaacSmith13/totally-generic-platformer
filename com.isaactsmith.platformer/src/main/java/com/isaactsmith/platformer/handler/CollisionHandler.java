package com.isaactsmith.platformer.handler;

import java.awt.Point;
import java.util.List;

import com.isaactsmith.platformer.obj.Obj;
import com.isaactsmith.platformer.obj.tile.PassableTile;
import com.isaactsmith.platformer.obj.tile.Tile;
import com.isaactsmith.platformer.obj.unit.EnemyUnit;
import com.isaactsmith.platformer.obj.unit.PlayerUnit;
import com.isaactsmith.platformer.obj.unit.Unit;

public class CollisionHandler {

	private Tile[][] tiles;
	private List<EnemyUnit> enemies;
	private PlayerUnit player;
	private UnitHandler unitHandler;

	public CollisionHandler(Tile[][] tiles, List<EnemyUnit> enemies, PlayerUnit player, UnitHandler unitHandler) {
		this.tiles = tiles;
		this.enemies = enemies;
		this.player = player;
		this.unitHandler = unitHandler;
	}

	public void handleCollision(Unit unit) {
		unit.resetCollision();
		int unitX = (int) unit.getX();
		int unitY = (int) unit.getY();
		if (unit instanceof PlayerUnit) {
			unitX += UnitHandler.getXOffset();
		}
		int size = unit.getWidth();

		handleTileCollision(unit, unitX, unitY, size);
	}

	public void handleEnemyCollision() {
		for (int i = 0; i < enemies.size(); i++) {
			EnemyUnit enemy = enemies.get(i);
			if (enemy.isActive() && player.isActive()) {
				if (player.getRect().intersects(enemy.getRect())) {
					if (player.getY() <= enemy.getY() - enemy.getHeight() + player.getYVelocity()
							+ enemy.getCurrentJumpSpeed()) {
						enemy.die();
					} else {
						unitHandler.resetEnemies();
						player.die();
					}
				}
			}
		}
	}

	public void handleTileCollision(Unit unit, int unitX, int unitY, int size) {

		int startX = Math.max(Math.min(-2 + (int) (unit.isLeft() ? (unitX - unit.getMoveSpeed()) / size : unitX / size),
				tiles[0].length - 1), 0);
		int endX = Math.max(Math.min(2 + (int) (unit.isRight() ? (unitX + unit.getMoveSpeed()) / size : unitX / size),
				tiles[0].length - 1), 0);
		int startY = Math.max(
				Math.min(-2 + (int) (unit.isJumping() ? (unitY - unit.getCurrentJumpSpeed()) / size : unitY / size),
						tiles.length - 1),
				0);
		int endY = Math.max(Math.min(2 + (int) ((unitY + unit.getYVelocity()) / size), tiles.length - 1), 0);

		for (int y = startY; y <= endY; y++) {
			for (int x = startX; x <= endX; x++) {
				if (tiles[y][x] == null) {
					continue;
				}
				Tile tile = tiles[y][x];

				if (!(tile instanceof PassableTile)) {
					handleRightwardCollision(unit, unitX, unitY, size, tile);
					handleLeftwardCollision(unit, unitX, unitY, size, tile);
					handleUpwardCollision(unit, unitX, unitY, size, tile);
				}
				handleDownwardCollision(unit, unitX, unitY, size, tile);
			}
		}
		if (!unit.isFallingHandled()) {
			unit.setFalling(!unit.isJumping());
		}
	}

	private void handleDownwardCollision(Unit unit, int unitX, int unitY, int size, Obj object) {
		int unitLocationOnTopOfObj = (int) object.getY() - size;

		if (unitY <= unitLocationOnTopOfObj + unit.getTerminalVelocity() + 1) {
			if (handleOneSideOfCollision(new Point(unitX + 1, unitY + size + 1), object)
					|| handleOneSideOfCollision(new Point(unitX + size - 1, unitY + size + 1), object)) {
				unit.setFalling(false);
				unit.setCollideTop(true);
				unit.setFallingHandled(true);
				if (unitY >= (int) object.getY() - size && !unit.isFalling() && !unit.isJumping()) {
					unit.setY(object.getY() - size - 1);
				}
			} else {
				unit.setFalling(!unit.willCollideTop() && !unit.isJumping());
			}
		}
	}

	private void handleUpwardCollision(Unit unit, int unitX, int unitY, int size, Obj object) {
		if (unit.getYVelocity() != 0) {
			if (handleOneSideOfCollision(new Point(unitX + 1, unitY), object)
					|| handleOneSideOfCollision(new Point(unitX + size - 1, unitY), object)) {
				unit.setJumping(false);
			}
		}
	}

	private void handleLeftwardCollision(Unit unit, int unitX, int unitY, int size, Obj object) {
		if (unit.isLeft()) {
			if (handleOneSideOfCollision(new Point(unitX - 2, unitY + 2), object)
					|| handleOneSideOfCollision(new Point(unitX - 2, unitY + size - 1), object)) {
				unit.setCollideLeft(true);
			}
		}
	}

	private void handleRightwardCollision(Unit unit, int unitX, int unitY, int size, Obj object) {
		if (unit.isRight()) {
			if (handleOneSideOfCollision(new Point(unitX + size + 3, unitY + 2), object)
					|| handleOneSideOfCollision(new Point(unitX + size + 3, unitY + size - 1), object)) {
				unit.setCollideRight(true);
			}
		}
	}

	private boolean handleOneSideOfCollision(Point pointInObj, Obj object) {
		return object.getRect().contains(pointInObj);
	}
}