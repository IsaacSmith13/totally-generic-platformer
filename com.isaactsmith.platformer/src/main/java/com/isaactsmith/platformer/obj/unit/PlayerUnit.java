package com.isaactsmith.platformer.obj.unit;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import com.isaactsmith.platformer.handler.FrameHandler;

public class PlayerUnit extends Unit {

	public PlayerUnit(BufferedImage[] images) {
		super(FrameHandler.WINDOW_WIDTH / 2, FrameHandler.WINDOW_HEIGHT / 2, images);
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
			setCurrentJumpSpeed(Math.max(getCurrentJumpSpeed(), getJumpspeed() / 2));
			break;
		default:
			break;
		}
	}
}