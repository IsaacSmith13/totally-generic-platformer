package com.isaactsmith.platformer.obj;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FrameHandler extends JPanel {

	private JFrame frame;
	private JPanel panel;
	private static final long serialVersionUID = 1L;
	private boolean isRunning = true;

	public void run() {
		// Initialize window
		frame = new JFrame("Totally Generic Platformer");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(800, 600));
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(800, 600));
		panel.setBackground(Color.black);
		panel.setFocusable(true);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		
		// Add custom key listener to window
		panel.addKeyListener(new InputHandler(panel));
		
		// While game is running, render a new frame every second
		while (isRunning) {
			try {
				Thread.sleep(1000);
				renderFrame();
			} catch (Exception e) {
				e.getStackTrace();
				isRunning = false;
			}
		}
		
	}
	
	public void renderFrame() {
		if (Math.random() < .5) {
			panel.setBackground(Color.red);
		}
		else {
			panel.setBackground(Color.blue);
		}
		// TODO - Display a frame of the game
	}
}
