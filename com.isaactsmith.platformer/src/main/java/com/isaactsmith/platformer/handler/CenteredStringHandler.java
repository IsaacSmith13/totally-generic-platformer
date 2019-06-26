package com.isaactsmith.platformer.handler;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.isaactsmith.platformer.obj.Obj;

public abstract class CenteredStringHandler {

	public static void drawScreenCenteredString(Graphics g, String text) {
		int stringHeight = Obj.getGlobalSize() * 2;
		Rectangle drawSpace = new Rectangle(0, FrameHandler.getWindowHeight() / 2 - stringHeight,
				FrameHandler.getWindowWidth(), Obj.getGlobalSize() * 3);
		drawCenteredString(g, text, drawSpace, new Font("helvetica", Font.BOLD, stringHeight));
	}

	public static void drawCenteredString(Graphics g, String text, Rectangle drawSpace, Font font) {
		// Get the FontMetrics of the specified font
		FontMetrics metrics = g.getFontMetrics(font);
		int x = drawSpace.x + (drawSpace.width - metrics.stringWidth(text)) / 2;
		// Add the ascent to the Y coordinate, as 2d 0 is the top of the screen
		int y = drawSpace.y + ((drawSpace.height - metrics.getHeight()) / 2) + metrics.getAscent();
		g.setFont(font);
		g.drawString(text, x, y);
	}
}
