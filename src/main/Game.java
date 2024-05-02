package main;

import java.awt.Graphics;
import java.awt.Toolkit;

import gamestates.GameState;
import gamestates.Menu;
import gamestates.Playing;

public class Game implements Runnable {

	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;

	private Menu menu;
	private Playing playing;

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
		menu = new Menu(this);
		playing = new Playing(this);
	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	private void update() {
		
		switch (GameState.gamestate) {
			case MENU:
				menu.update();
				break;
			case PLAYING:
				playing.update();
				break;
			case OPTIONS:
			case QUIT:
				System.exit(0);
				break;
			default:
				break;
		}
	}

	public void render(Graphics g) {

		switch (GameState.gamestate) {
			case MENU:
				menu.draw(g);
				break;
			case PLAYING:
				playing.draw(g);
				break;
			default:
				break;
		}
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
		if (GameState.gamestate == GameState.PLAYING){
			playing.windowFocusLost();
		}
	}

	public Menu getMenu() {
		return menu;
	}

    public Playing getPlaying() {
		return playing;
    }

}
