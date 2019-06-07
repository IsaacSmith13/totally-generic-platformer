package com.isaactsmith.platformer.obj;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FrameHandler extends JPanel {

	private static final long serialVersionUID = 1L;
	private boolean isRunning = true;
	private JFrame frame;
	
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
		addKeyListener(new InputHandler(this));
	}

	public void run() {
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
			setBackground(Color.red);
		} else {
			setBackground(Color.blue);
		}
		// TODO - Display a frame of the game
	}
}
