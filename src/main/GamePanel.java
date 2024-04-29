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

import static utilz.Constants.Directions.*;
import static utilz.Constants.PlayerConstants.*;

public class GamePanel extends JPanel {

	private MouseInputs mouseInputs;
	private float xDelta = 100, yDelta = 100;

	private BufferedImage image;
	private BufferedImage[][] playerAnimations;

	private int animationIndex, animationTick, animationSpeed = 15;

	private int playerAction = IDLE;
	private int playerDirecion = -1;
	private boolean moving = false;


	public GamePanel() {
		mouseInputs = new MouseInputs(this);
		importImage();
		loadAnimations();
		setPanelSize();
		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);

	}

	private void loadAnimations() {
		playerAnimations = new BufferedImage[15][12];

		for (int j = 0; j < playerAnimations.length; j++) {
            for (int i = 0; i < playerAnimations[j].length; i++) {
                playerAnimations[j][i] = image.getSubimage(i * 56, j * 56, 56, 56);
            }
        }
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

	public void setDirection(int direction) {
		this.playerDirecion = direction;
		moving = true;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	private void updateAnimationTick() {
		animationTick++;

		if (animationTick >= animationSpeed) {
			animationTick = 0;
			animationIndex++;
		}
		if (animationIndex >= getSpriteAmount(playerAction)) {
			animationIndex = 0;
		}
	}

	private void setAnimation() {
		if (moving) {
			playerAction = WALKING;
		} else {
			playerAction = IDLE;
		}
	}

	private void updatePosition() {
		if (moving){
			switch (playerDirecion) {
				case UP:
					yDelta -= 2;
					break;
				case DOWN:
					yDelta += 2;
					break;
				case LEFT:
					xDelta -= 2;
					break;
				case RIGHT:
					xDelta += 2;
				break;
				default:
					break;
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		updateAnimationTick();
		setAnimation();
		updatePosition();
		g.drawImage(playerAnimations[playerAction][animationIndex], (int) xDelta, (int) yDelta, 112, 112, null);
	}
}
