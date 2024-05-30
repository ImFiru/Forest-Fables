package overlays;

import static utilz.Constants.UI.Buttons.BUTTON_HEIGHT;
import static utilz.Constants.UI.Buttons.BUTTON_WIDTH;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.GameState;
import gamestates.Playing;
import main.Game;
import ui.RrsmButton;
import utilz.LoadSave;

public class LevelCompletedOverlay {
    private Playing playing;
    private RrsmButton menuButton, nextLevelButton;
    private BufferedImage levelCompletedImage;
    private int x, y, width, height;

    public LevelCompletedOverlay(Playing playing) {
        this.playing = playing;
        initImage();
        initButtons();
    }

    private void initButtons() {
        int buttonX = Game.GAME_WIDTH / 2 - BUTTON_WIDTH / 2;
        int buttonY = Game.GAME_HEIGHT / 2 + height / 2;
        menuButton = new RrsmButton(buttonX, (int) (buttonY - 72 * Game.SCALE), BUTTON_WIDTH, BUTTON_HEIGHT, 3);
        nextLevelButton = new RrsmButton(buttonX, (int) (buttonY - 24 * Game.SCALE), BUTTON_WIDTH, BUTTON_HEIGHT, 0);
    }

    private void initImage() {
        levelCompletedImage = LoadSave.getSpriteAtlas(LoadSave.MENU_LOGO); // change
        width = (int) (levelCompletedImage.getWidth() * Game.SCALE / 4);
        height = (int) (levelCompletedImage.getHeight() * Game.SCALE / 4);
        x = Game.GAME_WIDTH / 2 - width / 2;
        y = (int) (20 * Game.SCALE);
    }

    public void draw(Graphics g) {
        g.drawImage(levelCompletedImage, x, y, width, height, null);
        menuButton.draw(g);
        nextLevelButton.draw(g);
    }

    public void update() {
        menuButton.update();
        nextLevelButton.update();
    }

    private boolean isIn(RrsmButton button, MouseEvent e) {
        return button.getBounds().contains(e.getPoint());
    }

    public void mouseMoved(MouseEvent e) {
        menuButton.setMouseOver(false);
        nextLevelButton.setMouseOver(false);

        if (isIn(menuButton, e)) {
            menuButton.setMouseOver(true);
        } else if (isIn(nextLevelButton, e)) {
            nextLevelButton.setMouseOver(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(menuButton, e)) {
            if (menuButton.isMousePressed()) {
                playing.resetAll();
                GameState.gamestate = GameState.MENU;
            }
        } else if (isIn(nextLevelButton, e)) {
            if (nextLevelButton.isMousePressed()) {
                playing.loadNexLevel();
            }
        }

        menuButton.resetBooleans();
        nextLevelButton.resetBooleans();
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(menuButton, e)) {
            menuButton.setMousePressed(true);
        } else if (isIn(nextLevelButton, e)) {
            nextLevelButton.setMousePressed(true);
        }
    }

}
