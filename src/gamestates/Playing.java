package gamestates;

import entities.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import main.Game;
import managers.EnemyManager;
import managers.LevelManager;
import overlays.GameOverOverlay;
import overlays.LevelCompletedOverlay;
import overlays.PauseOverlay;
import utilz.LoadSave;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

public class Playing extends State implements StateMethods {

  private Player player;
  private LevelManager levelManager;
  private EnemyManager enemyManager;
  private PauseOverlay pauseOverlay;
  private GameOverOverlay gameOverOverlay;
  private LevelCompletedOverlay levelCompletedOverlay;
  private boolean paused = false;

  private int xLevelOffset;
  private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
  private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
  private int maxLevelOffsetX;

  private boolean isSpacebarPressed = false;

  private BufferedImage backgroundImage, backgroundImage2, backgroundImage3;
  private boolean gameOver;
  private boolean levelCompleted;

  public Playing(Game game) {
    super(game);
    initClasses();
    backgroundImage = LoadSave.getSpriteAtlas(LoadSave.LEVEL_BACKGROUND_IMAGE_1);
    backgroundImage2 = LoadSave.getSpriteAtlas(LoadSave.LEVEL_BACKGROUND_IMAGE_2);
    backgroundImage3 = LoadSave.getSpriteAtlas(LoadSave.LEVEL_BACKGROUND_IMAGE_3);

    calcLevelOffset();
    loadStartLevel();

  }

  public void loadNexLevel() {
    resetAll();
    levelManager.loadNextLevel();
    player.setSpawnPoint(levelManager.getCurrentLevel().getPlayerSpawn());

  }

  private void loadStartLevel() {
    enemyManager.loadEnemies(levelManager.getCurrentLevel());
  }

  private void calcLevelOffset() {
    maxLevelOffsetX = levelManager.getCurrentLevel().getLevelOfsetX();
  }

  private void initClasses() {
    levelManager = new LevelManager(game);
    enemyManager = new EnemyManager(this);
    player = new Player(200, 200, (int) (56 * Game.SCALE), (int) (56 * Game.SCALE), this);
    player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
    player.setSpawnPoint(levelManager.getCurrentLevel().getPlayerSpawn());
    pauseOverlay = new PauseOverlay(this);
    gameOverOverlay = new GameOverOverlay(this);
    levelCompletedOverlay = new LevelCompletedOverlay(this);
  }

  @Override
  public void update() {
    if (paused) {
      pauseOverlay.update();
    } else if (levelCompleted) {
      levelCompletedOverlay.update();
    } else if (!gameOver) {
      levelManager.update();
      player.update();
      enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
      checkCloseToBorder();
    }
  }

  private void checkCloseToBorder() {
    int playerX = (int) player.getHitbox().x;
    int diference = playerX - xLevelOffset;

    if (diference > rightBorder) {
      xLevelOffset += (diference - rightBorder);
    } else if (diference < leftBorder) {
      xLevelOffset += (diference - leftBorder);
    }

    if (xLevelOffset > maxLevelOffsetX) {
      xLevelOffset = maxLevelOffsetX;
    } else if (xLevelOffset < 0) {
      xLevelOffset = 0;
    }
  }

  @Override
  public void draw(Graphics g) {

    drawLayers(g);
    levelManager.draw(g, xLevelOffset);
    player.render(g, xLevelOffset);
    enemyManager.draw(g, xLevelOffset);
    if (paused) {
      g.setColor(new Color(0, 0, 0, 200));
      g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
      pauseOverlay.draw(g);
    } else if (gameOver) {
      gameOverOverlay.draw(g);
    } else if (levelCompleted) {
      levelCompletedOverlay.draw(g);
    }

  }

  private void drawLayers(Graphics g) {
    g.drawImage(backgroundImage, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
    for (int i = 0; i < 3; i++) {
      g.drawImage(backgroundImage2, i * GAME_WIDTH - (int) (xLevelOffset * 0.3), 0, GAME_WIDTH, GAME_HEIGHT, null);
      g.drawImage(backgroundImage3, i * GAME_WIDTH - (int) (xLevelOffset * 0.6), 0, GAME_WIDTH, GAME_HEIGHT, null);
    }
  }

  public void resetAll() {
    gameOver = false;
    paused = false;
    levelCompleted = false;
    player.resetAll();
    enemyManager.resetAllEnemies();

  }

  public void setGameover(boolean gameover) {
    this.gameOver = gameover;

  }

  public void checkEnemyHit(Rectangle2D.Float attackBox) {
    enemyManager.checkEnemyHit(attackBox);
  }

  public void mouseDragged(MouseEvent e) {
    if (!gameOver) {
      if (paused) {
        pauseOverlay.mouseDragged(e);
      }
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (!gameOver) {
      if (e.getButton() == MouseEvent.BUTTON1) {
        player.setAttacking(true);
      }
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    if (!gameOver) {
      if (paused) {
        pauseOverlay.mousePressed(e);
      } else if (levelCompleted) {
        levelCompletedOverlay.mousePressed(e);
      }
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    if (!gameOver) {
      if (paused) {
        pauseOverlay.mouseReleased(e);
      } else if (levelCompleted) {
        levelCompletedOverlay.mouseReleased(e);
      }
    }
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    if (!gameOver) {
      if (paused) {
        pauseOverlay.mouseMoved(e);
      } else if (levelCompleted) {
        levelCompletedOverlay.mouseMoved(e);
      }
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    int keyCode = e.getKeyCode();

    if (gameOver) {
      gameOverOverlay.keyPressed(e);
    } else {

      if (e.isShiftDown()) {
        player.setRunning(true);
      }

      switch (keyCode) {
        case KeyEvent.VK_A:
          player.setLeft(true);
          break;
        case KeyEvent.VK_D:
          player.setRight(true);
          break;
        case KeyEvent.VK_SPACE:
          if (!isSpacebarPressed) {
            player.setJump(true);
            isSpacebarPressed = true;
          }
          break;
        case KeyEvent.VK_ESCAPE:
          paused = !paused;
          break;

      }
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    int keyCode = e.getKeyCode();
    if (!gameOver) {
      if (!e.isShiftDown()) {
        player.setRunning(false);
      }

      switch (keyCode) {
        case KeyEvent.VK_A:
          player.setLeft(false);
          break;
        case KeyEvent.VK_D:
          player.setRight(false);
          break;
        case KeyEvent.VK_SPACE:
          isSpacebarPressed = false;
          break;
      }
    }
  }

  public void setLevelCompleted(boolean levelCompleted) {
    this.levelCompleted = levelCompleted;
  }

  public void setMaxLevelOffset(int xLevelOffset) {
    this.xLevelOffset = xLevelOffset;
  }

  public void unPauseGame() {
    paused = false;
  }

  public void windowFocusLost() {
    player.resetDirectionBooleans();
    isSpacebarPressed = false;
  }

  public Player getPlayer() {
    return player;
  }

  public EnemyManager getEnemyManager() {
    return enemyManager;
  }
}
