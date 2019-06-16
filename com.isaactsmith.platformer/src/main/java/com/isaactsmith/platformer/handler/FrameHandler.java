package com.isaactsmith.platformer.handler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.isaactsmith.platformer.level.ImageLoader;
import com.isaactsmith.platformer.obj.unit.PlayerUnit;

@SuppressWarnings("serial")
public class FrameHandler extends JPanel implements KeyListener, Runnable {

	public static final int WINDOW_WIDTH = 1280;
	public static final int WINDOW_HEIGHT = 720;
	private GameStateHandler gameStateHandler;
	private static boolean isRunning;
	private final double startTime = System.currentTimeMillis();
	private static boolean isLoading = false;
	private static int levelNumber = 1;

	public FrameHandler(JFrame frame) {
		// Initialize the window
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);

		// Attach this panel to the window
		frame.add(this, BorderLayout.CENTER);

		// Adjust size and center frame
		setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

		frame.pack();
		frame.setLocationRelativeTo(null);
		// Attach a custom key listener to the panel
		setFocusable(true);
		addKeyListener(this);
	}

	@Override
	public void run() {
		while (true) {
			isRunning = true;
			gameStateHandler = new GameStateHandler();
			long lastUpdate = System.nanoTime();
			long lastRender = System.nanoTime();
			double ups = 100;
			double wait = 1000 / ups;
			double delta = 0;
			long time;
			gameStateHandler.tick();
			while (isRunning) {
				time = System.nanoTime();
				delta += (time - lastRender) / 1000000;
				if ((time - lastUpdate) / 1000000 >= wait) {
					gameStateHandler.tick();
					lastUpdate = System.nanoTime();
				}
				if (delta >= 2) {
					repaint();
					delta = 0;
				}
			}
		}
	}

	public static void setRunning(boolean isRunning) {
		FrameHandler.isRunning = isRunning;
	}

	@Override
	public void paint(Graphics g) {
		if (isLoading) {
			paintLoadingScreen(g);
		} else {
			try {
				requestFocusInWindow();
				gameStateHandler.paint(g);
			} catch (NullPointerException e) {
				// Sometimes objects don't load in the first few seconds, so ignore error if
				// game just launched
				if (startTime + 5000 > System.currentTimeMillis()) {
					System.out.println("Objects not loaded yet");
				} else {
					e.printStackTrace();
				}
			}
		}
	}

	private void paintLoadingScreen(Graphics g) {
		requestFocusInWindow();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, FrameHandler.WINDOW_WIDTH, FrameHandler.WINDOW_HEIGHT);
		g.setColor(new Color(242, 2, 190));
		g.setFont(new Font("helvetica", Font.BOLD, 72));
		g.drawString("Level " + levelNumber, (int) (FrameHandler.WINDOW_WIDTH / 2.45), WINDOW_HEIGHT / 2);
		for (int i = PlayerUnit.getLives(); i > 0; i--) {
			g.drawImage(ImageLoader.getBufferedImage("PlayerRight0"),
					(int) (WINDOW_WIDTH / 2 + (i * 40) - PlayerUnit.getLives() * 35), WINDOW_HEIGHT / 2 + 32, 32, 32,
					null);
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		gameStateHandler.keyPressed(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		gameStateHandler.keyReleased(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public static boolean isLoading() {
		return isLoading;
	}

	public static void setLoading(boolean isLoading) {
		FrameHandler.isLoading = isLoading;
	}

	public static int getLevelNumber() {
		return levelNumber;
	}

	public static void setLevelNumber(int levelNumber) {
		FrameHandler.levelNumber = levelNumber;
	}
}