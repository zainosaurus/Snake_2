/**
 * SnakeRunner Class : Runs the game
 * Created- 19/07/2015
 */
package snakeGame;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 *
 * @author Zain
 */
public class SnakeRunner extends StateBasedGame {
    private static final int X = 400;
    private static final int Y = 300;
    
    public static final int MENU_ID = 0;
    public static final int GAME_ID = 1;
    public static final int GAMEOVER_ID = 2;
    
    private GameOver gameOverScreen;
    
    // accessor for gameover screen
    public GameOver getGameOverScreen() {
        return gameOverScreen;
    }

    // constructor
    public SnakeRunner(String title) {
        super(title);
        gameOverScreen = new GameOver();
    }
    
    // main method
    public static void main(String[] args) {
        try {    
            AppGameContainer game = new AppGameContainer(new SnakeRunner("Snake Game"), X, Y, false);
            game.setTargetFrameRate(60);
            game.setShowFPS(false);
            game.start();
        } catch(SlickException slick) {
            slick.printStackTrace();
            System.exit(0);
        }
    }
    
    @Override
    public void initStatesList(GameContainer container) {
        addState(new MainMenu());
        addState(new SnakeGame());
        addState(gameOverScreen);
    }
    
    @Override
    public boolean closeRequested() {
        System.exit(0);
        return true;
    }
}
        
        
        
        
        
        
        
        
        
        
        
        
        
//        final int FRAME_RATE = 7;
//        Game snakeManager = new Game(X, Y);
//        snakeManager.startGame();
//        
//        try {
//            Display.setDisplayMode(new DisplayMode(X, Y));
//            Display.setTitle("Snake Game");
//            Display.create();
//        } catch (LWJGLException ex) {
//            System.out.println("LWJGL Exception Thrown");
//            System.exit(1);
//        }
//        
//        Keyboard.enableRepeatEvents(false);
//        
//        //initialize opengl
//        GL11.glMatrixMode(GL11.GL_PROJECTION);
//        GL11.glLoadIdentity();
//        GL11.glOrtho(0, 1000, 0, 600, 1, -1);
//        GL11.glMatrixMode(GL11.GL_MODELVIEW);
//        
//        //Square sq = new Square(100, 100);
//        
//        // game loop
//        while(!Display.isCloseRequested()) {
//            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
//            snakeManager.startGame();
//            Display.update();
//            Display.sync(FRAME_RATE);
//        }
//        
//        // close window
//        Display.destroy();
//        System.exit(0);
