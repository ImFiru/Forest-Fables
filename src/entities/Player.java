package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

import static utilz.Constants.Directions.*;
import static utilz.Constants.PlayerConstants.*;

public class Player extends Entity {

    private BufferedImage[][] playerAnimations;
    private int animationIndex, animationTick, animationSpeed = 15;

	private int playerAction = IDLE;
	private boolean moving = false, attacking = false;
    private boolean up, down, left, right;
    private float playerSpeed = 1.0f;

    public Player(float x, float y) {
        super(x, y);
        loadAnimations();
    }

    public void update() {
		updatePosition();
        updateAnimationTick();
		setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage(playerAnimations[playerAction][animationIndex], (int) x, (int) y, 112, 112, null);

    }

	private void updateAnimationTick() {
		animationTick++;

		if (animationTick >= animationSpeed) {
			animationTick = 0;
			animationIndex++;
		}
		if (animationIndex >= getSpriteAmount(playerAction)) {
			animationIndex = 0;
            attacking = false;
		}
	}

	private void setAnimation() {
        int startAnimation = playerAction;

		if (moving) {
			playerAction = WALKING;
		} else {
			playerAction = IDLE;
		}

        if (attacking) {
            playerAction = ATTACKING;
        }

        if (startAnimation != playerAction) {
            resetAnimationTick();
        }
	}

	private void resetAnimationTick() {
        animationTick = 0;
        animationIndex = 0;
    }

    private void updatePosition() {
        moving = false;
		if (left && !right) {
            x -= playerSpeed;
            moving = true;
        } else if (right && !left) {
            x += playerSpeed;
            moving = true;
        }

        if (up && !down) {
            y -= playerSpeed;
            moving = true;
        } else if (down && !up) {
            y += playerSpeed;
            moving = true;
        }
	}

    private void loadAnimations() {

        InputStream is = getClass().getResourceAsStream("/entities/player/player_animations.png");
		try {
			BufferedImage image = ImageIO.read(is);
            playerAnimations = new BufferedImage[15][12];

		    for (int j = 0; j < playerAnimations.length; j++) {
                for (int i = 0; i < playerAnimations[j].length; i++) {
                    playerAnimations[j][i] = image.getSubimage(i * 56, j * 56, 56, 56);
                }
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public void resetDirectionBooleans() {
        up = false;
        down = false;
        left = false;
        right = false;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }
}
