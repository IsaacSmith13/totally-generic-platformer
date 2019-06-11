package com.isaactsmith.platformer.obj.unit;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import com.isaactsmith.platformer.handler.FrameHandler;
import com.isaactsmith.platformer.handler.UnitHandler;

public class PlayerUnit extends Unit {

	public PlayerUnit(BufferedImage[] images) {
		super((FrameHandler.WINDOW_WIDTH / 2) - (GLOBAL_SIZE / 2), (FrameHandler.WINDOW_HEIGHT / 2) - (GLOBAL_SIZE / 2), images);
	}

	public void walk() {
		if (isRight() && !willCollideRight()) {
			UnitHandler.setXOffset(UnitHandler.getXOffset() + getMoveSpeed());
		}
		if (isLeft() && !willCollideLeft()) {
			UnitHandler.setXOffset(UnitHandler.getXOffset() - getMoveSpeed());
		}
	}

	public void keyPressed(int e) {
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
}