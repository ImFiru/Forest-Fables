package managers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.GameState;
import levels.Level;
import main.Game;
import utilz.LoadSave;
import static utilz.LoadSave.LEVEL_ATLAS;
import static main.Game.*;

public class LevelManager {

    private Game game;
    private BufferedImage[] levelSprite;
    private ArrayList<Level> levels;
    private int levelIndex = 0;

    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprites();
        levels = new ArrayList<>();
        buildAllLevels();
    }

    public void loadNextLevel() {
        levelIndex++;
        if (levelIndex >= levels.size()) {
            // TODO: Game Completed Overlay
            levelIndex = 0;
            System.out.println("Game Completed");
            GameState.gamestate = GameState.MENU;
        }

        Level nextLevel = levels.get(levelIndex);
        game.getPlaying().getEnemyManager().loadEnemies(nextLevel);
        game.getPlaying().getPlayer().loadLevelData(nextLevel.getLevelData());
        game.getPlaying().setMaxLevelOffset(nextLevel.getLevelOfsetX());
    }

    private void buildAllLevels() {
        BufferedImage[] allLevels = LoadSave.getAllLevels();
        for (BufferedImage image : allLevels) {
            levels.add(new Level(image));
        }
    }

    private void importOutsideSprites() {
        BufferedImage image = LoadSave.getSpriteAtlas(LEVEL_ATLAS);
        levelSprite = new BufferedImage[198];
        for (int j = 0; j < 11; j++) {
            for (int i = 0; i < 18; i++) {
                int index = j * 18 + i;
                levelSprite[index] = image.getSubimage(i * 24, j * 24, 24, 24);
            }
        }
    }

    public void draw(Graphics g, int xLevelOffset) {
        for (int j = 0; j < TILES_IN_HEIGHT; j++) {
            for (int i = 0; i < levels.get(levelIndex).getLevelData()[0].length; i++) {
                int index = levels.get(levelIndex).getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], i * TILES_SIZE - xLevelOffset, j * TILES_SIZE, TILES_SIZE, TILES_SIZE,
                        null);
            }
        }
    }

    public void update() {

    }

    public Level getCurrentLevel() {
        return levels.get(levelIndex);
    }

    public int getAmmountOfLevels() {
        return levels.size();
    }
}
