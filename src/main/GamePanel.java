package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

public class GamePanel extends JPanel {

	private MouseInputs mouseInputs;
	private float xDelta = 100, yDelta = 100;
	private BufferedImage image, subImage;

	public GamePanel() {
		mouseInputs = new MouseInputs(this);
		importImage();
		setPanelSize();
		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);

	}

	private void importImage() {
		InputStream is = getClass().getResourceAsStream("/entities/player/player_animations.png");
		try {
			image = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setPanelSize() {
		Dimension size = new Dimension(960, 600);
		setPreferredSize(size);
	}

	public void changeXDelta(int value) {
		this.xDelta += value;

	}

	public void changeYDelta(int value) {
		this.yDelta += value;

	}

	public void setRectPos(int x, int y) {
		this.xDelta = x;
		this.yDelta = y;
	}


	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		subImage = image.getSubimage(0, 0, 56, 56);
		g.drawImage(subImage, (int) xDelta, (int) yDelta, 112, 112, null);
	}
}
