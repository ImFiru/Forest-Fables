package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

import main.Game;

import static main.Game.*;

public class LoadSave {
    public static final String PLAYER_ATLAS = "entities/player/player_animations.png";
    public static final String LEVEL_ATLAS = "levels/oak_woods/oak_woods_tileset.png";
    public static final String LEVEL_ONE_DATA = "levels/data/level_one_data.png";
    public static final String MENU_BUTTONS = "ui/menu/menu_buttons.png";


     public static BufferedImage getSpriteAtlas(String fileName) {
        BufferedImage image = null;
        try (InputStream is = LoadSave.class.getResourceAsStream("/" + fileName)) {
            if (is != null) {
                image = ImageIO.read(is);
        } else {
            throw new FileNotFoundException("Image " + fileName + " was not found!");
        }
            
    } catch (IOException e) {
        e.printStackTrace();
    }
        return image;
    
    }

    public static int[][] getLevelData() {
        int[][] levelData = new int[TILES_IN_HEIGHT][TILES_IN_WIDTH];
        BufferedImage image = getSpriteAtlas(LEVEL_ONE_DATA);
        
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
}
