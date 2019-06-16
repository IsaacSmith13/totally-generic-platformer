package com.isaactsmith.platformer.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import com.isaactsmith.platformer.handler.FrameHandler;
import com.isaactsmith.platformer.handler.GameStateHandler;
import com.isaactsmith.platformer.handler.TickHandler;
import com.isaactsmith.platformer.level.ImageLoader;
import com.isaactsmith.platformer.level.LevelLoader;
import com.isaactsmith.platformer.obj.tile.Tile;
import com.isaactsmith.platformer.obj.unit.EnemyUnit;
import com.isaactsmith.platformer.obj.unit.PlayerUnit;

public class LevelState extends GameState {

	// "Magic numbers" that allow the background parallax to render properly
	// -- do not alter
	private static final double PARALLAX_MODIFIER = -.3;
	private static final int BACKGROUND_OFFSET = 3;

	private static final String BACKGROUND_IMAGE_PATH = "Background";
	private static final int WINDOW_WIDTH = FrameHandler.WINDOW_WIDTH;
	private static final int WINDOW_HEIGHT = FrameHandler.WINDOW_HEIGHT;
	private final int currentLevelNumber;
	private LevelLoader currentLevel;
	private BufferedImage background;
	private List<Tile> winningTiles;
	private Tile[][] tiles;
	private List<Tile> terrain;
	private List<Tile> movingTiles;
	private List<EnemyUnit> enemies;
	private PlayerUnit player;
	private TickHandler tickHandler;
	private boolean hasWon = false;

	public LevelState(GameStateHandler gameStateHandler, int levelNumber) {
		super(gameStateHandler);
		currentLevelNumber = levelNumber;
		currentLevel = new LevelLoader(levelNumber);
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
			hasWon = false;
			gameStateHandler.loadLevel(currentLevelNumber + 1);
//			SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
//				@Override
//				protected Void doInBackground() throws Exception {
//					FrameHandler.setLevelNumber(currentLevelNumber + 1);
//					FrameHandler.setLoading(true);
//					Stack<GameState> gameStates = gameStateHandler.getGameStates();
//					gameStates.push(new LevelState(gameStateHandler, currentLevelNumber + 1));
//					FrameHandler.setLoading(false);
//					return null;
//				}
//			};
//			worker.execute();
//			Stack<GameState> gameStates = gameStateHandler.getGameStates();
//			gameStates.push(new LevelState(gameStateHandler, currentLevelNumber + 1));
//			gameStateHandler.loadLevel(currentLevelNumber + 1);
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
		int xOffsetParallax = (int) (TickHandler.getXOffset() * PARALLAX_MODIFIER);

		while (xOffsetParallax < WINDOW_WIDTH * BACKGROUND_OFFSET * PARALLAX_MODIFIER) {
			xOffsetParallax += WINDOW_WIDTH - BACKGROUND_OFFSET;
		}
		g.drawImage(background, xOffsetParallax - WINDOW_WIDTH + BACKGROUND_OFFSET, 0, WINDOW_WIDTH, WINDOW_HEIGHT,
				null);
		g.drawImage(background, xOffsetParallax, 0, WINDOW_WIDTH, WINDOW_HEIGHT, null);
		g.drawImage(background, xOffsetParallax + WINDOW_WIDTH - BACKGROUND_OFFSET, 0, WINDOW_WIDTH, WINDOW_HEIGHT,
				null);
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
		// Paint lives
		g.setColor(Color.BLACK);
		g.setFont(new Font("helvetica", Font.PLAIN, 16));
		g.drawString("Lives: ", (int) (WINDOW_WIDTH / 2 - 50), 25);
		for (int i = player.getLives(); i > 0; i--) {
			g.drawImage(player.getImages()[0], (int) (WINDOW_WIDTH / 2 + (i * 12) - 15), 10, 16, 16, null);
		}
	}

	public void paintEnemies(Graphics g) {
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).paint(g);
		}
	}

	@Override
	public void keyPressed(int e) {
		player.keyPressed(e);
	}

	@Override
	public void keyReleased(int e) {
		player.keyReleased(e);
	}
}