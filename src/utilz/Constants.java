package utilz;

import main.Game;

public class Constants {

  public static final float GRAVITY = 0.035f * Game.SCALE;
  
  public static class UI{

    public static class Buttons {
			public static final int BUTTON_WIDTH_DEFAULT = 64;
			public static final int BUTTON_HEIGHT_DEFAULT = 16;
			public static final int BUTTON_WIDTH = (int) (2 * BUTTON_WIDTH_DEFAULT * Game.SCALE);
			public static final int BUTTON_HEIGHT = (int) (2 * BUTTON_HEIGHT_DEFAULT * Game.SCALE);
		}

    public static class VolumeButtons {
      public static final int VOLUME_WIDTH_DEFAULT = 5;
      public static final int VOLUME_HEIGHT_DEFAULT = 16;
      public static final int SLIDER_WIDTH_DEFAULT = 62;
      public static final int SLIDER_HEIGHT_DEFAULT = 16;
      public static final int VOLUME_WIDTH = (int) (2 * VOLUME_WIDTH_DEFAULT * Game.SCALE);
      public static final int VOLUME_HEIGHT = (int) (2 * VOLUME_HEIGHT_DEFAULT * Game.SCALE);
      public static final int SLIDER_WIDTH = (int) (2 * SLIDER_WIDTH_DEFAULT * Game.SCALE);
      public static final int SLIDER_HEIGHT = (int) (2 * SLIDER_HEIGHT_DEFAULT * Game.SCALE);
    }

  }

  public static class Directions {

    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
  }

  public static class Entities {
    public static final int PLAYER = 0;
    public static final int PLAYERCAT = 1;
    public static final int ENEMY_MUSHROOM = 2;
  }

  public static class PlayerConstants {

    public static final int IDLE = 0;
    public static final int WALKING = 1;
    public static final int RUNNING = 2;
    public static final int ATTACKING = 3;
    public static final int COMBO = 4;
    public static final int CRITICAL_ATTACK = 5;
    public static final int POWERUP = 6;
    public static final int BLOCKING = 7;
    public static final int JUMPING = 8;
    public static final int FALLING = 9;
    public static final int CROUCHING = 10;
    public static final int SLIDING = 11;
    public static final int DAMAGE = 12;
    public static final int DEAD = 13;
    public static final int WALL_SLIDING = 14;
    public static final int JUMP_ATTACKING = 15;

    public static int getSpriteAmount(int player_action) {
      switch (player_action) {
        case IDLE:
        case ATTACKING:
          return 6;
        case COMBO:
          return 2;
        case RUNNING:
        case JUMPING:
        case FALLING:
        case POWERUP:
        case SLIDING:
        case CRITICAL_ATTACK:
          return 8;
        case DAMAGE:
        case WALL_SLIDING:
          return 4;
        case DEAD:
          return 12;
        case CROUCHING:
        case BLOCKING:
          return 3;
        case WALKING:
          return 10;
        case JUMP_ATTACKING:
          return 5;
        default:
          return 1;
      }
    }
  }

  public static class PlayerCatConstants {

    public static final int IDLE = 0;
    public static final int WALKING = 1;
    public static final int RUNNING = 2;
    public static final int ATTACKING = 3;
    public static final int JUMPING = 4;
    public static final int FALLING = 5;
    public static final int DAMAGE = 6;
    public static final int DEAD = 7;

    public static int getSpriteAmount(int player_action) {
      switch (player_action) {
        case IDLE:
        case WALKING:
        case DEAD:
          return 8;
        case RUNNING:
        case JUMPING:
        case DAMAGE:
          return 4;
        case FALLING:
          return 3;
        case ATTACKING:
          return 7;
        default:
          return 1;
      }
    }
  }

  public static class EnemyConstants {
    public static final int MUSHROOM = 1;

    public static final int IDLE = 0;
    public static final int RUNNING = 1;
    public static final int ATTACKING = 2;
    public static final int DAMAGE = 3;
    public static final int DEAD = 4;

    public static final int MUSHROOM_WIDTH_DEFAULT = 80;
    public static final int MUSHROOM_HEIGHT_DEFAULT = 64;
    public static final int MUSHROOM_WIDTH = (int) (MUSHROOM_WIDTH_DEFAULT * Game.SCALE);
    public static final int MUSHROOM_HEIGHT = (int) (MUSHROOM_HEIGHT_DEFAULT * Game.SCALE);

    public static final int MUSHROOM_DRAWOFFSET_X = (int) (25 * Game.SCALE);
    public static final int MUSHROOM_DRAWOFFSET_Y = (int) (31 * Game.SCALE);

    public static int getSpriteAmount(int enemy_type, int enemy_state) {
      switch (enemy_type) {
        case MUSHROOM: {
          switch (enemy_state) {
            case IDLE:
              return 6;
            case RUNNING:
              return 7;
            case ATTACKING:
              return 9;
            case DAMAGE:
              return 4;
            case DEAD:
              return 10;
          }
        }
        default:
          return 1;
      }
    }

    public static int getEnemyMaxHealth(int enemy_type) {
      switch (enemy_type) {
        case MUSHROOM:
          return 10;
        default:
          return 1;
      }
    }

    public static int getEnemyDamage(int enemy_type) {
      switch (enemy_type) {
        case MUSHROOM:
          return 100;
        default:
          return 0;
      }
    }

  }
}

