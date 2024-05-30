package managers;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Mushroom;
import entities.Player;
import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;
import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[][] MushroomArray;
    private ArrayList<Mushroom> mushrooms = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImages();
    }

    public void loadEnemies(Level level) {
        mushrooms = level.getMushrooms();
        System.out.println("Mushrooms loaded: " + mushrooms.size());
    }

    public void update(int[][] levelData, Player player) {
        boolean isAnyActive = false;
        for (Mushroom mushroom : mushrooms) {
            if (mushroom.isActive()) {
                mushroom.update(levelData, player);
                isAnyActive = true;
            }

        }
        if (!isAnyActive) {
            playing.setLevelCompleted(true);
        }
    }

    public void draw(Graphics g, int xLevelOffset) {
        drawMushrooms(g, xLevelOffset);
    }

    private void drawMushrooms(Graphics g, int xLevelOffset) {
        for (Mushroom mushroom : mushrooms) {
            if (mushroom.isActive()) {
                g.drawImage(MushroomArray[mushroom.getState()][mushroom.getAnimationIndex()],
                        (int) (mushroom.getHitbox().x - xLevelOffset - MUSHROOM_DRAWOFFSET_X + mushroom.getFlipX()),
                        (int) (mushroom.getHitbox().y - MUSHROOM_DRAWOFFSET_Y),
                        MUSHROOM_WIDTH * mushroom.getFlipW(), MUSHROOM_HEIGHT, null);

                // mushroom.drawHitbox(g, xLevelOffset);
                // mushroom.drawAttackBox(g, xLevelOffset);
            }
        }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Mushroom mushroom : mushrooms) {
            if (mushroom.isActive()) {
                if (attackBox.intersects(mushroom.getHitbox())) {
                    mushroom.hurt(10);
                    return;
                }
            }
        }
    }

    private void loadEnemyImages() {
        MushroomArray = new BufferedImage[5][11];
        BufferedImage temp = LoadSave.getSpriteAtlas(LoadSave.MUSHROOM_ATLAS);
        for (int j = 0; j < MushroomArray.length; j++) {
            for (int i = 0; i < MushroomArray[j].length; i++) {
                MushroomArray[j][i] = temp.getSubimage(i * MUSHROOM_WIDTH_DEFAULT, j * MUSHROOM_HEIGHT_DEFAULT,
                        MUSHROOM_WIDTH_DEFAULT, MUSHROOM_HEIGHT_DEFAULT);
            }
        }
    }

    public void resetAllEnemies() {
        for (Mushroom mushroom : mushrooms) {
            mushroom.resetEnemy();
        }
    }
}