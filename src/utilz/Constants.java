package utilz;

import main.Game;

public class Constants {

  public static class UI{

    public static class Buttons{

      public static final int BUTTON_WIDTH_DEFAULT = 160;
      public static final int BUTTON_HEIGHT_DEFAULT = 80;
      public static final int BUTTON_WIDTH = (int) (BUTTON_WIDTH_DEFAULT * Game.SCALE) / 2;
      public static final int BUTTON_HEIGHT = (int) (BUTTON_HEIGHT_DEFAULT * Game.SCALE) / 2;
    }
  }

  public static class Directions {

    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
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
        default:
          return 1;
      }
    }
  }
}
