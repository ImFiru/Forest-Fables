package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.MenuButton;
import utilz.LoadSave;

public class Menu extends State implements StateMethods{

    private MenuButton[] menuButtons = new MenuButton[3];

    private BufferedImage[] backgroundImages;
    private int backgroundImageIndex = 0;
    private int animationTimer = 0;
    private int animationDelay = 35;

    private BufferedImage logoImage;
	private int logoX, logoY, logoWidth, logoHeight;
   
    private StringBuilder inputBuffer = new StringBuilder();

    public Menu(Game game) {
        super(game);
        loadButtons();
        loadBackground();
        loadLogo();
    }

    private void loadLogo() {
		logoImage = LoadSave.getSpriteAtlas(LoadSave.MENU_LOGO);
		logoWidth = (int) (logoImage.getWidth() * Game.SCALE / 2.5);
		logoHeight = (int) (logoImage.getHeight() * Game.SCALE / 2.5);
		logoX = Game.GAME_WIDTH / 2 - logoWidth / 2;
		logoY = (int) (30 * Game.SCALE);

	}

    private void loadBackground() {
        backgroundImages = new BufferedImage[10];
        BufferedImage spriteAtlas = LoadSave.getSpriteAtlas(LoadSave.MENU_BACKGROUND);
    
        for (int i = 0; i < backgroundImages.length; i++) {
            backgroundImages[i] = spriteAtlas.getSubimage(i * 320, 0, 320, spriteAtlas.getHeight());
        }
    }

    private void updateBackgroundImageIndex() {
        animationTimer++;
        if (animationTimer >= animationDelay) {
            backgroundImageIndex = (backgroundImageIndex + 1) % backgroundImages.length;
            animationTimer = 0;
        }
    }

    private void loadButtons() {
        menuButtons[0] = new MenuButton((int) (120 * Game.SCALE), Game.GAME_HEIGHT - 150, 0, GameState.PLAYING);
		menuButtons[1] = new MenuButton(Game.GAME_WIDTH / 2, Game.GAME_HEIGHT - 150, 1, GameState.OPTIONS);
		menuButtons[2] = new MenuButton((int) (Game.GAME_WIDTH - 120 * Game.SCALE), Game.GAME_HEIGHT - 150, 2, GameState.QUIT);
    }
    
    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImages[backgroundImageIndex], 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(logoImage, logoX, logoY, logoWidth, logoHeight, null);
        for (MenuButton menubutton : menuButtons) {
            menubutton.draw(g);
        }
    }

    @Override
    public void update() {
        updateBackgroundImageIndex();
        for (MenuButton menubutton : menuButtons) {
            menubutton.update();
        }
    }

    
    private void resetButtons() {
        for (MenuButton menubutton : menuButtons) {
            menubutton.resetBooleans();
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
        }
        resetButtons();
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
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_ENTER) {
            String input = inputBuffer.toString().trim();
            inputBuffer.setLength(0);

            if (input.equalsIgnoreCase("admin")) {
                System.out.println("Admin panel activated");
            } else {
                System.out.println("Invalid input");
            }
        } else if (keyCode == KeyEvent.VK_BACK_SPACE) {
            if (inputBuffer.length() > 0) {
                inputBuffer.deleteCharAt(inputBuffer.length() - 1);
            }
        } else {
            inputBuffer.append(e.getKeyChar());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    
    
    
}