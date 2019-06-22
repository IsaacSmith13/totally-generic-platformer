package com.isaactsmith.platformer.handler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.isaactsmith.platformer.level.ImageLoader;
import com.isaactsmith.platformer.obj.unit.PlayerUnit;

@SuppressWarnings("serial")
public class FrameHandler extends JPanel implements KeyListener, Runnable {

	public static int WINDOW_WIDTH;
	public static int WINDOW_HEIGHT;
	private static final int TICKS_PER_SECOND = 100;
	// No need to change this; just change updates per second as needed
	private static final int MICROSECOND = 1000000;
	private static final int MICROSECONDS_PER_TICK = 1000 / TICKS_PER_SECOND;
	private final double startTime = System.currentTimeMillis();
	private static boolean isRunning;
	private static boolean isLoading = false;
	private static int levelNumber = 1;
	private GameStateHandler gameStateHandler;

	public FrameHandler(JFrame frame) {

		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
//		int width = 1600;
//		int height = 900;
		frame.setUndecorated(true);

//		
		// Initialize the window
		// Initialize the window
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);

		// Attach this panel to the window
		frame.add(this, BorderLayout.CENTER);

		// Adjust size and center frame
		setPreferredSize(new Dimension(width, height));
		WINDOW_WIDTH = getPreferredSize().width;
		WINDOW_HEIGHT = getPreferredSize().height;

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
			long lastTick = System.nanoTime();
			long lastRender = System.nanoTime();
			double delta = 0;
			long time;
			gameStateHandler.tick();
			while (isRunning) {
				time = System.nanoTime();
				delta += (time - lastRender) / MICROSECOND;
				if ((time - lastTick) / MICROSECOND >= MICROSECONDS_PER_TICK) {
					gameStateHandler.tick();
					lastTick = System.nanoTime();
				}
				if (delta >= 1) {
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
		requestFocusInWindow();
		if (isLoading) {
			paintLoadingScreen(g);
		} else {
			try {
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
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, FrameHandler.WINDOW_WIDTH, FrameHandler.WINDOW_HEIGHT);
		g.setFont(new Font("helvetica", Font.BOLD, 72));
		if (levelNumber == 0) {
			g.setColor(Color.RED);
			g.drawString("GAME OVER!", (int) (FrameHandler.WINDOW_WIDTH / 3), WINDOW_HEIGHT / 2);
		} else {
			g.setColor(new Color(242, 2, 190));
			g.drawString("Level " + levelNumber, (int) (FrameHandler.WINDOW_WIDTH / 2.45), WINDOW_HEIGHT / 2);
			for (int i = PlayerUnit.getLives(); i > 0; i--) {
				int x = 0;
				switch (PlayerUnit.getLives()) {
				case (2):
					x -= 20;
				case (1):
					x += 40;
				default:
					x += (int) (WINDOW_WIDTH / 2.375 + (i * 40));
					break;
				}
				g.drawImage(ImageLoader.getBufferedImage("PlayerRight0"), x, (WINDOW_HEIGHT / 2) + 32, 32, 32, null);
			}
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