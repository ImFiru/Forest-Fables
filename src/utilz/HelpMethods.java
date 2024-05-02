package utilz;

import main.Game;

import static main.Game.*;

import java.awt.geom.Rectangle2D;

public class HelpMethods {

	public static boolean canMoveHere(float x, float y, float width, float height, int[][] levelData) {
        // Cast vectors between corners
        if (castVector(x, y, x + width, y, levelData)) return false;
        if (castVector(x + width, y, x + width, y + height, levelData)) return false;
        if (castVector(x + width, y + height, x, y + height, levelData)) return false;
        if (castVector(x, y + height, x, y, levelData)) return false;
    
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
	private static boolean isSolid(float x, float y, int[][] lvlData) {
		if (x < 0 || x >= Game.GAME_WIDTH)
			return true;
		if (y < 0 || y >= Game.GAME_HEIGHT)
			return true;

		float xIndex = x / Game.TILES_SIZE;
		float yIndex = y / Game.TILES_SIZE;

		int value = lvlData[(int) yIndex][(int) xIndex];

		if (value > 198 || value < 0 || value != 0)
			return true;
		return false;
	}

    public static float getEntityXPositionNextToWall(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
        float x = hitbox.x;
        float width = hitbox.width;
        float height = hitbox.height;
        float y = hitbox.y;
    
        // Check if the entity is moving to the right
        if (xSpeed > 0) {
            // Cast a vector from the entity's top-right corner to the bottom-right corner
            if (castVector(x + width, y, x + width, y + height, lvlData)) {
                // If a collision is detected, move the entity back to the nearest tile boundary
                return ((int) (x + width)) / TILES_SIZE * TILES_SIZE - width;
            }
        }
        // Check if the entity is moving to the left
        else if (xSpeed < 0) {
            // Cast a vector from the entity's top-left corner to the bottom-left corner
            if (castVector(x, y, x, y + height, lvlData)) {
                // If a collision is detected, move the entity to the nearest tile boundary
                return ((int) x) / TILES_SIZE * TILES_SIZE + TILES_SIZE;
            }
        }
    
        // If no collision is detected, return the original x position
        return x;
    }

    public static float getEntityYPositionUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
		int currentTile = (int) (hitbox.y / Game.TILES_SIZE);
		if (airSpeed > 0) {
			// Falling - touching floor
			int tileYPos = currentTile * Game.TILES_SIZE;
			int yOffset = (int) (2 * Game.TILES_SIZE - hitbox.height);
			return tileYPos + yOffset - 1;
		} else
			// Jumping
			return currentTile * Game.TILES_SIZE;

	}


    public static boolean isEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
		// Check the pixel below bottomleft and bottomright
		if (!isSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
			if (!isSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
				return false;

		return true;

	}
}