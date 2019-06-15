package com.isaactsmith.platformer.obj.unit;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import com.isaactsmith.platformer.handler.FrameHandler;
import com.isaactsmith.platformer.handler.UnitHandler;
import com.isaactsmith.platformer.obj.Obj;

public class PlayerUnit extends Unit {

	private int lives = 3;
	private double timeOfDeath = -1;

	public PlayerUnit(BufferedImage[] images) {
		super((FrameHandler.WINDOW_WIDTH / 2) - (GLOBAL_SIZE / 2), (FrameHandler.WINDOW_HEIGHT / 2) - (GLOBAL_SIZE / 2),
				images);
		setActive(true);
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(getImageToRender(), (int) Math.round(getX()) - 3, (int) Math.round(getY()) - 3, getHeight() + 5,
				getWidth() + 5, null);
	}

	public void walk() {
		if (isRight() && !willCollideRight()) {
			UnitHandler.setXOffset(UnitHandler.getXOffset() + getMoveSpeed());
		}
		if (isLeft() && !willCollideLeft()) {
			UnitHandler.setXOffset(UnitHandler.getXOffset() - getMoveSpeed());
		}
	}

	public void die() {
		if (lives > 0) {
			if (timeOfDeath < 0) {
				setActive(false);
				setY(FrameHandler.WINDOW_HEIGHT + 50);
				timeOfDeath = System.currentTimeMillis() / 1000;
			}
			int xOffset = (int) UnitHandler.getXOffset();
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
				setLocation((FrameHandler.WINDOW_WIDTH / 2) - (Obj.GLOBAL_SIZE / 2),
						(FrameHandler.WINDOW_HEIGHT / 2) - (Obj.GLOBAL_SIZE / 2));
			}
			UnitHandler.setXOffset(xOffset);
		} else {
			UnitHandler.setXOffset(0);
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
		return new Rectangle((int) (getX() + UnitHandler.getXOffset()), (int) getY(), getWidth(), getHeight() + 2);
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}
}