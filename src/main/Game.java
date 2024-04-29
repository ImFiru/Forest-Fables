package main;

import java.awt.Graphics;
import java.awt.Toolkit;

import entities.Player;

public class Game implements Runnable {

	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;

	private Player player;

	public Game() {

		initClasses();
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.requestFocus();
		
		startGameLoop(); // Alaways last line of the constructor
	}

	private void initClasses() {
		player = new Player(200, 200);
	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	private void update() {
		player.update();
	}

	public void render(Graphics g) {
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
