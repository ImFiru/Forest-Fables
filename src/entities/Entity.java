package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

public abstract class Entity {

    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;
    protected int animationIndex, animationTick;
    protected int state;
    protected float airSpeed = 0f;
    protected boolean inAir;
    protected int maxHealth;
    protected int currentHealth;

    protected Rectangle2D.Float attackBox;

    protected float entitySpeed = 0.5f * Game.SCALE;

    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void drawHitbox(Graphics g, int xLevelOffset) {
        // For debugging the hitbox
        g.setColor(Color.RED);
        g.drawRect((int) hitbox.x - xLevelOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    protected void drawAttackBox(Graphics g, int xLevelOffset) {
        g.setColor(Color.GREEN);
        g.drawRect((int) (attackBox.x - xLevelOffset), (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    protected void initHitbox(int width, int height) {
        hitbox = new Rectangle2D.Float(x, y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public int getState() {
        return state;
    }

    public int getAnimationIndex() {
        return animationIndex;
    }

}
