package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utilz.LoadSave;

import static utilz.Constants.UI.Buttons.*;

public class SoundButton extends PauseButton {

    private BufferedImage[][] soundButtonImages;
    private boolean isSfx;
    private boolean mouseOver, mousePressed;
    private boolean muted;
    private int rowIndex, columnIndex;

    public SoundButton(int x, int y, int width, int height, boolean isSfx) {
        super(x, y, width, height);
        this.isSfx = isSfx;
        loadSoundImages();
    }

    private void loadSoundImages() {
        BufferedImage tempImage = LoadSave.getSpriteAtlas(LoadSave.SOUND_BUTTONS);
        soundButtonImages = new BufferedImage[4][3];

        for (int j = 0; j < soundButtonImages.length; j++) {
            for (int i = 0; i < soundButtonImages[j].length; i++) {
                int x = i * BUTTON_WIDTH_DEFAULT;
                int y = j * BUTTON_HEIGHT_DEFAULT;
                soundButtonImages[j][i] = tempImage.getSubimage(x, y, BUTTON_WIDTH_DEFAULT, BUTTON_HEIGHT_DEFAULT);
            }
        }
    }

    public void resetBooleans() {
        mouseOver = false;
        mousePressed = false;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(soundButtonImages[rowIndex][columnIndex], x, y, BUTTON_WIDTH, BUTTON_HEIGHT, null);
    }
    
    @Override
    public void update() {
        if (isSfx) {
            // For SFX button
            if (muted) {
                rowIndex = 3;
            } else {
                rowIndex = 2;
            }
        } else {
            // For Music button
            if (muted) {
                rowIndex = 1;
            } else {
                rowIndex = 0;
            }
        }
    
        columnIndex = 0;
        if (mouseOver) {
            columnIndex = 1;
        }
        if (mousePressed) {
            columnIndex = 2;
        }
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }
}