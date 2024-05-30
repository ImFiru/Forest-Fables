package entities;

// Java imports
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.Color;

// Custom imports
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Entities.*;
import static utilz.LoadSave.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.*;

public class Player extends Entity {

    private BufferedImage[][] playerAnimations;
    private int animationIndex, animationTick, animationSpeed = 15;
    private boolean moving = false, attacking = false, running = false;

    private boolean left, right, jump;
    private float jumpSpeed = -2f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.1f * Game.SCALE;

    // StatusBarUI
    private BufferedImage statusBarImage;

    private int statusBarWidth = (int) (308 * Game.SCALE / 2);
    private int statusBarHeight = (int) (64 * Game.SCALE / 2);
    private int statusBarX = (int) (15 * Game.SCALE);
    private int statusBarY = (int) (15 * Game.SCALE);

    private int healthBarWidth = (int) (180 * Game.SCALE / 2);
    private int healthBarHeight = (int) (8 * Game.SCALE / 2);
    private int healthBarXStart = (int) (80 * Game.SCALE / 2);
    private int healthBarYStart = (int) (4 * Game.SCALE / 2);

    private int healthWidth = healthBarWidth;

    private int flipX = 0;
    private int flipW = 1;

    private int[][] levelData;

    private float xDrawOffset = 18 * Game.SCALE, yDrawOffset = 24 * Game.SCALE;

    private boolean attackChecked;
    private Playing playing;

    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        this.state = IDLE;
        this.maxHealth = 100;
        this.currentHealth = maxHealth;
        loadAnimations();
        initHitbox(18, 32);
        initAtackBox();
    }

    private void initAtackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (18 * Game.SCALE), (int) (20 * Game.SCALE));
    }

    public void update() {
        updateHealthBar();
        if (currentHealth <= 0) {
            playing.setGameover(true);
            return;
        }
        updateAttackBox();
        updatePosition();
        if (attacking) {
            checkAttack();
        }
        updateAnimationTick();
        setAnimation();
    }

    private void checkAttack() {
        if (attackChecked || animationIndex != 3) {
            return;
        }
        attackChecked = true;
        playing.checkEnemyHit(attackBox);
    }

    private void updateAttackBox() {
        if (right) {
            attackBox.x = hitbox.x + hitbox.width + (int) (Game.SCALE);
        } else if (left) {
            attackBox.x = hitbox.x - hitbox.width - (int) (Game.SCALE);
        }
        attackBox.y = hitbox.y + (int) (10 * Game.SCALE);
        ;
    }

    private void updateHealthBar() {
        healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
    }

    public void render(Graphics g, int xLevelOffset) {
        g.drawImage(playerAnimations[state][animationIndex], (int) (hitbox.x - xDrawOffset + flipX) - xLevelOffset,
                (int) (hitbox.y - yDrawOffset), width * flipW, height, null);
        // drawHitbox(g, xLevelOffset);
        // drawAttackBox(g, xLevelOffset);
        drawUI(g);

    }

    public void setSpawnPoint(Point spawnPoint) {
        System.out.println(spawnPoint);
        this.x = spawnPoint.x;
        this.y = spawnPoint.y;
        System.out.println(x + " " + y);
        hitbox.x = x;
        hitbox.y = y;
    }

    private void drawUI(Graphics g) {
        g.drawImage(statusBarImage, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        g.setColor(new Color(172, 63, 60));
        g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
    }

    private void updateAnimationTick() {
        animationTick++;

        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
        }

        if (animationIndex >= getSpriteAmount(state)) {
            animationIndex = 0;
            attacking = false;
            attackChecked = false;
        }
    }

    private void setAnimation() {
        int startAnimation = state;

        if (moving) {
            if (running) {
                state = RUNNING;
            } else {
                state = WALKING;
            }
        } else {
            state = IDLE;
        }

        if (inAir) {
            if (airSpeed < 0) {
                state = JUMPING;
            } else {
                state = FALLING;
            }
        }

        if (attacking) {
            state = ATTACKING;
        }

        if (startAnimation != state) {
            resetAnimationTick();
        }
    }

    private void resetAnimationTick() {
        animationTick = 0;
        animationIndex = 0;
    }

    private void updatePosition() {
        moving = false;
        float xSpeed = 0;

        if (running) {
            entitySpeed = 0.9f * Game.SCALE;
        } else {
            entitySpeed = 0.5f * Game.SCALE;
        }

        if (attacking) {
            entitySpeed = 0f * Game.SCALE;
        }

        if (jump) {
            jump();
        }

        if (!inAir) {
            if ((!left && !right) || (right && left)) {
                return;
            }
        }

        if (left) {
            xSpeed -= entitySpeed;
            flipX = width;
            flipW = -1;
        }

        if (right) {
            xSpeed += entitySpeed;
            flipX = 0;
            flipW = 1;
        }

        if (!inAir) {
            if (!isEntityOnFloor(hitbox, levelData)) {
                inAir = true;
            }
        }

        if (inAir) {
            if (canMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelData)) {
                hitbox.y += airSpeed;
                airSpeed += GRAVITY;
                updateXPosition(xSpeed);
            } else {
                hitbox.y = getEntityYPositionUnderRoofOrAboveFloor(hitbox, airSpeed, PLAYER);
                if (airSpeed > 0) {
                    resetInAir();
                } else {
                    airSpeed = fallSpeedAfterCollision;
                }
                updateXPosition(xSpeed);
                ;
            }
        } else {
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
        jump = false;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
        jump = false;
    }

    private void updateXPosition(float xSpeed) {
        if (canMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = getEntityXPositionNextToWall(hitbox, xSpeed, levelData);
        }
    }

    public void changeHealth(int change) {
        currentHealth += change;
        if (currentHealth <= 0) {
            currentHealth = 0;
            // gameOver();
        } else if (currentHealth >= maxHealth) {
            currentHealth = maxHealth;
        }
    }

    private void loadAnimations() {
        BufferedImage image = LoadSave.getSpriteAtlas(PLAYER_ATLAS);
        playerAnimations = new BufferedImage[16][12];

        for (int j = 0; j < playerAnimations.length; j++) {
            for (int i = 0; i < playerAnimations[j].length; i++) {
                playerAnimations[j][i] = image.getSubimage(i * 56, j * 56, 56, 56);
            }
        }

        statusBarImage = LoadSave.getSpriteAtlas(STATUS_BAR);
    }

    public void loadLevelData(int[][] levelData) {
        this.levelData = levelData;
        if (!isEntityOnFloor(hitbox, levelData)) {
            inAir = true;
        }
    }

    public void resetAll() {
        resetDirectionBooleans();
        inAir = false;
        attacking = false;
        moving = false;
        state = IDLE;
        currentHealth = maxHealth;

        hitbox.x = x;
        hitbox.y = y;

        if (!isEntityOnFloor(hitbox, levelData)) {
            inAir = true;
        }
    }

    public void resetDirectionBooleans() {
        left = false;
        right = false;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
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

    public void setRunning(boolean running) {
        this.running = running;
    }
}
