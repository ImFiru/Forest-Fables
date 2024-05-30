package entities;

import java.awt.geom.Rectangle2D;

import main.Game;

import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.Entities.*;
import static utilz.Constants.Directions.*;

public class Mushroom extends Enemy {

    private int attackBoxXOffset;

    private int flipX = 0;
    private int flipW = 1;

    public Mushroom(float x, float y) {
        super(x, y - 20, MUSHROOM_WIDTH, MUSHROOM_HEIGHT, MUSHROOM);
        initHitbox(30, 33);
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (20 * Game.SCALE), (int) (33 * Game.SCALE));
        attackBoxXOffset = (int) (5 * Game.SCALE);
    }

    public void update(int[][] levelData, Player player) {
        updateBehavior(levelData, player);
        updateAnimationTick();
        updateAttackBox();
        updateFlip();
    }

    private void updateFlip() {
        if (walkingDirection == RIGHT) {
            flipX = width;
            flipW = -1;
        } else {
            flipX = 0;
            flipW = 1;
        }
    }

    private void updateAttackBox() {
        if (walkingDirection == RIGHT) {
            attackBox.x = hitbox.x + hitbox.width + (int) (attackBoxXOffset);
        } else if (walkingDirection == LEFT) {
            attackBox.x = hitbox.x - attackBox.width - (int) (attackBoxXOffset);
        }
        attackBox.y = hitbox.y;
    }

    private void updateBehavior(int[][] levelData, Player player) {
        if (firstUpdate) {
            firstUpdateCheck(levelData);
        }
        if (inAir) {
            updateInAir(levelData, ENEMY_MUSHROOM);
        } else {
            switch (state) {
                case IDLE:
                    newState(RUNNING);
                    break;

                case RUNNING:
                    if (canSeePlayer(levelData, player)) {
                        turnTowardPlayer(player);
                        if (isPlayerCloseForAttack(player)) {
                            newState(ATTACKING);
                        }
                    }

                    updateWalking(levelData, ENEMY_MUSHROOM);
                    break;

                case ATTACKING:
                    if (animationIndex == 0) {
                        attackChecked = false;
                    }
                    if (animationIndex == 5 && !attackChecked) {
                        checkPlayerHit(attackBox, player);
                    }
                    break;
                case DAMAGE:
                    break;
            }
        }
    }

    public int getFlipX() {
        return flipX;
    }

    public int getFlipW() {
        return flipW;
    }

}
