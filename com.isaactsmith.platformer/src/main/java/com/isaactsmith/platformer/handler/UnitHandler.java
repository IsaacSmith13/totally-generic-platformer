package com.isaactsmith.platformer.handler;

import java.util.List;

import com.isaactsmith.platformer.obj.tile.MovingTile;
import com.isaactsmith.platformer.obj.tile.Tile;
import com.isaactsmith.platformer.obj.unit.EnemyUnit;
import com.isaactsmith.platformer.obj.unit.PlayerUnit;
import com.isaactsmith.platformer.obj.unit.Unit;

public class UnitHandler {

	private static double xOffset = 0;
	private CollisionHandler collisionHandler;
	private List<Tile> movingTiles;
	private List<EnemyUnit> enemies;
	PlayerUnit player;

	public UnitHandler(Tile[][] tiles, List<Tile> movingTiles, List<EnemyUnit> enemies, PlayerUnit player) {
		collisionHandler = new CollisionHandler(tiles, movingTiles, enemies, player, this);
		this.movingTiles = movingTiles;
		this.enemies = enemies;
		this.player = player;
	}

	public void tick() {
		handleMovingTiles();

		// Handle player tick logic
		collisionHandler.handleEnemyCollision();
		collisionHandler.handleCollision(player);
		handleJumping(player);
		handleFalling(player);
		player.walk();

		handleEnemies();
	}

	public void handleMovingTiles() {
		for (int i = 0, movingTileAmount = movingTiles.size(); i < movingTileAmount; i++) {
			MovingTile tile = (MovingTile) (movingTiles.get(i));
			double tileX = tile.getX();
			double xOffset = UnitHandler.getXOffset();
			double tileXOffset = tileX - xOffset;
			int moveSpeed = tile.getMoveSpeed();
			if (tileXOffset + tile.getWidth() >= tile.getRightLimit() - xOffset && moveSpeed != 1) {
				moveSpeed *= -1;
			} else if (tileXOffset <= tile.getLeftLimit() - xOffset && moveSpeed != 1) {
				moveSpeed *= -1;
			}
			tile.setX(tileX + moveSpeed);
		}
	}

	public void handleEnemies() {
		for (int i = 0; i < enemies.size(); i++) {
			Unit currentEnemy = enemies.get(i);
			if (((EnemyUnit) currentEnemy).isActive() && player.isActive()) {
				collisionHandler.handleCollision(currentEnemy);
				handleJumping(currentEnemy);
				handleFalling(currentEnemy);
				currentEnemy.walk();
			} else {
				currentEnemy.setRight(false);
				currentEnemy.setLeft(false);
			}
		}
	}

	public void handleJumping(Unit unit) {
		if (unit.isJumping()) {
			double currentJumpSpeed = unit.getCurrentJumpSpeed();
			unit.setY(unit.getY() - currentJumpSpeed);
			unit.setCurrentJumpSpeed(currentJumpSpeed - .2);

			if (unit.getCurrentJumpSpeed() <= 0.1) {
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
			unit.setYVelocity(0.2);
		}
		if (unit.getY() > FrameHandler.WINDOW_HEIGHT) {
			if (unit instanceof PlayerUnit) {
				resetEnemies();
			}
			unit.die();
		}
	}

	public static double getXOffset() {
		return xOffset;
	}

	public static void setXOffset(double xOffset) {
		UnitHandler.xOffset = xOffset;
	}

	public void resetEnemies() {
		for (int i = 0, numberOfEnemies = enemies.size(); i < numberOfEnemies; i++) {
			enemies.get(i).reset();
		}
	}
}