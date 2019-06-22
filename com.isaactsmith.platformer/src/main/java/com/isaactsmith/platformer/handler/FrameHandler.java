package com.isaactsmith.platformer.handler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.isaactsmith.platformer.level.ImageLoader;
import com.isaactsmith.platformer.obj.Obj;
import com.isaactsmith.platformer.obj.unit.PlayerUnit;

@SuppressWarnings("serial")
public class FrameHandler extends JPanel implements KeyListener, Runnable {

	// Calculate full screen and windowed sizes based on screen resolution
	private static final GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	private static final Rectangle fullScreen = new Rectangle(gd.getDisplayMode().getWidth(),
			gd.getDisplayMode().getHeight());
	private static final Rectangle windowed = new Rectangle(gd.getDisplayMode().getWidth() / 2,
			gd.getDisplayMode().getHeight() / 2);
	// Record time the program starts
	private static final double startTime = System.currentTimeMillis();
	// How many times per second the game updates
	private static final int TICKS_PER_SECOND = 100;
	private static final int MICROSECOND = 1000000;
	private static final int MICROSECONDS_PER_TICK = 1000 / TICKS_PER_SECOND;
	// Initialize game state handler to handle which state is being
	// updated/repainted
	private static final GameStateHandler gameStateHandler = new GameStateHandler();
	// Current window size
	private static int windowWidth;
	private static int windowHeight;
	// Current game states
	private static boolean isLoading = false;
	private static int levelNumber = 1;
	private static JFrame frame;

	public FrameHandler(JFrame frame) {
		FrameHandler.frame = frame;
		// Initialize the window
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setResizable(true);
		// Adjust window size -- starts off in windowed mode
		setPreferredSize(new Dimension((int) windowed.getWidth(), (int) windowed.getHeight()));
		// Attach this panel to the window
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		// Center window
		frame.setLocationRelativeTo(null);
		// Attach a custom key listener to the panel
		setFocusable(true);
		addKeyListener(this);
		// Update current window dimension variables
		windowWidth = getPreferredSize().width;
		windowHeight = getPreferredSize().height;
	}

	@Override
	public void run() {
		long lastTick = System.nanoTime();
		long lastRender = System.nanoTime();
		double delta = 0;
		long time;
		gameStateHandler.tick();
		// Update and refresh game forever
		while (true) {
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

	@Override
	public void paint(Graphics g) {
		requestFocusInWindow();
		windowWidth = frame.getWidth();
		windowHeight = frame.getHeight();
		if (isLoading) {
			paintLoadingScreen(g);
		} else {
			try {
				gameStateHandler.paint(g);
			} catch (NullPointerException e) {
				// Sometimes objects don't load in the first few seconds, so ignore error if
				// game just launched
				if (startTime + 5000 < System.currentTimeMillis()) {
					e.printStackTrace();
				}
			}
		}
	}

	private void paintLoadingScreen(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, windowWidth, windowHeight);
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 72));
		if (levelNumber == 0) {
			g.setColor(Color.RED);
			g.drawString("GAME OVER!", (int) (windowWidth / 3), windowHeight / 2);
		} else {
			g.setColor(new Color(242, 2, 190));
			g.drawString("Level " + levelNumber, (int) (windowWidth / 2.45), windowHeight / 2);
			for (int i = PlayerUnit.getLives(); i > 0; i--) {
				int x = 0;
				switch (PlayerUnit.getLives()) {
				case (2):
					x -= 20;
				case (1):
					x += 40;
				default:
					x += (int) (windowWidth / 2.375 + (i * 40));
					break;
				}
				g.drawImage(ImageLoader.getBufferedImage("PlayerRight0"), x, (windowHeight / 2) + 32, 32, 32, null);
			}
		}
	}

	public static void setFullScreen() {
		frame.setBounds(fullScreen);
		windowWidth = frame.getWidth();
		windowHeight = frame.getHeight();
		Obj.reScale();
	}

	public static void setWindowed() {
		frame.setBounds(windowed);
		frame.setLocationRelativeTo(null);
		windowWidth = frame.getWidth();
		windowHeight = frame.getHeight();
		Obj.reScale();
	}

	public static int getWindowWidth() {
		return windowWidth;
	}

	public static void setWindowWidth(int windowWidth) {
		FrameHandler.windowWidth = windowWidth;
	}

	public static int getWindowHeight() {
		return windowHeight;
	}

	public static void setWindowHeight(int windowHeight) {
		FrameHandler.windowHeight = windowHeight;
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