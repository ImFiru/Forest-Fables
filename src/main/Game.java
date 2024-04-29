package main;

import java.awt.Toolkit;

public class Game implements Runnable {

	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;

	public Game() {

		gamePanel = new GamePanel();
		gameWindow = new GameWindow(gamePanel);
		gamePanel.requestFocus();
		startGameLoop();

	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	private void update() {
		gamePanel.updateGame();
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

}
