package com.isaactsmith.platformer.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.List;

import com.isaactsmith.platformer.handler.CenteredStringHandler;
import com.isaactsmith.platformer.handler.FrameHandler;
import com.isaactsmith.platformer.handler.GameStateHandler;
import com.isaactsmith.platformer.handler.TickHandler;
import com.isaactsmith.platformer.level.ImageLoader;
import com.isaactsmith.platformer.level.LevelLoader;
import com.isaactsmith.platformer.obj.Obj;
import com.isaactsmith.platformer.obj.tile.Tile;
import com.isaactsmith.platformer.obj.unit.EnemyUnit;
import com.isaactsmith.platformer.obj.unit.PlayerUnit;

public class LevelState extends GameState {

	// "Magic numbers" that allow the background parallax to render properly
	// -- do not alter
	private static final double PARALLAX_MODIFIER = -.3;
	private static final int BACKGROUND_OFFSET = 3;

	private static final String BACKGROUND_IMAGE_PATH = "Background";
	private static final int MENU_LEVEL_NUMBER = 0;
	private final int currentLevelNumber;
	private final LevelLoader currentLevel;
	private final BufferedImage background;
	private final List<Tile> winningTiles;
	private final Tile[][] tiles;
	private final List<Tile> terrain;
	private final List<Tile> movingTiles;
	private final List<EnemyUnit> enemies;
	private final PlayerUnit player;
	private final TickHandler tickHandler;
	private boolean hasWon = false;
	private boolean gameOver = false;

	public LevelState(GameStateHandler gameStateHandler, int levelNumber) {
		super(gameStateHandler);
		currentLevelNumber = levelNumber;
		currentLevel = new LevelLoader(levelNumber);
		currentLevel.readLevel();
		background = ImageLoader.getBufferedImage(BACKGROUND_IMAGE_PATH);
		winningTiles = currentLevel.getWinningTiles();
		tiles = currentLevel.getTiles();
		terrain = currentLevel.getTerrain();
		movingTiles = currentLevel.getMovingTiles();
		enemies = currentLevel.getEnemies();
		player = currentLevel.getPlayer();
		tickHandler = new TickHandler(winningTiles, tiles, movingTiles, enemies, player);
	}

	@Override
	public void tick() {
		if (tickHandler.tick() && !hasWon) {
			gameStateHandler.loadLevel(currentLevelNumber + 1);
			hasWon = false;
		} else if (PlayerUnit.getLives() < 0 && !gameOver) {
			gameOver = true;
			PlayerUnit.setLives(3);
			gameStateHandler.loadLevel(MENU_LEVEL_NUMBER);
			gameOver = false;
		}
	}

	@Override
	public void paint(Graphics g) {
		paintBackground(g);
		paintTerrain(g);
		paintTiles(g);
		paintDisplay(g);
		paintEnemies(g);
		player.paint(g);
	}

	public void paintBackground(Graphics g) {
		final int windowWidth = FrameHandler.getWindowWidth();
		final int windowHeight = FrameHandler.getWindowHeight();
		int xOffsetParallax = (int) (TickHandler.getXOffset() * PARALLAX_MODIFIER);

		while (xOffsetParallax < windowWidth * BACKGROUND_OFFSET * PARALLAX_MODIFIER) {
			xOffsetParallax += windowWidth - BACKGROUND_OFFSET;
		}
		g.drawImage(background, xOffsetParallax - windowWidth + BACKGROUND_OFFSET, 0, windowWidth, windowHeight, null);
		g.drawImage(background, xOffsetParallax, 0, windowWidth, windowHeight, null);
		g.drawImage(background, xOffsetParallax + windowWidth - BACKGROUND_OFFSET, 0, windowWidth, windowHeight, null);
	}

	public void paintTerrain(Graphics g) {
		for (int i = 0, terrainAmount = terrain.size(); i < terrainAmount; i++) {
			terrain.get(i).paint(g);
		}
	}

	public void paintTiles(Graphics g) {
		for (int y = 0, height = tiles.length; y < height; y++) {
			for (int x = 0, width = tiles[y].length; x < width; x++) {
				if (tiles[y][x] != null) {
					tiles[y][x].paint(g);
				}
			}
		}
		for (int i = 0, movingTilesAmount = movingTiles.size(); i < movingTilesAmount; i++) {
			movingTiles.get(i).paint(g);
		}
		for (int i = 0, winningTilesAmount = winningTiles.size(); i < winningTilesAmount; i++) {
			winningTiles.get(i).paint(g);
		}
	}

	public void paintDisplay(Graphics g) {
		int globalSize = Obj.getGlobalSize();
		// Paint lives
		g.setColor(Color.BLACK);
		g.setFont(new Font("helvetica", Font.PLAIN, 16));
		Rectangle drawSpace = new Rectangle(0, globalSize, FrameHandler.getWindowWidth() - globalSize * 2, globalSize);
		CenteredStringHandler.drawCenteredString(g, "Lives:", drawSpace,
				new Font("helvetica", Font.BOLD, globalSize / 2));
		for (int i = PlayerUnit.getLives(); i > 0; i--) {
			g.drawImage(player.getImages()[0],
					FrameHandler.getWindowWidth() / 2 + (i * globalSize / 2) - globalSize / 2,
					(int) (globalSize * 1.15), globalSize * 3 / 4, globalSize * 3 / 4, null);
		}
	}

	public void paintEnemies(Graphics g) {
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).paint(g);
		}
	}

	@Override
	public void keyPressed(int e) {
		if (e == KeyEvent.VK_ESCAPE) {
			gameStateHandler.pause();
		} else {
			player.keyPressed(e);
		}
	}

	@Override
	public void keyReleased(int e) {
		player.keyReleased(e);
	}
}