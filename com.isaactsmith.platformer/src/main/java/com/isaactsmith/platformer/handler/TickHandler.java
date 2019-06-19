package com.isaactsmith.platformer.handler;

import java.util.List;

import com.isaactsmith.platformer.obj.tile.MovingTile;
import com.isaactsmith.platformer.obj.tile.Tile;
import com.isaactsmith.platformer.obj.unit.EnemyUnit;
import com.isaactsmith.platformer.obj.unit.PlayerUnit;
import com.isaactsmith.platformer.obj.unit.Unit;

public class TickHandler {

	private static double xOffset;
	private final CollisionHandler collisionHandler;
	private final List<Tile> movingTiles;
	private final List<EnemyUnit> enemies;
	private final PlayerUnit player;

	public TickHandler(List<Tile> winningTiles, Tile[][] tiles, List<Tile> movingTiles, List<EnemyUnit> enemies,
			PlayerUnit player) {
		collisionHandler = new CollisionHandler(winningTiles, tiles, movingTiles, enemies, player, this);
		this.movingTiles = movingTiles;
		this.enemies = enemies;
		this.player = player;
		xOffset = 0;
	}

	public boolean tick() {
		if (collisionHandler.handleWinning()) {
			return true;
		}

		handleMovingTiles();

		// Handle player tick logic
		collisionHandler.handleTileCollision(player);
		collisionHandler.handleEnemyCollision();
		handleJumping(player);
		handleFalling(player);
		player.walk();

		handleEnemies();
		return false;
	}

	public void handleMovingTiles() {
		if (player.isActive()) {
			for (int i = 0, movingTileAmount = movingTiles.size(); i < movingTileAmount; i++) {
				MovingTile tile = (MovingTile) (movingTiles.get(i));
				int tileX = (int) tile.getX();
				int moveSpeed = tile.getMoveSpeed();
				if (tileX + tile.getWidth() > tile.getRightLimit() && moveSpeed > 0) {
					moveSpeed *= -1;
				}
				if (tileX < tile.getLeftLimit() && moveSpeed < 0) {
					moveSpeed *= -1;
				}
				tile.setX(tileX + moveSpeed);
				tile.setMoveSpeed(moveSpeed);
			}
		}
	}

	public void handleEnemies() {
		for (int i = 0; i < enemies.size(); i++) {
			Unit currentEnemy = enemies.get(i);
			if (((EnemyUnit) currentEnemy).isActive() && player.isActive()) {
				collisionHandler.handleTileCollision(currentEnemy);
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
			unit.setYVelocity(0);
		}
		if (unit.getY() > FrameHandler.WINDOW_HEIGHT) {
			if (unit instanceof PlayerUnit) {
				reset();
			}
			unit.die();
		}
	}

	public static double getXOffset() {
		return xOffset;
	}

	public static void setXOffset(double xOffset) {
		TickHandler.xOffset = xOffset;
	}

	public void reset() {
		for (int i = 0, numberOfMovingTiles = movingTiles.size(); i < numberOfMovingTiles; i++) {
			((MovingTile) movingTiles.get(i)).reset();
		}
		for (int i = 0, numberOfEnemies = enemies.size(); i < numberOfEnemies; i++) {
			enemies.get(i).reset();
		}
	}
}