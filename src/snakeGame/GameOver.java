/*
 * -Djava.library.path="/Users/Zain/LWJGL/lwjgl-2.9.3/native/macosx"
 */
package snakeGame;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 *
 * @author Zain
 */
public class GameOver extends BasicGameState {
    // Variables
    StateBasedGame game;
    GameContainer container;
    private int points;
    private String status;
    private final int LINE_SPACING = 25;
    
    // Default constructor
    
    // mutators
    public void setPoints(int p) {
        points = p;
    }
    public void setStatus(String s) {
        status = s;
    }
    
    
    @Override
    public void init(GameContainer container, StateBasedGame game) {
        this.game = game;
        this.container = container;
        points = 0;
        status = "No Status";
    }
    
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        g.drawString("Game Over", (container.getWidth() - g.getFont().getWidth("Game Over")) / 2, (container.getHeight() - g.getFont().getHeight("Game Over")) / 4);
        g.drawString(status, (container.getWidth() - g.getFont().getWidth(status)) / 2, (container.getHeight() - g.getFont().getHeight("Game Over")) / 4 + LINE_SPACING);
        g.drawString("Points: " + points, (container.getWidth() - g.getFont().getWidth("Points: " + points)) / 2, (container.getHeight() - g.getFont().getHeight("Game Over")) / 4 + 2 * LINE_SPACING);
        g.drawString("PLAY AGAIN [SPACE]", (container.getWidth() - g.getFont().getWidth("PLAY AGAIN [SPACE]")) / 2, (container.getHeight() - g.getFont().getHeight("Game Over")) / 4 + 4 * LINE_SPACING);
        g.drawString("QUIT [ESC]", (container.getWidth() - g.getFont().getWidth("QUIT [ESC]")) / 2, (container.getHeight() - g.getFont().getHeight("Game Over")) / 4 + 5 * LINE_SPACING);
    }
    
    @Override
    public void update(GameContainer container, StateBasedGame game, int time) {
        
    }
    
    // key presses
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
            game.enterState(SnakeRunner.MENU_ID);
        }
    }
    
    @Override
    public int getID() {
        return SnakeRunner.GAMEOVER_ID;
    }
}
