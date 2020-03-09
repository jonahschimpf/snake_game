import java.awt.Color;
import java.util. *;


public class Snake{
/**
 * 
 * @author Jonah Schimpf
 * 
 * Description: Snake: This models a Snake to be played in Snake
 * 
 * Fields : (SnakePiece) headSnake: The headSnake of the snake
 * 			(SnakePiece) lastSnake: The lastSnake of the snake
 * 
 * Methods://constructor// Snake() - generate a last Snake and head Snake
 * 			TreeSet<Positions> getPositions() - generates a the Positions of each SnakePiece in a 
 * 												Snake. Positions are a custom class I made that 
 * 												essentially model tuples of (int, int)
 * 			addSnakePiece() - a testing functino that add a Snake piece
 * 			addSnakePiece(PowerUp p) - creates a new SnakePiece using the PowerUp p and sets that
 * 									   as the new head, with p's position and the last head's vx/vy
 * 			advance(Direction d) - advances the entire snake  in the direction d of the head given
 * 									the heads current direction.
 *          boolean willHitBoard() - determines if the snake will hit board in the next time step 
 *          boolean hitSnake() - determines if the snake has hit itself. Relies on getPositions() 
 *          					function
 *          int getLength() - returns the length of the string
 *          String toString() - this object toString() function                
 */
	
	private SnakePiece headSnake;
	private SnakePiece lastSnake;

	/**
	 * Snake constructor generate a last Snake and head Snake
	 */
	public Snake() {
		lastSnake = new SnakePiece(null, null);
		headSnake = new SnakePiece(lastSnake, new FoodUp(lastSnake.getPx() + 1, lastSnake.getPy()));
		headSnake.setNext(lastSnake);
		lastSnake.setPrev(headSnake);
	}
    /**
     * Uses a custom class @Positions to get the current positions of the SnakePieces in the snake
     * Intentionally exludes the head.
     * 
     * @return - The TreeSet<Positions> positions of the SnakePieces
     */
    public TreeSet<Positions> getPositions() {
    	TreeSet<Positions> positionSet = new TreeSet<Positions>();
    	SnakePiece s = headSnake.getNext();
    	while (s != null) {
    		int px = s.getPx();
    		int py = s.getPy(); 
    		Positions p = new Positions(px, py);
    		positionSet.add(p);
    		s = s.getNext();
    }
    	return positionSet;
    }
    /**
     * Testing function to quickly add a snake piece without creating a new FoodUp
     */
    public void addSnakePiece() {
    	SnakePiece s = new SnakePiece(null, null);
    	s.setPrev(null);
    	s.setPx(headSnake.getPx() + 1);
    	s.setPy(headSnake.getPy());
    	headSnake.setPrev(s);
    	s.setNext(headSnake);
    	headSnake = s;
    }
    /**
     * Actual function that takes in a food up to generate a new SnakePiece
     * Creates a new SnakePiece to be the headSnake of the snake.
     * 
     * @param foodUp
     */
	public void addSnakePiece(PowerUp foodUp){
		if (foodUp instanceof FoodUp) {
		SnakePiece s = new SnakePiece(headSnake, (FoodUp) foodUp);
		headSnake.setPrev(s);
		s.setNext(headSnake);
		headSnake = s;
		}
		
	}
	/**
	 * Advances all the snakes positions in the direction of the head
	 * @param d- Direction of the head
	 */
	public void advance(Direction d) {
			int lastXPosition = headSnake.getPx();
			int lastYPosition = headSnake.getPy();
			int lastVx = headSnake.getVx();
			int lastVy = headSnake.getVy();
			SnakePiece s = headSnake;
			while (s != null) {
				if (s == headSnake) {
					int newVx;
					int newVy;
					int newPx;
					int newPy;
					//if we want it to go right, then we have to update the head accordingly
					if (d == Direction.RIGHT) {
					newVx = 0;
					newVy = 1;
					newPx = s.getPx() + newVx;
					newPy = s.getPy() + newVy;
					} else if (d == Direction.LEFT) {
						newVx = 0;
						newVy = -1;
						newPx = s.getPx() + newVx;
						newPy = s.getPy() + newVy;
					} else if (d == Direction.UP) {
						newVx = -1;
						newVy = 0;
						newPx = s.getPx() + newVx;
						newPy = s.getPy() + newVy;
					} else { //if its going down
						newVx = 1; 
						newVy =  0;
						newPx = s.getPx() + newVx;
						newPy = s.getPy() + newVy;
					}
					headSnake.setPx(newPx);
					headSnake.setPy(newPy);
					headSnake.setVx(newVx);
					headSnake.setVy(newVy);
					s = s.getNext();
				}//if the new position contains one of the snake pieces it cant advance
				//Log the current velocities and positions
				if (hitSnake()) {
					throw new IllegalArgumentException("The snake has hit itself");
				}
				int currentXPosition = s.getPx();
				int currentYPosition = s.getPy();
				int currentVx = s.getVx();
				int currentVy = s.getVy();
				
				//Set the current positions to the previous snakepiece's positions/velocity
				s.setPx(lastXPosition);
				s.setPy(lastYPosition);
				s.setVx(lastVx);
				s.setVy(lastVy);
				
				//Update the last position/velocity to be what was the current velocities/positions
				lastXPosition = currentXPosition;
				lastYPosition = currentYPosition;
				lastVx = currentVx;
				lastVy = currentVy;
				s = s.getNext();
				}
			}
	/**
	 * Determines if the snake will hit the board in the next time step	
	 * @return - if it will hit the board in the text time step
	 */
	public boolean willHitBoard() {
		int nextHeadRow = headSnake.getPx() + headSnake.getVx();
		int nextHeadCol = headSnake.getPy() + headSnake.getVy();
		return (nextHeadRow > 10 || nextHeadRow < -1 || nextHeadCol > 10 || nextHeadCol < -1);
	}
	/**
	 * Determines if the snake will hit itself in the next time step
	 * @return if the snake will hit itself in the next time step
	 */
	public boolean hitSnake() {
	//	int nextHeadRow = headSnake.getPx() + headSnake.getVx();
		//int nextHeadCol = headSnake.getPy() + headSnake.getVy();
		Positions headPositions = new Positions(headSnake.getPx(), headSnake.getPy()); 
			
		TreeSet<Positions> currentSnakePositions = getPositions(); 
		currentSnakePositions.add(new Positions (lastSnake.getPx(), lastSnake.getPy()));
	//	currentSnakePositions.remove(new Positions(headSnake.getPx(), headSnake.getPy()));
		return (currentSnakePositions.contains(headPositions));
	}
	
	public SnakePiece getLastSnake() {
		return lastSnake;
	}
	public SnakePiece getHeadSnake() {
		return headSnake;
	}
	public void setHeadSnake(SnakePiece head) {
		headSnake = head;
	}
	public void setLastSnake(SnakePiece last) {
		lastSnake = last;
	}
	/**
	 * Gets the current length of the snake
	 * @return - int length of the snake
	 */
	public int getLength() {
		SnakePiece s = headSnake;
		int length = 0;
		while(s != null) {
			length++;;
			s = s.getNext();
		}
		return length;
	}
	/**
	 * This object toString() function
	 */
	@Override
	public String toString() {
		SnakePiece s = headSnake;
		String str = "";
		while (s != null) {
			if (s == headSnake) {
				str += "head-> ";
			} else if (s == lastSnake) {
				str += "tail";
			} else {
				str += "piece-> ";
			}
			s = s.getNext();
		}
		return str;
	}
}
