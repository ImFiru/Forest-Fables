package levels;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Mushroom;
import main.Game;
import utilz.HelpMethods;

import static utilz.HelpMethods.getLevelDataFromImage;
import static utilz.HelpMethods.getMushroomData;

public class Level {

    private BufferedImage image;
    private int[][] levelData;
    private ArrayList<Mushroom> Mushrooms;
    private int levelTilesWide;
    private int maxTilesOffset;
    private int maxLevelOffsetX;
    private Point playerSpawn;

    public Level(BufferedImage image) {
        this.image = image;
        createLevelData();
        createEnemies();
        calcLevelOffsets();
        calcPlayerSpawn();
    }

    private void calcPlayerSpawn() {
        playerSpawn = HelpMethods.getPlayerSpawn(image);
    }

    private void calcLevelOffsets() {
        levelTilesWide = image.getWidth();
        maxTilesOffset = levelTilesWide - Game.TILES_IN_WIDTH;
        maxLevelOffsetX = maxTilesOffset * Game.TILES_SIZE;
    }

    private void createEnemies() {
        Mushrooms = getMushroomData(image);
    }

    private void createLevelData() {
        levelData = getLevelDataFromImage(image);
    }

    public int getSpriteIndex(int x, int y) {
        return levelData[y][x];
    }

    public int[][] getLevelData() {
        return levelData;
    }

    public int getLevelOfsetX() {
        return maxLevelOffsetX;
    }

    public ArrayList<Mushroom> getMushrooms() {
        return Mushrooms;
    }

    public Point getPlayerSpawn() {
        return playerSpawn;
    }
}
