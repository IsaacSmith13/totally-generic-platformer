package com.isaactsmith.platformer.obj;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class InputHandler implements KeyListener {
	
	private JPanel panel;
	
	public InputHandler(JPanel panel) {
		this.panel = panel;
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		panel.setBackground(Color.green);
		
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
