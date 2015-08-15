/**
 * Game Class: Manager the Game
 * Created- 18/07/2015
 */
package snakeGame;

import java.util.*;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.state.*;

/**
 *
 * @author Zain
 */
public class SnakeGame extends BasicGameState {
    // Variables
    private final int POINTS_PER_MEAL = 10;
    private final int SPEED = 9;    // how often to move the snake (# of frames)
    private final int SQUARE_SIZE = 10;
    
    private Random uberDice;
    private Snake snake;
    private Rectangle food;
    
    private long count; // counts the frames which have elapsed (snake will only move every few count values to control speed)
    private int points;
    private int maxx;
    private int maxy;
    
    private StateBasedGame game;
    private GameContainer container;
    
    // Constructor not required
    
    
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        this.game = game;
        this.container = container;
        
        maxx = container.getWidth();
        maxy = container.getHeight();
        
        uberDice = new Random();
        snake = new Snake(0, 0);
        food = new Rectangle(0, 0, SQUARE_SIZE, SQUARE_SIZE);
        points = 0;
        count = 0;
        initFood();
    }
    
    
    @Override
    public void update(GameContainer container, StateBasedGame game, int time) throws SlickException {
        if (count % SPEED == 0) {
            snake.move();
        }
        count++;
        handleCollisions();
    }
    
    
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        snake.draw(g);
        g.fill(food);
        drawPoints(container, g);
    }
    
    @Override
    public int getID() {
        return SnakeRunner.GAME_ID;
    }
    
    
    /**
     * Handles a key release for movement
     * @param key The key that was pressed (int)
     * @param c The character code (for this program it will be blank since arrow keys are used)
     */
    
    @Override
    public void keyReleased(int key, char c) {
        if (key == Input.KEY_UP && snake.getDirection() != Direction.DOWN) {
            snake.setDirection(Direction.UP);
        }
        else if (key == Input.KEY_DOWN && snake.getDirection() != Direction.UP) {
            snake.setDirection(Direction.DOWN);
        }
        else if (key == Input.KEY_LEFT && snake.getDirection() != Direction.RIGHT) {
            snake.setDirection(Direction.LEFT);
        }
        else if (key == Input.KEY_RIGHT && snake.getDirection() != Direction.LEFT) {
            snake.setDirection(Direction.RIGHT);
        }
        else if (key == Input.KEY_P) {
            if (container.isPaused()) {
                container.resume();
            } else {
                container.pause();
            }
            //container.setPaused(true);
        }
        else if (key == Input.KEY_ESCAPE) {
            game.enterState(SnakeRunner.MENU_ID);
        }
    }
    
    
    /**
     * Checks and handles any collisions
     */
    public void handleCollisions() {
        // handles out of bounds
        if (outOfBounds()) {
            gameOver("(Out of Bounds)");
        }
        //handles food being eaten
        if (foodEaten()) {
            points += POINTS_PER_MEAL;
            initFood();
            snake.addSegment();
        }
        // handles snake eating itself
        if (cannibal()) {
            gameOver("(You ate yourself)");
        }
        
    }
    
    /**
     * Detects when the snake goes out of bounds
     * @return out : boolean true when the snake is out of bounds
     */
    public boolean outOfBounds() {
        boolean out = false;
        Direction dir = snake.getDirection();
        if (dir == Direction.DOWN && snake.getLocation().getY() > maxy - SQUARE_SIZE) {
            out = true;
        } else if (dir == Direction.UP && snake.getLocation().getY() < 0) {
            out = true;
        } else if (dir == Direction.LEFT && snake.getLocation().getX() < 0) {
            out = true;
        } else if (dir == Direction.RIGHT && snake.getLocation().getX() > maxx - SQUARE_SIZE) {
            out = true;
        }
        return out;
    }
    
    /**
     * Detects when the snake eats the food
     * @return boolean true when the food is eaten (will then spawn a new food)
     */
    public boolean foodEaten() {
        if (snake.getLocation().equals(food.getLocation())) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Detects when the snake eats itself
     * @return check True when the snake eats itself
     */
    public boolean cannibal() {
        boolean check = false;
        Rectangle[] array = snake.getSegments();
        for (int i = 1; i < array.length; i++) {
            if (snake.getLocation().getX() == array[i].getLocation().getX() && snake.getLocation().getY() == array[i].getLocation().getY()) {
                check = true;
            }
        }
        return check;
    }
    
    
    /**
     * Draws the Points on the Screen
     * @param c : GameContainer the container
     * @param g : Graphics the graphics to use
     */
    
    public void drawPoints(GameContainer c, Graphics g) {
        g.drawString("" + points, c.getWidth() - 50, 10);
    }
    
    
    /** 
     * Generates a random coordinate for the food to spawn on. 
     * It needs to be 'aligned' with a space that the snake's head will occupy, so it needs to be a multiple
     * of (square size + buffer[in snake class])
     * @return random : Location the randomly generated location
     */
    
    public Vector2f generateRandomCoordinate() {
        Vector2f random;
        float x = 0;
        float y = 0;
        int temp;
        int gridX = maxx / (int)(Square.SIZE + snake.BUFFER);     // number of valid x coordinates on the screen
        int gridY = maxy / (int)(Square.SIZE + snake.BUFFER);     // number of valid y coordinates on the screen
        
        // generating random x and y:
        temp = uberDice.nextInt(gridX);
        x = temp * (SQUARE_SIZE + snake.BUFFER);
        temp = uberDice.nextInt(gridY);
        y = temp * (SQUARE_SIZE + snake.BUFFER);
        
        // create and return location
        random = new Vector2f(x, y);
        return random;
    }
    
    
    /**
     * Initializes a new Food
     */
    
    public void initFood() {
        food.setLocation(generateRandomCoordinate());
    }    
        
    
    /**
     * Ends the Game
     */
    public void gameOver(String statement) {
        ((SnakeRunner)game).getGameOverScreen().setStatus(statement);
        ((SnakeRunner)game).getGameOverScreen().setPoints(points);
        
        game.enterState(SnakeRunner.GAMEOVER_ID);
    }

}
    
    
    
    
    
    
    
    
    
    
    
    
    
    //**************************

    
    
    
    
    
    
    
    
    
    
    
    
    
    //**************************
    /*
     * 
     * 
    // Accessors/Mutators
    
    // Constructor: parameters are the max x and y values of the board (pixels)
    public Game(int x, int y, String title) {
        
        super(title);
        
        // display stuff
        maxx = x;
        maxy = y;
        
        //opengl/lwjgl stuff
        initDisplay();
        initOpenGL();
        initFont();
        Keyboard.enableRepeatEvents(false);
        
        // game stuff
        uberDice = new Random();
        snake = new Snake(0, 0);
        food = new Square();
        initFood();
        points = 0;
    }
    
    // Methods
    
    // Manages the game
    public void startGame() {
        while(!Display.isCloseRequested()) {
	    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            
            // game management
            if (count % SPEED == 0) {
                manageInput();
            }
            snake.draw();
            drawPoints();
            growFood();
            handleCollisions();
            if (count % SPEED == 0) {
                snake.move();
            }
            count++;
            
            Display.update();
            Display.sync(FRAME_RATE);
        }
        
        gameOver();
    }
    
    
    
    // Manages the input
    public void manageInput() {
        while(Keyboard.next()) {
            if (Keyboard.getEventKey() == Keyboard.KEY_UP && snake.getDirection() != Direction.DOWN) {
                snake.setDirection(Direction.UP);
            } else if (Keyboard.getEventKey() == Keyboard.KEY_DOWN && snake.getDirection() != Direction.UP) {
                snake.setDirection(Direction.DOWN);
            } else if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && snake.getDirection() != Direction.RIGHT) {
                snake.setDirection(Direction.LEFT);
            } else if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && snake.getDirection() != Direction.LEFT) {
                snake.setDirection(Direction.RIGHT);
            }
        }
//        if (Keyboard.isKeyDown(Keyboard.KEY_UP) && snake.getDirection() != Direction.DOWN) {
//            snake.setDirection(Direction.UP);
//        } else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) && snake.getDirection() != Direction.UP) {
//            snake.setDirection(Direction.DOWN);
//        } else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) && snake.getDirection() != Direction.RIGHT) {
//            snake.setDirection(Direction.LEFT);
//        } else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && snake.getDirection() != Direction.LEFT) {
//            snake.setDirection(Direction.RIGHT);
//        }
    }
    
    public void addPoints(int num) {
        points += num;
    }
    
    public void removePoints(int num) {
        points -= num;
    }
    
    // creates new food
    public void growFood(int x, int y) {
        food.setLocation(x, y);
        growFood();
    }
    public void growFood() {
        food.draw();
    }
    public void growFood(Location loc) {
        food.setLocation(loc);
        growFood();
    }
   
    
    // called when snake touches the edges
    public void gameOver() {
        Display.destroy();
        System.out.println("Game over");
        System.out.println("Points: " + points);
        System.exit(0);
    }
    
    // draws the text on game screen (number of points)
    public void drawPoints() {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        font.drawString((maxx * 6) / 7, maxy / 15, "POINTS: " + points);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }
     
    // initializes opengl
    public void initOpenGL() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glOrtho(0, 1000, 600, 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }
    
    // initializes display
    public void initDisplay() {
        try {
            Display.setDisplayMode(new DisplayMode(maxx, maxy));
            Display.setTitle("Snake Game");
            Display.create();
        } catch (LWJGLException ex) {
            System.out.println("LWJGL Exception Thrown");
            System.exit(1);
        }
    }
    
    // initialize font
    public void initFont() {
        Font awtFont = new Font("Courier New", Font.PLAIN, 20);
        font = new TrueTypeFont(awtFont, true);
        
//        // load font from file
//        try {
//            InputStream inputStream = ResourceLoader.getResourceAsStream("/Users/Zain/NetBeansProjects/Snake/digital7.ttf");
//
//            Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
//            awtFont = awtFont.deriveFont(20f); // set font size
//            font = new TrueTypeFont(awtFont, true);
//
//        } catch (FontFormatException e) {
//            e.printStackTrace();
//        } catch (IOException iox) {
//            iox.printStackTrace();
//        }
    }
}

* */