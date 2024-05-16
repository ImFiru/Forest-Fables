package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utilz.LoadSave;

import static utilz.Constants.UI.Buttons.*;

public class RrsmButton extends PauseButton {

    private BufferedImage[] buttonImages;
    private int rowIndex, index;
    private boolean mouseOver, mousePressed;

    public RrsmButton(int x, int y, int width, int height, int rowIndex) {
        super(x, y, width, height);
        this.rowIndex = rowIndex;
        
        loadButtonImages();
    }

    private void loadButtonImages() {
        BufferedImage tempImage = LoadSave.getSpriteAtlas(LoadSave.PAUSE_BUTTONS);
        buttonImages = new BufferedImage[3];

        for(int i = 0; i < buttonImages.length; i++) {
            buttonImages[i] = tempImage.getSubimage(i * BUTTON_WIDTH_DEFAULT, rowIndex * BUTTON_HEIGHT_DEFAULT, BUTTON_WIDTH_DEFAULT, BUTTON_HEIGHT_DEFAULT);
        }
    }

    @Override
    public void draw(Graphics g) {
       g.drawImage(buttonImages[index], x, y, BUTTON_WIDTH, BUTTON_HEIGHT, null);
    }
    
    @Override
    public void update() {
        index = 0;
        if (mouseOver){
            index = 1;
        }
        if (mousePressed){
            index = 2;
        }
    }

    public void resetBooleans() {
        mouseOver = false;
        mousePressed = false;
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

    
}
