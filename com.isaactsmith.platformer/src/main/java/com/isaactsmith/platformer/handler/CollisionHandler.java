package com.isaactsmith.platformer.handler;

import java.awt.Point;
import java.util.List;

import com.isaactsmith.platformer.obj.Obj;
import com.isaactsmith.platformer.obj.tile.MovingTile;
import com.isaactsmith.platformer.obj.tile.PassableTile;
import com.isaactsmith.platformer.obj.tile.Tile;
import com.isaactsmith.platformer.obj.unit.EnemyUnit;
import com.isaactsmith.platformer.obj.unit.PlayerUnit;
import com.isaactsmith.platformer.obj.unit.Unit;

public class CollisionHandler {

	private List<Tile> winningTiles;
	private Tile[][] tiles;
	private List<Tile> movingTiles;
	private List<EnemyUnit> enemies;
	private PlayerUnit player;
	private TickHandler tickHandler;

	public CollisionHandler(List<Tile> winningTiles, Tile[][] tiles, List<Tile> movingTiles, List<EnemyUnit> enemies, PlayerUnit player,
			TickHandler tickHandler) {
		this.winningTiles = winningTiles;
		this.tiles = tiles;
		this.movingTiles = movingTiles;
		this.enemies = enemies;
		this.player = player;
		this.tickHandler = tickHandler;
	}
	
	public boolean handleWinning() {
		for (int i = 0, winningTilesAmount = winningTiles.size(); i < winningTilesAmount; i++) {
			Tile tile = winningTiles.get(i);
			if (player.getRect().intersects(tile.getRect())) {
				return true;
			}
		}
		return false;
	}

	public void handleTileCollision(Unit unit) {
		unit.resetCollision();
		int unitX = (int) unit.getX();
		int unitY = (int) unit.getY();
		if (unit instanceof PlayerUnit) {
			unitX += TickHandler.getXOffset();
		}
		int size = unit.getWidth();

		handleStationaryTileCollision(unit, unitX, unitY, size);
		handleMovingTileCollision(unit, unitX, unitY, size);

		if (!unit.isFallingHandled()) {
			unit.setFalling(!unit.isJumping());
		}
	}

	public void handleStationaryTileCollision(Unit unit, int unitX, int unitY, int size) {

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
	}

	private void handleMovingTileCollision(Unit unit, int unitX, int unitY, int size) {
		for (int i = 0, movingTilesAmount = movingTiles.size(); i < movingTilesAmount; i++) {
			if (handleDownwardCollision(unit, unitX, unitY, size, movingTiles.get(i)) && !unit.isJumping()) {
				if (unit instanceof PlayerUnit) {
					TickHandler.setXOffset(TickHandler.getXOffset() + ((MovingTile) movingTiles.get(i)).getMoveSpeed());
				} else {
					unit.setX(unit.getX() + ((MovingTile) movingTiles.get(i)).getMoveSpeed());
				}
			}
		}
	}

	private boolean handleDownwardCollision(Unit unit, int unitX, int unitY, int size, Obj object) {
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
				return true;
			} else {
				unit.setFalling(!unit.willCollideTop() && !unit.isJumping());
			}
		}
		return false;
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
	
	public void handleEnemyCollision() {
		for (int i = 0; i < enemies.size(); i++) {
			EnemyUnit enemy = enemies.get(i);
			if (enemy.isActive() && player.isActive()) {
				if (player.getRect().intersects(enemy.getRect())) {
					if (player.getY() <= enemy.getY() - enemy.getHeight() + player.getYVelocity()
							+ enemy.getCurrentJumpSpeed()) {
						enemy.die();
					} else {
						tickHandler.reset();
						player.die();
					}
				}
			}
		}
	}
}