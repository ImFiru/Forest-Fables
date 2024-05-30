package overlays;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import gamestates.GameState;
import gamestates.Playing;
import main.Game;
import ui.PauseButton;
import ui.RrsmButton;
import ui.SoundButton;
import ui.VolumeButton;

import static utilz.Constants.UI.Buttons.*;
import static utilz.Constants.UI.VolumeButtons.*;

public class PauseOverlay {

    private Playing playing;

    private SoundButton musicButton, sfxButton;
    private RrsmButton restartButton, resumeButton, settingsButton, menuButton, backButton;
    private VolumeButton sfxVolumeButton, musicVolumeButton;
    private boolean isSettingsPressed = false;

    public PauseOverlay(Playing playing) {
        this.playing = playing;

        createSoundButtons();
        createRrmButtons();
        createVolumeButtons();
    }

    private void createVolumeButtons() {
        int soundY = (int) (Game.GAME_HEIGHT / 3 - BUTTON_HEIGHT / 2 + 32 * Game.SCALE);
        int musicX = (int) (Game.GAME_WIDTH / 2 - BUTTON_WIDTH / 2 - 75 * Game.SCALE);
        int sfxX = (int) (Game.GAME_WIDTH / 2 - BUTTON_WIDTH / 2 + 75 * Game.SCALE);

        musicVolumeButton = new VolumeButton(musicX, soundY, SLIDER_WIDTH, VOLUME_HEIGHT);
        sfxVolumeButton = new VolumeButton(sfxX, soundY, SLIDER_WIDTH, VOLUME_HEIGHT);
    }

    private void createRrmButtons() {
        int buttonX = (int) (Game.GAME_WIDTH / 2 - BUTTON_WIDTH / 2);
        int buttonY = (int) (Game.GAME_HEIGHT / 2 - BUTTON_HEIGHT / 2);

        resumeButton = new RrsmButton(buttonX, (int) (buttonY - 72 * Game.SCALE), BUTTON_WIDTH, BUTTON_HEIGHT, 0);
        restartButton = new RrsmButton(buttonX, (int) (buttonY - 24 * Game.SCALE), BUTTON_WIDTH, BUTTON_HEIGHT, 1);
        settingsButton = new RrsmButton(buttonX, (int) (buttonY + 24 * Game.SCALE), BUTTON_WIDTH, BUTTON_HEIGHT, 2);
        menuButton = new RrsmButton(buttonX, (int) (buttonY + 72 * Game.SCALE), BUTTON_WIDTH, BUTTON_HEIGHT, 3);
        backButton = new RrsmButton(buttonX, (int) (buttonY - 48 + 72 * Game.SCALE), BUTTON_WIDTH, BUTTON_HEIGHT, 4);

    }

    private void createSoundButtons() {
        int soundY = (int) (Game.GAME_HEIGHT / 3 - BUTTON_HEIGHT / 2);
        int musicX = (int) (Game.GAME_WIDTH / 2 - BUTTON_WIDTH / 2 - 75 * Game.SCALE);
        int sfxX = (int) (Game.GAME_WIDTH / 2 - BUTTON_WIDTH / 2 + 75 * Game.SCALE);

        musicButton = new SoundButton(musicX, soundY, BUTTON_WIDTH, BUTTON_HEIGHT, false);
        sfxButton = new SoundButton(sfxX, soundY, BUTTON_WIDTH, BUTTON_HEIGHT, true);
    }

    public void draw(Graphics g) {
        // Sound Buttons
        if (isSettingsPressed) {
            musicButton.draw(g);
            sfxButton.draw(g);
            backButton.draw(g);
            musicVolumeButton.draw(g);
            sfxVolumeButton.draw(g);
        }
        // Rrsm Buttons
        else {
            resumeButton.draw(g);
            restartButton.draw(g);
            settingsButton.draw(g);
            menuButton.draw(g);
        }
    }

    public void update() {
        musicButton.update();
        sfxButton.update();
        resumeButton.update();
        restartButton.update();
        settingsButton.update();
        menuButton.update();
        backButton.update();
        musicVolumeButton.update();
        sfxVolumeButton.update();

    }

    public void mouseDragged(MouseEvent e) {
        if (musicVolumeButton.isMousePressed()) {
            musicVolumeButton.changeX(e.getX());
        } else if (sfxVolumeButton.isMousePressed()) {
            sfxVolumeButton.changeX(e.getX());
        }

    }

    public void mousePressed(MouseEvent e) {
        if (isSettingsPressed) {
            if (isIn(e, musicButton)) {
                musicButton.setMousePressed(true);
            } else if (isIn(e, sfxButton)) {
                sfxButton.setMousePressed(true);
            } else if (isIn(e, backButton)) {
                backButton.setMousePressed(true);
            } else if (isIn(e, musicVolumeButton)) {
                musicVolumeButton.setMousePressed(true);
            } else if (isIn(e, sfxVolumeButton)) {
                sfxVolumeButton.setMousePressed(true);
            }
        } else {
            if (isIn(e, resumeButton)) {
                resumeButton.setMousePressed(true);
            } else if (isIn(e, restartButton)) {
                restartButton.setMousePressed(true);
            } else if (isIn(e, settingsButton)) {
                settingsButton.setMousePressed(true);
            } else if (isIn(e, menuButton)) {
                menuButton.setMousePressed(true);
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (isSettingsPressed) {
            if (isIn(e, musicButton)) {
                if (musicButton.isMousePressed()) {
                    musicButton.setMuted(!musicButton.isMuted());
                }
            } else if (isIn(e, sfxButton)) {
                if (sfxButton.isMousePressed()) {
                    sfxButton.setMuted(!sfxButton.isMuted());
                }
            } else if (isIn(e, backButton)) {
                if (backButton.isMousePressed()) {
                    isSettingsPressed = !isSettingsPressed;
                }
            }
        } else {
            if (isIn(e, resumeButton)) {
                if (resumeButton.isMousePressed()) {
                    playing.unPauseGame();
                }
            } else if (isIn(e, restartButton)) {
                if (restartButton.isMousePressed()) {
                    playing.resetAll();
                    playing.unPauseGame();
                }
            } else if (isIn(e, settingsButton)) {
                if (settingsButton.isMousePressed()) {
                    isSettingsPressed = !isSettingsPressed;
                }
            } else if (isIn(e, menuButton)) {
                if (menuButton.isMousePressed()) {
                    GameState.gamestate = GameState.MENU;
                    playing.unPauseGame();
                }
            }
        }

        musicButton.resetBooleans();
        sfxButton.resetBooleans();
        backButton.resetBooleans();
        resumeButton.resetBooleans();
        restartButton.resetBooleans();
        settingsButton.resetBooleans();
        menuButton.resetBooleans();
        musicVolumeButton.resetBooleans();
        sfxVolumeButton.resetBooleans();

    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        backButton.setMouseOver(false);
        resumeButton.setMouseOver(false);
        restartButton.setMouseOver(false);
        settingsButton.setMouseOver(false);
        menuButton.setMouseOver(false);
        musicVolumeButton.setMouseOver(false);
        sfxVolumeButton.setMouseOver(false);

        if (isSettingsPressed) {
            if (isIn(e, musicButton)) {
                musicButton.setMouseOver(true);
            } else if (isIn(e, sfxButton)) {
                sfxButton.setMouseOver(true);
            } else if (isIn(e, backButton)) {
                backButton.setMouseOver(true);
            } else if (isIn(e, musicVolumeButton)) {
                musicVolumeButton.setMouseOver(true);
            } else if (isIn(e, sfxVolumeButton)) {
                sfxVolumeButton.setMouseOver(true);
            }
        } else {

            if (isIn(e, resumeButton)) {
                resumeButton.setMouseOver(true);
            } else if (isIn(e, restartButton)) {
                restartButton.setMouseOver(true);
            } else if (isIn(e, settingsButton)) {
                settingsButton.setMouseOver(true);
            } else if (isIn(e, menuButton)) {
                menuButton.setMouseOver(true);
            }
        }
    }

    private boolean isIn(MouseEvent e, PauseButton button) {
        return button.getBounds().contains(e.getPoint());
    }

}