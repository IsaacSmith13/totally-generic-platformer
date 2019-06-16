package com.isaactsmith.platformer.obj.unit;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import com.isaactsmith.platformer.handler.FrameHandler;
import com.isaactsmith.platformer.handler.TickHandler;

public class PlayerUnit extends Unit {

	private static final int spawnX = GLOBAL_SIZE * 5;
	private static final int initialX = (FrameHandler.WINDOW_WIDTH / 2) - (GLOBAL_SIZE / 2);
	private static final int initialY = (FrameHandler.WINDOW_HEIGHT / 2) - (GLOBAL_SIZE / 2);
	private static int lives = 3;
	private double timeOfDeath = -1;

	public PlayerUnit(BufferedImage[] images) {
		super(GLOBAL_SIZE * 5, initialY, images);
		setActive(true);
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(getImageToRender(), (int) Math.round(getX()) - 3, (int) Math.round(getY()) - 3, getHeight() + 5,
				getWidth() + 5, null);
	}

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
		}
		if (isLeft() && !willCollideLeft()) {
			if (xOffset - moveSpeed < 0) {
				if (xOffset - x - moveSpeed < 0) {
					setX(x - moveSpeed);
				}
			} else {
				TickHandler.setXOffset(xOffset - moveSpeed);
			}
		}
	}

	public void die() {
		if (lives > 0) {
			if (timeOfDeath < 0) {
				setActive(false);
				setLeft(false);
				setRight(false);
				setY(FrameHandler.WINDOW_HEIGHT + 50);
				timeOfDeath = System.currentTimeMillis() / 1000;
			}
			int xOffset = (int) TickHandler.getXOffset();
			int deathCameraSpeed = Math.max(Math.abs(xOffset) / GLOBAL_SIZE, 4);
			if (xOffset > deathCameraSpeed) {
				xOffset -= deathCameraSpeed;
			} else if (xOffset < -deathCameraSpeed) {
				xOffset += deathCameraSpeed;
			} else if (System.currentTimeMillis() / 1000 > timeOfDeath + 3) {
				setActive(true);
				timeOfDeath = -1;
				lives--;
				xOffset = 0;
				setLocation(spawnX, initialY);
			}
			TickHandler.setXOffset(xOffset);
		} else {
			lives = 3;
			TickHandler.setXOffset(0);
			FrameHandler.setRunning(false);
		}
	}

	public void keyPressed(int e) {
		if (isActive()) {
			switch (e) {
			case KeyEvent.VK_A:
				setLeft(true);
				break;
			case KeyEvent.VK_LEFT:
				setLeft(true);
				break;
			case KeyEvent.VK_D:
				setRight(true);
				break;
			case KeyEvent.VK_RIGHT:
				setRight(true);
				break;
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
			if (isJumping()) {
				setCurrentJumpSpeed(Math.min(getCurrentJumpSpeed(), getJumpspeed() / 2));
			}
			break;
		default:
			break;
		}
	}

	public Rectangle getRect() {
		return new Rectangle((int) (getX() + TickHandler.getXOffset()), (int) getY(), getWidth(), getHeight() + 2);
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		PlayerUnit.lives = lives;
	}
}