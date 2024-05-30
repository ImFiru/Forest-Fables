package entities;
// Java imports
import java.awt.geom.Rectangle2D;

// Own imports
import main.Game;

import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.Directions.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.*;

public abstract class Enemy extends Entity {
    // Enemy Mushroom
    Mushroom mushroom;
    protected boolean attackChecked;
    protected int enemyType;
    protected int animationSpeed = 25;
    protected boolean firstUpdate = true;

    // Movement
    protected int walkingDirection = LEFT;
    protected int tileY;
    protected float attackRange = Game.TILES_SIZE;

    // Health
    protected boolean active = true;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        this.maxHealth = getEnemyMaxHealth(enemyType);
        this.currentHealth = maxHealth;
    }

    protected void firstUpdateCheck(int[][] levelData) {
        if (!isEntityOnFloor(hitbox, levelData)) {
            inAir = true;
        }
        firstUpdate = false;
    }

    protected void updateInAir(int[][] levelData, int enemyType) {
        if (canMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelData)) {
            hitbox.y += airSpeed;
            airSpeed += GRAVITY;
        } else {
            inAir = false;
            hitbox.y = getEntityYPositionUnderRoofOrAboveFloor(hitbox, airSpeed, enemyType);
            tileY = (int) (hitbox.y / Game.TILES_SIZE);
        }
    }

    protected void updateWalking(int[][] levelData, int enemyType) {
        float xSpeed = 0f;
        if (walkingDirection == LEFT) {
            xSpeed = -entitySpeed;
        } else {
            xSpeed = entitySpeed;
        }
        if (canMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData)) {
            if (isFloor(hitbox, xSpeed, levelData)) {
                hitbox.x += xSpeed;
                return;
            }

        }
        changeWalkingDirection();
    }

    protected void turnTowardPlayer(Player player) {
        if (player.hitbox.x > hitbox.x) {
            walkingDirection = RIGHT;
        } else {
            walkingDirection = LEFT;
        }
    }

    protected boolean canSeePlayer(int[][] levelData, Player player) {
        int playerTileY = (int) (player.getHitbox().y / Game.TILES_SIZE);
        if (playerTileY == tileY) {
            if (isPlayerInRange(player)) {
                if (isSightClear(levelData, hitbox, player.hitbox, tileY)){
                    return true;
                } 
            }
        }
        return false;
    }

    protected boolean isPlayerInRange(Player player){
        int absValue = Math.abs( (int) (player.hitbox.x - hitbox.x));
        return absValue <= attackRange * 5;
    }

    protected boolean isPlayerCloseForAttack(Player player) {
        int absValue = Math.abs( (int) (player.hitbox.x - hitbox.x));
        return absValue <= attackRange;
    }

    protected void newState(int enemyState) {
        if (enemyState == DEAD) {
            this.state = enemyState;
        } else{
        this.state = enemyState;
        animationTick = 0;
        animationIndex = 0;
    }
    }

    public void hurt(int damage) {
        currentHealth -= damage;
        if (currentHealth <= 0) {
            newState(DEAD);
        } else {
            newState(DAMAGE);
        }
    }

    protected void checkPlayerHit(Rectangle2D.Float attackBox, Player player) {
        if (attackBox.intersects(player.hitbox)) {
            player.changeHealth(- getEnemyDamage(enemyType));
        }
        attackChecked = true;
    }

    protected void updateAnimationTick() {
        animationTick++;
        if (animationTick > animationSpeed) {
            animationTick = 0;
            animationIndex++;
        }
        if (animationIndex > getSpriteAmount(enemyType, state)) {
            animationIndex = 0;

            switch (state) {
                case ATTACKING,DAMAGE -> state = IDLE;
                case DEAD -> active = false;
            }
        }
    }

    protected void changeWalkingDirection() {
        if (walkingDirection == LEFT) {
            walkingDirection = RIGHT;
        } else {
            walkingDirection = LEFT;
        }
    }

    public void resetEnemy() {
        hitbox.x = x;
        hitbox.y = y - 20;
        firstUpdate = true;
        currentHealth = maxHealth;
        newState(IDLE);
        active = true;
        airSpeed = 0f;
    }

    public boolean isActive() {
        return active;
    }
}
