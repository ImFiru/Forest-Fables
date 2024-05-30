package utilz;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Mushroom;
import main.Game;

import static main.Game.*;
import static utilz.Constants.Entities.*;
import static utilz.Constants.EnemyConstants.*;

public class HelpMethods {

    public static boolean canMoveHere(float x, float y, float width, float height, int[][] levelData) {
        if (castVector(x, y, x + width, y, levelData)) {
            return false;
        }
        if (castVector(x + width, y, x + width, y + height, levelData)) {
            return false;
        }
        if (castVector(x + width, y + height, x, y + height, levelData)) {
            return false;
        }
        if (castVector(x, y + height, x, y, levelData)) {
            return false;
        }
        return true;
    }

    private static boolean castVector(float x1, float y1, float x2, float y2, int[][] levelData) {
        float dx = x2 - x1;
        float dy = y2 - y1;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        dx /= distance;
        dy /= distance;

        float x = x1;
        float y = y1;

        for (int i = 0; i < distance; i++) {
            x += dx;
            y += dy;

            if (isSolid(x, y, levelData)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isSolid(float x, float y, int[][] levelData) {
        int maxWidth = levelData[0].length * Game.TILES_SIZE;
        if (x < 0 || x >= maxWidth) {
            return true;
        }
        if (y < 0 || y >= Game.GAME_HEIGHT) {
            return true;
        }

        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        return isTileSolid((int) (xIndex), (int) (yIndex), levelData);
    }

    private static boolean isTileSolid(int xTile, int yTile, int[][] levelData) {
        int value = levelData[yTile][xTile];

        if (value > 198 || value < 0 || value != 0) {
            return true;
        }

        return false;
    }

    public static float getEntityXPositionNextToWall(Rectangle2D.Float hitbox, float xSpeed, int[][] levelData) {
        float x = hitbox.x;
        float width = hitbox.width;
        float height = hitbox.height;
        float y = hitbox.y;

        // Check if the entity is moving to the right
        if (xSpeed > 0) {
            // Cast a vector from the entity's top-right corner to the bottom-right corner
            if (castVector(x + width, y, x + width, y + height, levelData)) {
                // If a collision is detected, move the entity back to the nearest tile boundary
                return ((int) (x + width)) / TILES_SIZE * TILES_SIZE - width;
            }
        }
        // Check if the entity is moving to the left
        else if (xSpeed < 0) {
            // Cast a vector from the entity's top-left corner to the bottom-left corner
            if (castVector(x, y, x, y + height, levelData)) {
                // If a collision is detected, move the entity to the nearest tile boundary
                return ((int) x) / TILES_SIZE * TILES_SIZE + TILES_SIZE;
            }
        }

        // If no collision is detected, return the original x position
        return x;
    }

    public static float getEntityYPositionUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed, int type) {
        int currentTile = (int) (hitbox.y / Game.TILES_SIZE);
        int yOffsetMultiplier = 0;
        switch (type) {
            case PLAYER:
            case ENEMY_MUSHROOM:
                yOffsetMultiplier = 2;
                break;
            case PLAYERCAT:
                yOffsetMultiplier = 1;
            default:
                break;
        }
        if (airSpeed > 0) {
            // Falling - touching floor
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffset = (int) (yOffsetMultiplier * Game.TILES_SIZE - hitbox.height);
            return tileYPos + yOffset - 1;
        } else
            // Jumping
            return currentTile * Game.TILES_SIZE;

    }

    public static boolean isEntityOnFloor(Rectangle2D.Float hitbox, int[][] levelData) {
        // Check the pixel below bottomleft and bottomright
        if (!isSolid(hitbox.x, hitbox.y + hitbox.height + 1, levelData)) {
            if (!isSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, levelData)) {
                return false;
            }
        }
        return true;

    }

    public static boolean isFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] levelData) {
        if (xSpeed > 0) {
            return isSolid(hitbox.x + hitbox.width + xSpeed, hitbox.y + hitbox.height + 1, levelData);
        } else {
            return isSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, levelData);
        }
    }

    public static boolean isAllTilesWalkable(int xStart, int XEnd, int y, int[][] levelData) {
        for (int i = 0; i < XEnd - xStart; i++) {
            if (isSolid(xStart + i, y, levelData)) {
                return false;
            }
            if (!isTileSolid(xStart + i, y + 1, levelData)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSightClear(int[][] levelData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox,
            int tileY) {
        int firstTileX = (int) (firstHitbox.x / TILES_SIZE);
        int secondTileX = (int) (secondHitbox.x / TILES_SIZE);

        if (firstTileX > secondTileX) {
            return isAllTilesWalkable(secondTileX, firstTileX, tileY + 1, levelData);
        } else {
            return isAllTilesWalkable(firstTileX, secondTileX, tileY + 1, levelData);
        }
    }

    public static int[][] getLevelDataFromImage(BufferedImage image) {

        int[][] levelData = new int[image.getHeight()][image.getWidth()];

        for (int j = 0; j < image.getHeight(); j++) {
            for (int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getRed();
                if (value >= 198) {
                    value = 0;
                }
                levelData[j][i] = value;
            }
        }
        return levelData;
    }

    public static ArrayList<Mushroom> getMushroomData(BufferedImage image) {

        ArrayList<Mushroom> list = new ArrayList<>();

        for (int j = 0; j < image.getHeight(); j++) {
            for (int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getGreen();
                if (value == MUSHROOM) {
                    list.add(new Mushroom(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
                }
            }
        }
        return list;
    }

    public static Point getPlayerSpawn(BufferedImage image) {
        for (int j = 0; j < image.getHeight(); j++) {
            for (int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getGreen();
                if (value >= 100) {
                    return new Point(i * Game.TILES_SIZE, j * Game.TILES_SIZE);
                }
            }
        }
        return new Point(1 * Game.TILES_SIZE, 1 * Game.TILES_SIZE);
    }
}