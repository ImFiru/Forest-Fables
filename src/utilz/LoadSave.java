package utilz;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

public class LoadSave {
    public static final String PLAYER_ATLAS = "entities/player/player_animations.png";
    public static final String CAT_ATLAS = "entities/cat/cat_animations.png";
    public static final String MUSHROOM_ATLAS = "entities/enemies/mushroom_animations.png";

    public static final String LEVEL_ATLAS = "levels/oak_woods/oak_woods_tileset.png";
    public static final String LEVEL_BACKGROUND_IMAGE_1 = "levels/oak_woods/background/background_layer_1.png";
    public static final String LEVEL_BACKGROUND_IMAGE_2 = "levels/oak_woods/background/background_layer_2.png";
    public static final String LEVEL_BACKGROUND_IMAGE_3 = "levels/oak_woods/background/background_layer_3.png";

    public static final String MENU_BUTTONS = "ui/start_menu/button_atlas.png";
    public static final String MENU_BACKGROUND = "ui/start_menu/menu_background.png";
    public static final String MENU_LOGO = "ui/start_menu/logo.png";

    public static final String PAUSE_BUTTONS = "ui/pause_menu/button_atlas.png";
    public static final String SOUND_BUTTONS = "ui/pause_menu/sound_button_atlas.png";
    public static final String VOLUME_SLIDER_BUTTONS = "ui/pause_menu/slider_button_atlas.png";

    public static final String STATUS_BAR = "ui/playing_ui/health_power_bar.png";

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

    public static BufferedImage[] getAllLevels() {
        URL url = LoadSave.class.getResource("/levels/data");
        File file = null;

        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        File[] files = file.listFiles();
        File[] sortedFiles = new File[files.length];

        for (int i = 0; i < sortedFiles.length; i++) {
            for (int j = 0; j < files.length; j++) {
                if (files[j].getName().equals(i + 1 + ".png")) {
                    sortedFiles[i] = files[j];
                }
            }

        }

        BufferedImage[] images = new BufferedImage[sortedFiles.length];

        for (int i = 0; i < images.length; i++) {
            try {
                images[i] = ImageIO.read(sortedFiles[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return images;
    }
}
