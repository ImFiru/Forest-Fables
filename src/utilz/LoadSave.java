package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;


import static main.Game.*;

public class LoadSave {
    public static final String PLAYER_ATLAS = "entities/player/player_animations.png";
    public static final String LEVEL_ATLAS = "levels/oak_woods/oak_woods_tileset.png";
    public static final String LEVEL_ONE_DATA = "levels/data/level_one_data_long.png";
    public static final String LEVEL_BACKGROUND_IMAGE_1 = "levels/oak_woods/background/background_layer_1.png";
    public static final String LEVEL_BACKGROUND_IMAGE_2 = "levels/oak_woods/background/background_layer_2.png";
    public static final String LEVEL_BACKGROUND_IMAGE_3 = "levels/oak_woods/background/background_layer_3.png";

    
    public static final String MENU_BUTTONS = "ui/start_menu/button_atlas.png";
    public static final String MENU_BACKGROUND = "ui/start_menu/menu_background.png";
    public static final String MENU_LOGO = "ui/start_menu/logo.png";

    public static final String PAUSE_BACKGROUND = "ui/pause_menu/pause_background.png";
    public static final String PAUSE_BUTTONS = "ui/pause_menu/button_atlas.png";
    public static final String SOUND_BUTTONS = "ui/pause_menu/sound_button_atlas.png";
    public static final String VOLUME_SLIDER_BUTTONS = "ui/pause_menu/slider_button_atlas.png";


    
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
        BufferedImage image = getSpriteAtlas(LEVEL_ONE_DATA);
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
}
