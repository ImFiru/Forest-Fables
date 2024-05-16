package ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gamestates.GameState;
import utilz.LoadSave;

import static utilz.Constants.UI.Buttons.*;

public class MenuButton {

    private int xPosition, yPositon, rowIndex, index;
    private int xOffsetCenter = BUTTON_WIDTH / 2;
    private GameState gameState;
    private BufferedImage[] buttonImages;
    private boolean mouseOver, mousePressed;
    private Rectangle bounds;

    public MenuButton(int xPosition, int yPositon, int rowIndex, GameState gameState) {
        this.xPosition = xPosition;
        this.yPositon = yPositon;
        this.rowIndex = rowIndex;
        this.gameState = gameState;
        loadImages();
        initBounds();
    }

    private void initBounds() {
        bounds = new Rectangle(xPosition - xOffsetCenter, yPositon, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    private void loadImages() {
        buttonImages = new BufferedImage[3];
        BufferedImage tempImage = LoadSave.getSpriteAtlas(LoadSave.MENU_BUTTONS);

        for (int i = 0; i < buttonImages.length; i++) {
            buttonImages[i] = tempImage.getSubimage(i * BUTTON_WIDTH_DEFAULT, rowIndex * BUTTON_HEIGHT_DEFAULT, BUTTON_WIDTH_DEFAULT, BUTTON_HEIGHT_DEFAULT);
        }
    }

    public void draw(Graphics g) {
        g.drawImage(buttonImages[index], xPosition - xOffsetCenter, yPositon, BUTTON_WIDTH, BUTTON_HEIGHT, null);
    }

    public void update() {
        index = 0;
        if (mouseOver){
            index = 1;
        } 
        if (mousePressed){
            index = 2;
        }
    }

    public Rectangle getBounds() {
        return bounds;
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

    public void applyGameState() {
        GameState.gamestate = gameState;
    }

    public void resetBooleans() {
        mouseOver = false;
        mousePressed = false;
    }
}
