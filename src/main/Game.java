package main;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.logging.Level;

import entities.Player;
import levels.LevelManager;

public class Game implements Runnable {

	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;

	private Player player;
	private LevelManager levelManager;

	public final static int TILES_DEFAULT_SIZE = 24;
	public final static float SCALE = 2f; 
	public final static int TILES_IN_WIDTH = 26;
	public final static int TILES_IN_HEIGHT = 14;
	public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

	public Game() {

		initClasses();
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.requestFocus();
		
		startGameLoop(); // Alaways last line of the constructor
	}

	private void initClasses() {
		levelManager = new LevelManager(this);
		player = new Player(200, 200);
	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	private void update() {
		player.update();
		levelManager.update();
	}

	public void render(Graphics g) {
		levelManager.draw(g);
		player.render(g);
	}

	@Override
	public void run() {

		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;

		long previousTime = System.nanoTime();
		long lastCheck = System.currentTimeMillis();

		int frames = 0;
		int updates = 0;

		double deltaUpdates = 0;
		double deltaFrames = 0;

		while (true) {

			long currentTime = System.nanoTime();

			deltaUpdates += (currentTime - previousTime) / timePerUpdate;
			deltaFrames += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			if (deltaUpdates >= 1) {
				update();
				updates++;
				deltaUpdates--;
			}
			
			if (deltaFrames >= 1) {
				gamePanel.repaint();
				Toolkit.getDefaultToolkit().sync();
				frames++;
				deltaFrames--;
			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;
			}
		}

	}

	public void windowFocusLost() {
		player.resetDirectionBooleans();
	}

	public Player getPlayer() {
		return player;
	}

}
