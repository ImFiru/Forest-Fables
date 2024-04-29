package utilz;

import main.Game;

import static main.Game.*;

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
}