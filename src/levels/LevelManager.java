package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.LoadSave;
import static utilz.LoadSave.LEVEL_ATLAS;
import static main.Game.*;


public class LevelManager {

    private Game game;
    private BufferedImage[] levelSprite;
    private Level levelOne;
    
    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprites();
        levelOne = new Level(LoadSave.getLevelData());
    }

    private void importOutsideSprites() {
        BufferedImage image = LoadSave.getSpriteAtlas(LEVEL_ATLAS);
        levelSprite = new BufferedImage[198];
        for(int j = 0; j < 11; j++) {
            for(int i = 0; i < 18; i++) {
                int index = j * 18 + i;
                levelSprite[index] = image.getSubimage(i * 24, j * 24, 24, 24);
            }
        }
    }

    public void draw(Graphics g) {
        for (int j = 0; j < TILES_IN_HEIGHT; j++) {
            for (int i = 0; i < TILES_IN_WIDTH; i++) {
                int index = levelOne.getSpriteIndex(i, j);
                    g.drawImage(levelSprite[index], i * TILES_SIZE, j * TILES_SIZE, TILES_SIZE, TILES_SIZE, null);
            }
        }
    }

    public void update() {
        
    }

    public Level getCurrentLevel() {
        return levelOne;
    }
}
