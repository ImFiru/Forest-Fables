package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utilz.LoadSave;

import static utilz.Constants.UI.VolumeButtons.*;

public class VolumeButton extends PauseButton {

    private BufferedImage[] buttonImages;
    private BufferedImage slider;
    private boolean mouseOver, mousePressed;
    private int index;
    private int buttonX, minimumX, maximumX;


    public VolumeButton(int x, int y, int width, int height) {
        super(x + width / 2, y, VOLUME_WIDTH, height);
        bounds.x -= VOLUME_WIDTH / 2;
        buttonX = x + width / 2;
        this.x = x;
        this.width = width;
        minimumX = x + VOLUME_WIDTH / 2;
        maximumX = x + width - VOLUME_WIDTH / 2;
        loadButtonImages();
    }

    private void loadButtonImages() {
        BufferedImage tempImage = LoadSave.getSpriteAtlas(LoadSave.VOLUME_SLIDER_BUTTONS);
        buttonImages = new BufferedImage[3];

        for(int i = 0; i < buttonImages.length; i++) {
            buttonImages[i] = tempImage.getSubimage(i * VOLUME_WIDTH_DEFAULT, 0, VOLUME_WIDTH_DEFAULT, VOLUME_HEIGHT_DEFAULT);
        }
        
        slider = tempImage.getSubimage(3 * VOLUME_WIDTH_DEFAULT, 0, SLIDER_WIDTH_DEFAULT, SLIDER_HEIGHT_DEFAULT);
    }

    @Override
    public void draw(Graphics g) {
       g.drawImage(slider, x, y, width, height, null);
       g.drawImage(buttonImages[index], buttonX - VOLUME_WIDTH / 2, y, VOLUME_WIDTH, VOLUME_HEIGHT, null);

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

    public void changeX(int x) {
        if (x < minimumX) {
            buttonX = minimumX;
        } else if (x > maximumX) {
            buttonX = maximumX;
        } else {
            buttonX = x;
        }

        bounds.x = buttonX - VOLUME_WIDTH / 2;
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