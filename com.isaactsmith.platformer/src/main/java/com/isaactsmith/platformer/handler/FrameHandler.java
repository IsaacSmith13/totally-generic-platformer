package com.isaactsmith.platformer.handler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FrameHandler extends JPanel implements KeyListener {

	private static final long serialVersionUID = 1L;
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
//	private List<EnemyUnit> enemies;
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

		gameStateHandler = new GameStateHandler();

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
		gameStateHandler.tick();
		while (true) {
			time = System.nanoTime();
			delta += (time - lastRender) / 1000000;
			if ((time - lastUpdate) / 1000000 >= wait) {
				gameStateHandler.tick();
				lastUpdate = System.nanoTime();
			}
			if (delta >= 1) {
				repaint();
				delta--;
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		gameStateHandler.paint(g);
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
}