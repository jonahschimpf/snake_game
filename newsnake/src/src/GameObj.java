/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.Graphics;

/** 
 * An object in the game. 
 *
 * Game objects exist in the game court. They have a position, velocity, size. Their
 * velocity controls how they move; their position should always be within the array.
 */
public abstract class GameObj {
    /*
     * Current position of the object (in terms of graphics coordinates)
     *  
     * Coordinates are given by the CURRENT CELL of the object. This position should
     * always be within bounds.
     *  0 <= px <= 9
     *  0 <= py <= 9 
     */
    private int px; 
    private int py;

    /* Size of object, in pixels. */
    private int width;
    private int height;

    /* Velocity: number of cells to move every time move() is called. */
    private int vx;
    private int vy;

    /**
     * Constructor
     */
    public GameObj(int px, int py, int vx, int vy, int width, int height) {
        this.vx = vx;
        this.vy = vy;
        this.px = px;
        this.py = py;
        this.width  = width;
        this.height = height;
    }

    /*** GETTERS **********************************************************************************/
    public int getPx() {
        return this.px;
    }

    public int getPy() {
        return this.py;
    }
    
    public int getVx() {
        return this.vx;
    }
    
    public int getVy() {
        return this.vy;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    /*** SETTERS **********************************************************************************/
    public void setPx(int px) {
        this.px = px;
    }

    public int getHeight() {
	    return this.height;
	}

	public void setPy(int py) {
        this.py = py;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }
    /**
     * Determines the current direction of a game up by comparing its velocity to its current 
     * position. Will only be used for SnakePiece
     * 
     * 
     * @param void
     * @return The Direction d it is currently going. Throws a RuneTimeException if no direction
     * matches.
     */
    Direction getDirection() {
    	if (!((this.vx == 0 && this.vy != 0) || (this.vx != 0 && this.vy == 0))) {
    		throw new RuntimeException("GameObj: Velocity Invariant Broken");
    	}
    	int thisNextX = this.px +this.vx;
    	int thisNextY = this.py + this.vy;
    	if (thisNextY > py) {
    		return Direction.RIGHT;
    	}
    	if (thisNextX > px) {
    		return Direction.DOWN;
    	}
    	if (px > thisNextX) {
    		return Direction.UP;
    	}
    	if (py > thisNextY ) {
    		return Direction.LEFT;
    	} else {
    		throw new RuntimeException("GameObj: Velocity Invariant Broken");
    	}
    }
    /**
     * Default draw method that provides how the object should be drawn in the GUI. This method does
     * not draw anything. Subclass should override this method based on how their object should
     * appear.
     * 
     * @param g The <code>Graphics</code> context used for drawing the object. Remember graphics
     * contexts that we used in OCaml, it gives the context in which the object should be drawn (a
     * canvas, a frame, etc.)
     */
    public abstract void draw(Graphics g);
    public abstract String toString();
}