package com.isaactsmith.platformer.handler;

import java.util.List;

import com.isaactsmith.platformer.obj.tile.Tile;
import com.isaactsmith.platformer.obj.unit.EnemyUnit;
import com.isaactsmith.platformer.obj.unit.PlayerUnit;
import com.isaactsmith.platformer.obj.unit.Unit;

public class UnitHandler {

	private static double xOffset = 0;
	private CollisionHandler collisionHandler;
	private List<EnemyUnit> enemies;
	PlayerUnit player;

	public UnitHandler(Tile[][] tiles, List<Tile> movingTiles, List<EnemyUnit> enemies, PlayerUnit player) {
		collisionHandler = new CollisionHandler(tiles, enemies, player, this);
		this.enemies = enemies;
		this.player = player;
	}

	public void tick() {
		// Handle enemy collision
		collisionHandler.handleEnemyCollision();
		// Handle player tick logic
		collisionHandler.handleCollision(player);
		handleJumping(player);
		handleFalling(player);
		player.walk();
		// Handle enemy tick logic
		for (int i = 0; i < enemies.size(); i++) {
			Unit currentEnemy = enemies.get(i);
			if (((EnemyUnit) currentEnemy).isActive() && player.isActive()) {
				collisionHandler.handleCollision(currentEnemy);
				handleJumping(currentEnemy);
				handleFalling(currentEnemy);
				currentEnemy.walk();
			}
			else {
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