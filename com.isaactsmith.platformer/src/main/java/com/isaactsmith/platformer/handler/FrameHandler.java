package com.isaactsmith.platformer.handler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.isaactsmith.platformer.levelReadWrite.LevelReader;
import com.isaactsmith.platformer.levelReadWrite.LevelWriter;
import com.isaactsmith.platformer.obj.Tile;
import com.isaactsmith.platformer.obj.unit.PlayerUnit;

public class FrameHandler extends JPanel implements KeyListener {

	private static final long serialVersionUID = 1L;
	private static final String LEVEL_PATH = "testlevel.level";
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	private boolean isRunning = true;
	private List<Tile> terrain;
//	private List<EnemyUnit> enemies;
	private PlayerUnit player;
	private UnitHandler unitHandler;
	private GameStateHandler gameStateHandler;

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
	}

	public void run() {

//		gameStateHandler = new GameStateHandler();

		// Call LevelWriter to "randomly" generate a level and write it to the file at
		// LEVEL_PATH
		LevelWriter.writeLevel(LEVEL_PATH);
		// Call LevelReader to read the file and generate tiles, enemies, and a player
		// at the specified locations
		LevelReader levelReader = new LevelReader();
		levelReader.loadLevel(LEVEL_PATH);
		player = levelReader.getPlayer();
		terrain = levelReader.getTerrain();
//		enemies = levelReader.getEnemies();
		unitHandler = new UnitHandler(player, terrain);

		// Attach a custom key listener to the panel
		setFocusable(true);
		addKeyListener(this);

		// While game is running, render a new frame every second
		long lastUpdate = System.nanoTime();
		long lastRender = System.nanoTime();
		double ups = 100;
		double wait = 1000 / ups;
		double delta = 0;
		long time;
		if (!(player == null || terrain == null || terrain.isEmpty())) {
			while (isRunning) {
				time = System.nanoTime();
				delta += (time - lastRender) / 1000000;
				if ((time - lastUpdate) / 1000000 >= wait) {
					tick();
					lastUpdate = System.nanoTime();
				}
				if (delta >= 1) {
					repaint();
					delta--;
				}
			}
		}
	}

	public void tick() {
		unitHandler.tick(player);
//		gameStateHandler.tick();
	}

	@Override
	public void paint(Graphics g) {
//		gameStateHandler.paint(g);
		try {
			requestFocusInWindow();
			g.setColor(new Color(135, 206, 235));
			g.fillRect(0, 0, getWidth(), getHeight());
			for (int i = 0, terrainSize = terrain.size(); i < terrainSize; i++) {
				terrain.get(i).paint(g);
			}

			// for (EnemyUnit enemy : enemies) {
			// enemy.paint(g);
			// }
			player.paint(g);
		} catch (Exception e) {
			// Do nothing, sometimes list of tiles not complete in the first frame or two of
			// the game
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		player.keyPressed(e);
//		gameStateHandler.keyPressed(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		player.keyReleased(e);
//		gameStateHandler.keyReleased(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}