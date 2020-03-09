import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util. *;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * 
 * @author Jonah Schimpf
 *	
 * Description: SnakePiece: This models the pieces a snake is made up of in Snake
 * 
 * Fields : (SnakePiece) nextSnakePiece -This is the SnakePiece behind a given snake piece. 
 * 										  For the tail piece, this will be null
 * 			(SnakePiece) prevSnakePiece - This is the SnakePiece before a given snake piece.
 * 										 For the head piece, this will be null
 * Methods://constructor// SnakePiece(SnakePiece, FoodUp) - 
 * 										  Takes in the currentHead of a snake. And its 
 * 										  food used to generate the next position.
 * 			void setPrev(SnakePiece) - setter for prevSnakePiece
 * 			SnakePiece getPrev() - getter for prevSnakePiece
 * 			void setNext(SnakePiece) - setter for nextSnakePiece
 *          SnakePiece getNext() - getter for nextSnakePiece
 *          boolean willIntersect(GameObj) - determines if the SnakePiece will intersect a given	
 *          							game object
 */
public class SnakePiece extends GameObj implements GameObject {
	//This is the SnakePiece behind a given snake piece. For the tail piece, this will be null
	private SnakePiece nextSnakePiece;
	//This is the SnakePiece before a given snake piece. For the head piece, this will be null
	private SnakePiece prevSnakePiece;
	//The final size of all SnakePieces
    private static final int SIZE = 20;
    //The initial position of the head snake piece
    private static final int INIT_POS_X = 4;
    private static final int INIT_POS_Y = 4;
    //The initial velocity of the snake pieces
    private static final int INIT_VEL_X = 0;
    private static final int INIT_VEL_Y = 0; 
    private Color color;
    //Snakehead image
    public static final String IMG_FILE = "files/snakeHead.png";
    private static BufferedImage img;
    /**
     * The SnakePiece constructor.
     * 
     * @param currentHead - the currentHead of the snake. Will be replaced by this new piece
     * @param powerUp- uses this to find the positions of the new SnakePiece head
     */
    public SnakePiece(SnakePiece currentHead, FoodUp powerUp) {
        super( INIT_POS_X, INIT_POS_Y, INIT_VEL_X, INIT_VEL_Y, SIZE, SIZE);
        this.setVx(1);
        //if the snake has already been instantiated
        if (currentHead != null) {
        	this.prevSnakePiece = null;
        	this.nextSnakePiece = currentHead;
        	currentHead.prevSnakePiece = this;
           	this.setVx(currentHead.getVx());
        	this.setVy(currentHead.getVy());
        	this.setPx(powerUp.getPx());
        	this.setPy(powerUp.getPy());
        } else {
        	try {
        		   if (img == null) {
                       img = ImageIO.read(new File(IMG_FILE));
                   }
               } catch (IOException e) {
                   System.out.println("Internal Error:" + e.getMessage());
               }
        	}
        }
      
     // getter for prevSnakePiece
    public SnakePiece getPrev(){
    	return (this.prevSnakePiece);
    }
    //setter for the previous snake
    public void setPrev(SnakePiece sp) {
    	this.prevSnakePiece = sp;
    }
    
    public SnakePiece getNext() {
    	return (this.nextSnakePiece);
    }
    public void setNext(SnakePiece sn) {
    	this.nextSnakePiece = sn;
    }
   /**
    * This objects draw function. Takes in an image :^)
    * @param Graphics g - the graphic context
    */
    @Override
    public void draw(Graphics g) {
     	int modifiedCoordX = 30 * (getPx());
    	int modifiedCoordY = 30 * (getPy());
    	if (this.prevSnakePiece == null) {
    	 g.drawImage(img, modifiedCoordY, modifiedCoordX, this.getWidth(), this.getHeight(), null);
    
    	} else {
        	this.color = Color.green;
        	g.setColor(this.color);
            g.fillOval(modifiedCoordY, modifiedCoordX, this.getWidth(), this.getHeight());
    	}
       
    }
    @Override
    public String toString() {
    	return "X";
    }
    /**
     * Will it intersect a game obj? This can be either another SnakePiece or PowerUp. This is based
     * of the current position and velocity of the snakehead
     * @param that, GameObj that you might intersect
     * @return a boolean if you will hit that obj
     */
    public boolean willIntersect(GameObj that) {
    	if (that == null) {
    		return false;
    	}
    	int thatRow = that.getPx();
    	int thatCol = that.getPy();
    	int nextThisRow = getPx() + getVx();
    	int nextThisCol = getPy() + getVy();
        return (nextThisRow == thatRow && nextThisCol == thatCol);
        
    }

    public static void main(String[] args) {
    }

    
}
