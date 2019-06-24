package com.isaactsmith.platformer.handler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
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

	// Messages
	private static final String GAME_OVER = "GAME OVER!";
	// Record time the program starts
	private static final double startTime = System.currentTimeMillis();
	// How many times per second the game updates
	private static final int TICKS_PER_SECOND = 100;
	private static final int MICROSECOND = 1000000;
	private static final int MICROSECONDS_PER_TICK = 1000 / TICKS_PER_SECOND;
	// Initialize game state handler to handle which state is being
	// updated/repainted
	private static final GameStateHandler gameStateHandler = new GameStateHandler();
	// Calculate full screen and windowed sizes based on screen resolution
	private static Rectangle fullScreen;
	private static Rectangle windowed;
	// Current window size
	private static int windowWidth;
	private static int windowHeight;
	// Current game states
	private static boolean isLoading = false;
	private static int levelNumber = 1;
	private static JFrame frame;

	public FrameHandler(JFrame frame) {
		fullScreen = frame.getGraphicsConfiguration().getBounds();
		// Windowed display is 3/4ths of the screen size
		windowed = new Rectangle((int) frame.getGraphicsConfiguration().getBounds().getWidth() * 3 / 4,
				(int) frame.getGraphicsConfiguration().getBounds().getHeight() * 3 / 4);
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
				// Game state handler paints the appropriate level state
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
		// Paint black background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, windowWidth, windowHeight);
		if (levelNumber == 0) {
			// Level 0 is menu, so the player must have just gotten a game over
			g.setColor(Color.RED);
			CenteredStringHandler.drawScreenCenteredString(g, GAME_OVER);
		} else {
			int globalSize = Obj.getGlobalSize();
			// Otherwise paint the current level number and remaining lives
			g.setColor(new Color(242, 2, 190));
			CenteredStringHandler.drawScreenCenteredString(g, "Level " + levelNumber);
			for (int i = PlayerUnit.getLives(); i > 0; i--) {
				int x = 0;
				switch (PlayerUnit.getLives()) {
				case (2):
					x -= globalSize * 1.25 / 2;
				case (1):
					x += globalSize * 1.25;
				default:
					x += (int) (windowWidth / 2.4 + (i * globalSize * 1.25));
					break;
				}
				g.drawImage(ImageLoader.getBufferedImage("PlayerRight0"), x, (windowHeight / 2) + 10, globalSize, globalSize, null);
			}
		}
		
		
	}

	public static void setFullScreen() {
		frame.setBounds(fullScreen);
		windowWidth = frame.getWidth();
		windowHeight = frame.getHeight();
		// Updates the global size and scalar
		Obj.reScale();
	}

	public static void setWindowed() {
		frame.setBounds(windowed);
		frame.setLocationRelativeTo(null);
		windowWidth = frame.getWidth();
		windowHeight = frame.getHeight();
		// Updates the global size and scalar
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