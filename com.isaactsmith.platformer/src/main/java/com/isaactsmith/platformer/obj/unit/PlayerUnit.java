package com.isaactsmith.platformer.obj.unit;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import com.isaactsmith.platformer.handler.FrameHandler;
import com.isaactsmith.platformer.handler.TickHandler;

public class PlayerUnit extends Unit {

	private final int spawnX;
	private final int initialX;
	private final int initialY;
	private static int lives = 3;
	private double timeOfDeath = -1;

	public PlayerUnit(BufferedImage[] images) {
		super(getGlobalSize() * 5, (FrameHandler.getWindowHeight() / 2) - (getGlobalSize() / 2), images);
		spawnX = getGlobalSize() * 5;
		initialX = (FrameHandler.getWindowWidth() / 2) - (getGlobalSize() / 2);
		initialY = (FrameHandler.getWindowHeight() / 2) - (getGlobalSize() / 2);
		setActive(true);
	}

	@Override
	public void paint(Graphics g) {
		// Adds some padding to the unit when painted so it seems more fair when hit
		g.drawImage(getImageToRender(), (int) Math.round(getX()) - getPaintPadding(),
				(int) Math.round(getY()) - getPaintPadding(), getHeight() + getPaintPadding() * 2,
				getWidth() + getPaintPadding() * 2, null);
	}

	@Override
	public void walk() {
		double xOffset = TickHandler.getXOffset();
		double x = getX();
		double moveSpeed = getMoveSpeed();
		if (isRight() && !willCollideRight()) {
			if (x < initialX) {
				setX(x + moveSpeed);
			} else {
				TickHandler.setXOffset(xOffset + moveSpeed);
			}
		} else if (isLeft() && !willCollideLeft()) {
			if (xOffset - moveSpeed < 0) {
				if (xOffset - x - moveSpeed < 0) {
					setX(x - moveSpeed);
				}
			} else {
				TickHandler.setXOffset(xOffset - moveSpeed);
			}
		}
	}

	@Override
	public void die() {
		if (lives > 0) {
			// The first time this method is called per death
			if (timeOfDeath < 0) {
				lives--;
				FrameHandler.setLoading(true);
				setActive(false);
				setLeft(false);
				setRight(false);
				setY(FrameHandler.getWindowHeight() + 50);
				timeOfDeath = System.currentTimeMillis() / 1000;
			}
			int xOffset = (int) TickHandler.getXOffset();
			// Move camera a percentage of the distance away from the start
			int deathCameraSpeed = Math.max(Math.abs(xOffset) / getGlobalSize(), 4);
			if (xOffset > deathCameraSpeed) {
				xOffset -= deathCameraSpeed;
			} else if (xOffset < -deathCameraSpeed) {
				xOffset += deathCameraSpeed;
				// If the death time has elapsed, respawn
			} else if (System.currentTimeMillis() / 1000 > timeOfDeath + 3) {
				FrameHandler.setLoading(false);
				setActive(true);
				timeOfDeath = -1;
				xOffset = 0;
				setLocation(spawnX, initialY);
			}
			TickHandler.setXOffset(xOffset);
		} else {
			// If the player has no lives left, set lives to -1 (so they can be reset),
			// and reposition the camera
			lives--;
			TickHandler.setXOffset(0);
		}
	}

	public void keyPressed(int e) {
		if (isActive()) {
			switch (e) {
			case KeyEvent.VK_A:
				e = KeyEvent.VK_LEFT;
			case KeyEvent.VK_LEFT:
				setLeft(true);
				break;
			case KeyEvent.VK_D:
				e = KeyEvent.VK_RIGHT;
			case KeyEvent.VK_RIGHT:
				setRight(true);
				break;
			case KeyEvent.VK_W:
				e = KeyEvent.VK_SPACE;
			case KeyEvent.VK_UP:
				e = KeyEvent.VK_SPACE;
			case KeyEvent.VK_SPACE:
				jump();
				break;
			default:
				break;
			}
		}
	}

	public void keyReleased(int e) {
		switch (e) {
		case KeyEvent.VK_A:
			setLeft(false);
			break;
		case KeyEvent.VK_LEFT:
			setLeft(false);
			break;
		case KeyEvent.VK_D:
			setRight(false);
			break;
		case KeyEvent.VK_RIGHT:
			setRight(false);
			break;
		case KeyEvent.VK_SPACE:
			// Cuts jump short when player stops holding the spacebar
			if (isJumping()) {
				setCurrentJumpSpeed(Math.min(getCurrentJumpSpeed(), getJumpspeed() / 2));
			}
			break;
		default:
			break;
		}
	}

	// Gets the player's current position as a rectangle, accounting for the global
	// xOffset
	@Override
	public Rectangle getRect() {
		return new Rectangle((int) (getX() + TickHandler.getXOffset()), (int) getY(), getWidth(), getHeight() + 2);
	}

	public static int getLives() {
		return lives;
	}

	public static void setLives(int lives) {
		PlayerUnit.lives = lives;
	}
}