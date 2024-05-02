package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.LoadSave;


import static utilz.Constants.PlayerConstants.*;
import static utilz.LoadSave.*;
import static utilz.HelpMethods.*;

public class Player extends Entity {

    private BufferedImage[][] playerAnimations;
    private int animationIndex, animationTick, animationSpeed = 15;

	private int playerAction = IDLE;
	private boolean moving = false, attacking = false;
    private boolean up, down, left, right, jump;
    private float playerSpeed = 0.5f * Game.SCALE;
    private float airSpeed = 0f;
    private float gravity = 0.045f * Game.SCALE;
    private float jumpSpeed = -2.55f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.1f * Game.SCALE;
    private boolean inAir = false;

    private int[][] levelData;

    private float xDrawOffset = 18 * Game.SCALE, yDrawOffset = 24 * Game.SCALE;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();

        initHitbox(x, y, (int)(18 * Game.SCALE), (int)(32 * Game.SCALE));
    }

    public void update() {
		updatePosition();
        updateAnimationTick();
		setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage(playerAnimations[playerAction][animationIndex], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), width, height, null);
        drawHitbox(g);

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
        if (inAir){
            if (airSpeed < 0) {
                playerAction = JUMPING;
            } else {
                playerAction = FALLING;
            }
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

        if (jump) {
            jump();
        }
		if (!left && !right && !inAir) {
			return;
        }

		float xSpeed = 0;

		if (left) {
			xSpeed  -= playerSpeed;
        }
		if (right) {
            xSpeed += playerSpeed;
        }

        if (!inAir) {
            if (!isEntityOnFloor(hitbox, levelData)) {
                inAir = true;
            }
        }

        if (inAir) {
            if (canMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelData)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPosition(xSpeed);
            } else {
                hitbox.y = getEntityYPositionUnderRoofOrAboveFloor(hitbox, airSpeed);
                if (airSpeed > 0){
                    resetInAir();
                }
                else{
                   airSpeed = fallSpeedAfterCollision;
                }
                updateXPosition(xSpeed);;
            }

        }else {
            updateXPosition(xSpeed);
        }
        moving = true;
	}

    private void jump() {
        if (inAir) {
            return;
        }
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPosition(float xSpeed) {
        if (canMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData)) {
			hitbox.x += xSpeed;
		} else {
            hitbox.x = getEntityXPositionNextToWall(hitbox, xSpeed, levelData);
        }
    }

    private void loadAnimations() {
        BufferedImage image = LoadSave.getSpriteAtlas(PLAYER_ATLAS);
        playerAnimations = new BufferedImage[15][12];
        
        for (int j = 0; j < playerAnimations.length; j++) {
            for (int i = 0; i < playerAnimations[j].length; i++) {
                playerAnimations[j][i] = image.getSubimage(i * 56, j * 56, 56, 56);
            }
        }
	}
    
    public void loadLevelData(int[][] levelData) {
        this.levelData = levelData;
        if (!isEntityOnFloor(hitbox, levelData)){
            inAir = true;
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

    public void setJump(boolean jump) {
        this.jump = jump;
    }
}
