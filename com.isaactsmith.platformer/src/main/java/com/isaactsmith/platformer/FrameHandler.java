package com.isaactsmith.platformer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.isaactsmith.platformer.obj.EnemyUnit;
import com.isaactsmith.platformer.obj.PlayerUnit;
import com.isaactsmith.platformer.obj.Tile;

public class FrameHandler extends JPanel implements KeyListener {

	private static final long serialVersionUID = 1L;
	private boolean isRunning = true;
	private JFrame frame;
	private List<Tile> terrain;
	private List<EnemyUnit> enemies;
	private PlayerUnit player;

	public FrameHandler(JFrame frame) {
		// Initialize the window
		this.frame = frame;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);

		// Attach this panel to the window
		frame.add(this, BorderLayout.CENTER);

		// Adjust size and center frame
		setPreferredSize(new Dimension(800, 600));
		frame.pack();
		frame.setLocationRelativeTo(null);
		setBackground(Color.black);

		// Attach a custom key listener to the panel
		setFocusable(true);
		addKeyListener(this);
	}

	public void run() {
		
		// temporary for testing
		terrain = new ArrayList<Tile>();
		enemies = new ArrayList<EnemyUnit>();
		BufferedImage playerImage = new BufferedImage(50, 50, 
			     BufferedImage.TYPE_INT_ARGB);
		
		playerImage.createGraphics();
		playerImage.getGraphics().setColor(new Color(2,1,3));
		playerImage.getGraphics().fillRect(0, 0, 50, 50);
		
//		BufferedImage image2 = new BufferedImage(50, 50, 
//			     BufferedImage.TYPE_INT_ARGB);
//		
//		image2.createGraphics();
//		image2.getGraphics().setColor(new Color(2,1,3));
//		image2.getGraphics().fillRect(0, 0, 50, 50);
		
		player = new PlayerUnit(70, 50, playerImage);
		
//		terrain.add(new Tile(50, 300, image2));
		
		for (int i = 0; i < 30; i++) {
			BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_4BYTE_ABGR);
			image.createGraphics();
			image.getGraphics().setColor(new Color((int)Math.random() * 255, (int)Math.random() * 255, (int)Math.random() * 255));
			image.getGraphics().fillRect(0, 0, 50, 50);
			terrain.add(new Tile(i * 32, 400, image));
		}
		
		
		
		
		// While game is running, render a new frame every second
		long lastUpdate = System.nanoTime();
		long lastRender = System.nanoTime();
		double ups = 100;
		double wait = 1000 / ups;
		double delta = 0;
		long time;
		while (isRunning) {
			time = System.nanoTime();
			delta += (time - lastRender) / 1000000;
			if ((time - lastUpdate) / 1000000 >= wait) {
				tick();
				repaint();
				lastUpdate = System.nanoTime();
			}
		}
	}

	public void tick() {
		player.handleFalling(terrain);
		player.tick();
//		for (List<Enemy> enemy : enemies) {
//			enemy.handleFalling(terrain);
//			if player(isCollidingWith(enemy)) {
//				if (player.killsEnemy(enemy)) {
//					// kill enemy
//				} else {
//					// kill player
//				}
//			}
//			for (List<Enemy> secondEnemy : enemies) {
//				if enemy(isCollidingWith(secondEnemy)) {
//					// stop collision
//				}
//			}
//			for (List<Tile> tile : terrain) {
//				if (player.isOn((Obj)tile)) {
//					//stop collision
//				}
//				if player(isCollidingWith(tile)) {
//					//stop collision
//				}
//			}		
//			
//		}
	}

	public void paint(Graphics g) {
		g.setColor(new Color(135, 206, 235));
		g.fillRect(0, 0, getWidth(), getHeight());
		for (Tile tile : terrain) {
			tile.paint(g);
		}
		for (EnemyUnit enemy : enemies) {
			enemy.paint(g);
		}
		player.paint(g);
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
}