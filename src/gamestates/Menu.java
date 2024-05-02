package gamestates;


import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import main.Game;
import ui.MenuButton;

import static main.Game.*;
import static gamestates.GameState.*;
import static utilz.Constants.UI.Buttons.*;

public class Menu extends State implements StateMethods{

    private MenuButton[] menuButtons = new MenuButton[3];

    public Menu(Game game) {
        super(game);
        loadButtons();
        
    }

    private void loadButtons() {
        menuButtons[0] = new MenuButton(GAME_WIDTH / 2,(int) (50 * SCALE),0, PLAYING);
        menuButtons[1] = new MenuButton(GAME_WIDTH / 2,(int) (95 * SCALE),1, OPTIONS);
        menuButtons[2] = new MenuButton(GAME_WIDTH / 2,(int) (140 * SCALE),2, PLAYING);
        }

    @Override
    public void update() {
        for (MenuButton menubutton : menuButtons) {
            menubutton.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        for (MenuButton menubutton : menuButtons) {
            menubutton.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton menubutton : menuButtons) {
            if (isIn(e, menubutton)){
                menubutton.setMousePressed(true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton menubutton : menuButtons) {
            if (isIn(e, menubutton)){
                if (menubutton.isMousePressed()){
                    menubutton.applyGameState();
                    break;
                }
            }
            resetButtons();
        }
    }

    private void resetButtons() {
        for (MenuButton menubutton : menuButtons) {
            menubutton.resetBooleans();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton menubutton : menuButtons) {
            menubutton.setMouseOver(false);
        }
        for (MenuButton menubutton : menuButtons) {
            if (isIn(e, menubutton)){
                menubutton.setMouseOver(true);
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            GameState.gamestate = GameState.PLAYING;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    
    
    
}