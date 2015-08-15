/*
 * Main Menu screen for snake game
 */
package snakeGame;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 *
 * @author Zain
 */
public class MainMenu extends BasicGameState {
    // Variables
    private Image snakeImage;
    private StateBasedGame game;
    private GameContainer container;
    
    // default constructor is used
    
    // methods
    
    @Override
    public void init(GameContainer container, StateBasedGame game) {
        this.game = game;
        this.container = container;
        
        try {
            snakeImage = new Image("/Users/Zain/NetBeansProjects/Snake_Copy/src/snakeGame/snake.jpg");
            snakeImage.setFilter(Image.FILTER_NEAREST);
        } catch (SlickException slick) {
            slick.printStackTrace();
            System.exit(1);
        }
    }
    
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        snakeImage.draw(150, 100, 100, 100);
        g.drawString("PLAY [SPACE]\nQUIT  [ESC]", 160, 250);
    }
    
    @Override
    public void update(GameContainer container, StateBasedGame game, int time) {
        
    }
    
    // handles input
    public void keyReleased(int key, char c) {
        if (key == Input.KEY_SPACE) {
            try {
                container.reinit();
            } catch(SlickException slick) {
                slick.printStackTrace();
                System.exit(1);
            }
            
            game.enterState(SnakeRunner.GAME_ID);
        }
        else if (key == Input.KEY_ESCAPE) {
            System.exit(0);
        }
    }
   
    @Override
    public int getID() {
        return SnakeRunner.MENU_ID;
    }
}
