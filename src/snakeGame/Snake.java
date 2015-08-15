package snakeGame;

/**
 * Snake Class: The snake
 * Created- 18/07/2015
 */

import java.util.*;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

/**
 *
 * @author Zain
 */
public class Snake {
    // Variables
    private int numSegments;
    private ArrayList segments;
    private Rectangle head;
    private Rectangle tail;
    private Direction dir;
//    private final double SHIFT = 2;
    public final float BUFFER = 3;
    private Vector2f location;
    private final int DEFAULT_START_SIZE = 5;
    private final int SQUARE_SIZE = 10;
    
    // Accessors and Mutators
    public Direction getDirection() {
        return dir;
    }
    public Vector2f getLocation() {
        return location;
    }
    public Rectangle[] getSegments() {
        Rectangle[] arr = new Rectangle[numSegments];
        for (int i = 0; i < numSegments; i++) {
            arr[i] = (Rectangle)segments.get(i);
        }       
        return arr;
    }
    
    public void setDirection(Direction direction) {
        dir = direction;
    }
    
    // Constructor:
    // Creates snake at location in parameters
    public Snake(float x, float y) {
        numSegments = 1;
        segments = new ArrayList(numSegments);
        segments.add(new Rectangle(x, y, SQUARE_SIZE, SQUARE_SIZE));
        head = (Rectangle)segments.get(0);
        tail = (Rectangle)segments.get(segments.size() - 1);
        dir = Direction.RIGHT;
        updateLocation();
        initSnakeSegments(DEFAULT_START_SIZE - 1);
    }
    
    
    // Methods
    
    // updates the location of the snake with the location of the head (first element in the arraylist)
    public void updateLocation() {
        location = head.getLocation();
    }
    
    // initializes the snake with the number of EXTRA segments specified -> method is only for when snake starts with more than one square
    public void initSnakeSegments(int segments) {
        for (int i = 0; i < segments; i++) {
            addSegment();
        }
    }
    
    
    /** 
     * Moves the last segment of the snake to the front to move the snake forward.
     */
    
    public void move() {
        if (dir == Direction.UP) {
            tail.setY(location.getY() - SQUARE_SIZE - BUFFER);
            tail.setX(location.getX());
        } else if (dir == Direction.DOWN) {
            tail.setY(location.getY() + SQUARE_SIZE + BUFFER);
            tail.setX(location.getX());
        } else if (dir == Direction.LEFT) {
            tail.setX(location.getX() - SQUARE_SIZE - BUFFER);
            tail.setY(location.getY());
        } else if (dir == Direction.RIGHT) {
            tail.setX(location.getX() + SQUARE_SIZE + BUFFER);
            tail.setY(location.getY());
        }

        // changing the position of the head and tail in the array to match the layout on screen
        // Shifting all the elements of the array up, placing tail at the front (to mirror what happens on screen)
        // first keep in mind that the last element in the array is stored in a var called 'tail'
        if (numSegments > 1) {
            for (int i = numSegments - 2; i >= 0; i--) {
                segments.set(i + 1, segments.get(i));
            }
            segments.set(0, tail);
        }
        
        // updating the head and tail values        
        head = (Rectangle)segments.get(0);
        tail = (Rectangle)segments.get(numSegments - 1);
        
        // keeping this class's location up to date with the head
        updateLocation();
    }
    
    
    /** 
     * Adds a Segment to the snake. Called when food is eaten.
     */
    
    public void addSegment() {
        Vector2f endLocation = tail.getLocation();
        
        if (dir == Direction.UP) {
            segments.add(new Rectangle(endLocation.getX(), endLocation.getY() - SQUARE_SIZE - BUFFER, SQUARE_SIZE, SQUARE_SIZE));
        } else if (dir == Direction.DOWN) {
            segments.add(new Rectangle(endLocation.getX(), endLocation.getY() + SQUARE_SIZE + BUFFER, SQUARE_SIZE, SQUARE_SIZE));
        } else if (dir == Direction.LEFT) {
            segments.add(new Rectangle(endLocation.getX() + SQUARE_SIZE + BUFFER, endLocation.getY(), SQUARE_SIZE, SQUARE_SIZE));
        } else if (dir == Direction.RIGHT) {
            segments.add(new Rectangle(endLocation.getX() - SQUARE_SIZE - BUFFER, endLocation.getY(), SQUARE_SIZE, SQUARE_SIZE));
        }
        
        numSegments ++;
        tail = (Rectangle)segments.get(numSegments - 1);
    }
    
    
    /**
     * Draws the entire snake.
     * @param g The Graphics to use when drawing
     */
    
    public void draw(Graphics g) {        
        for (int i = 0; i < numSegments; i++) {
            g.fill((Rectangle)segments.get(i));
        }
    }
    
}
