import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
/**
 * 
 * @author Jonah Schimpf
 *
 * Description: This models the board in which Snake is played
 * 
 * Fields: JLabel status - the status label of the game
 * 			GameObj[][] boardArray- the actual array wherein objects are store
 * 			Snake snake - the snake that is being used in the game
 * 			FoodUp foodUp - the foodUp that is on screen at the time
 * 			DeathUp deathU- the deathUp that is on screen at the time
 * 			WinUp winUp - the winUp that is onboa screen at the time
 * 			SnakePiece snakeHead - the head of our snake at the time
 * 			final int INTERVAL- the interval, speed essentially, of the snake game
 * 			boolean playing- the boolean if we are currently in a game
 * 			private int userScore - the currentScore of the game
 * Methods: //constructor// Takes in the JLabel, creates a timer, then adds an action listener to it
 * 						This action listener sets the head of the snake's velocity (effectively 
 * 						changing the snake's velocity) to 1 or -1 depending on the key pressed
 * 			putRandomInBoard - pseduo randomly puts an object into the board into 
 * 								a spot that is not already occupied
 * 			putSnakeInBoard - simply places the snake into the board based on each pieces position
 * 			advanceBoard - just advances the snake
 * 			draw - iterates throughout the array and draws anything not null
 * 			clearBoardOfSnake- iterates through array, replace all snake pieces with null
 * 			clearObj- replace the current obj's position with null
 * 			reset() - implements starting position of the game, see comments for more details
 * 			tick() - used by Game implements Runnable to run the game.
 * 			containsWinUp- checks if the baord has a PowerUp WinUp
 * 			containsDeathUp - checks if the board has a PowerUp DeathUp
 * 			checkIfFoodUp - checks if the board has a PowerUp FoodUp
 * 			
 */

@SuppressWarnings("serial")
public class BoardArray extends JPanel{
    private JLabel status;
	private GameObj[][] boardArray;
	private Snake snake;
	private FoodUp foodUp;
	private DeathUp deathUp;
	private WinUp winUp;
	private SnakePiece snakeHead;
	private final int INTERVAL = 300;
	private boolean playing;
	private int userScore;

	public BoardArray(JLabel status) {
		this.status = status;
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		//Its not playing
		playing = false;
		//Create the Board
		boardArray = new GameObj[10][10];
		//Create our timer
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start(); // MAKE SURE TO START THE TIMER!
        setFocusable(true);
        //The velocity should change based on the key pressed
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                	snakeHead.setVx(0);
                	snakeHead.setVy(-1);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                	snakeHead.setVx(0);
                	snakeHead.setVy(1);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                	snakeHead.setVx(1);
                	snakeHead.setVy(0);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                	snakeHead.setVx(-1);
                	snakeHead.setVy(0);
                }
            }
        });
	}
	/**
	 * pseduo randomly puts an object into the board into 
	 * a spot that is not already occupied
	 * 
	 * @param intoTheBoard- the GameObj you want to randomly be placed into the board
	 */
	public void putRandomInBoard(GameObj intoTheBoard) {
		//if its null, something wrong has happened
		if (intoTheBoard == null) {throw new RuntimeException("BoardArray: Null into Board");}
		//Otherwise get the positions of IntoTheBoard and put it there
		
		//A boolean that tells us if this hasn't happened
		boolean notPlaced = true;
		
		//If its called to many times, prioritize the null value of the array, instead of random
		int called = 0;
		while(notPlaced) {
		for (int i = 0; i < 10; i++) {
			if (!notPlaced) {
				break;
			}
			for (int j = 0; j < 10; j++) {
				//Create a "random variable" that will be used to generate randomness
				int random = (int) (100 * Math.random());
				called++;
				if (called > 500) {
					random = 96;
				}
				if (boardArray[i][j] == null && random > 95) {
					notPlaced = false;
					intoTheBoard.setPx(i);
					intoTheBoard.setPy(j);
					boardArray[i][j] = intoTheBoard;
					break;
				} 
			}
		}
	}		
}
	/**
	 * Puts the snake into the board
	 * @param s - the snake you want into the board
	 */
	public void putSnakeInBoard(Snake s) {
		clearBoardOfSnake();
		snake = s;
		snakeHead = s.getHeadSnake();
		SnakePiece snakePiece = snakeHead;
		while (snakePiece != null) {
			int px = snakePiece.getPx();
			int py = snakePiece.getPy();
			boardArray[px][py] = snakePiece;
			snakePiece = snakePiece.getNext();
		}
	}
	/**
	 * Iterates through the board, draws everything that isn't null
	 * @param g- the graphic context
	 */
	public void draw(Graphics g) {
		 for (int row = 0; row < 10; row++) {
			    for (int col = 0; col < 10; col++) {
			    	if (boardArray[col][row] != null) {	
			    		boardArray[col][row].draw(g);
			    }
			 }
		 }
	}
	/**
	 * calls advance snake
	 * @param d- the direction you want the snake to advance
	 */
	public void advanceBoard(Direction d) {
		snake.advance(d);
	}
	/**
	 * Sets every element in the array to null, "clearing" the board
	 */
	public void clearBoardOfSnake() {
		 for (int row = 0; row < boardArray.length; row++) {
			    for (int col = 0; col < boardArray[row].length; col++) {
			    	if (boardArray[row][col] instanceof SnakePiece) {
			       boardArray[row][col] = null;
		
			    }
			 }
		 }
	}
	/**
	 * Removes the object from the board
	 * @param px - the objects row position
	 * @param py - the objects col position
	 */
	public void clearObj(int px, int py) {
	    boardArray[px][py] = null;
	}
	/**
	 * Sets the game to its intial values
	 */
	public void reset() {
		 for (int row = 0; row < 10; row++) {
			    for (int col = 0; col < 10; col++) {
			       boardArray[row][col] = null;
			    }
			 }
		 //Create a new snake and put it into the board
		 Snake startingSnake = new Snake();
		 snake = startingSnake;
	//	snake.addSnakePiece();
		snakeHead =  snake.getHeadSnake() ;
		 putSnakeInBoard(snake);
		 
		 //make sure its playing
		 playing = true;
		 
		 //create a food up and put it into the board
		 foodUp = new FoodUp();
		 this.putRandomInBoard(foodUp);
		 
		 //create a Deathup to be used for later
		 deathUp = new DeathUp();
	     requestFocusInWindow();
	     
	     //create a WinUp to be used for later
	     winUp = new WinUp();
	     
	     //the panel should say "running"
	     status.setText("Playing: SNAKE!");
	     
	     //The score is 0
	     userScore = 0;
	}
	/**
	 * If the score is 98 (100 possible squares, start with two snake pieces), you have won
	 * @return If you have woon
	 */
	public boolean checkIfWon() {
		if (userScore == 98) {
			return true;
		} return false;
	}
	/**
	 * The method called by game which actually plays the game
	 */
	void tick() {
        if (playing) { try {
        //If there's not a FoodUp, put one in there
        if (foodUp.checkReady(boardArray, snake)) {
        	FoodUp newFoodUp = new FoodUp();
        	foodUp = newFoodUp;
        	putRandomInBoard(newFoodUp);
        }
        //check if the death up is ready
        if (deathUp.checkReady(boardArray, snake) && !containsDeathUp(boardArray)) {
        	if (deathUp.getPx() != -1) {
        	deathUp = null;
        	}
        	DeathUp newDeathUp = new DeathUp();
        	deathUp = newDeathUp;
        	putRandomInBoard(deathUp);
        } else {
        	if (deathUp.getPx() != -1) {
        	this.clearObj(deathUp.getPx(), deathUp.getPy());
        	deathUp.setPx(-1);
        	}
        }
        //check if win up is ready
        if (winUp.checkReady(boardArray, snake) && !containsDeathUp(boardArray)) {
        	if (winUp.getPx() != -1) {
        	winUp = null;
        	}
        	WinUp newWinUp = new WinUp();
        	winUp = newWinUp;
        	putRandomInBoard(winUp);
        } else {
        	if (winUp.getPx() != -1) {
        	this.clearObj(winUp.getPx(), winUp.getPy());
        	winUp.setPx(-1);
        	}
        }
        
        
        if (snake.willHitBoard() || snake.hitSnake()) {
      	  playing = false;
          status.setText("You lose!");
        }	
        if (checkIfWon()) {
        	playing = false;
        	status.setText("You Win");
        }
        
        	//check if it will intersect deathUp
        	if (snakeHead.willIntersect(deathUp)) {
        		snake = deathUp.effect(snake, this);
        	}
        	//check if it will intersectWinUp
        	if (snakeHead.willIntersect(winUp)) {
        		snake = winUp.effect(snake, this);
        	}
        	
        	//check if it intersects foodUp
        	if (snakeHead.willIntersect(foodUp)) {
        		userScore++;
        		snake = foodUp.effect(snake, this);
        		putSnakeInBoard(snake);
        	} else {
        	advanceBoard(snakeHead.getDirection());
        	putSnakeInBoard(snake);
        	}
        } catch (Exception e) {
        	System.out.print(e);
        	 playing = false;
             status.setText("You lose!");
        }
        	repaint();
        } 
	}	
    /**
     * Objects toString() function
     */
	public String toString() {
		String s = "";
		for(int i = 9; i >= 0; i--) 
		{
		    for(int j = 0; j < 10; j++)
		    {
		        if ((boardArray[i][j]) != null) {
		        	s += (" " + boardArray[j][i].toString()) + " "; 
		        }
		        else {
		        	s += (" 0 ");
		        }
		    }
		    s += "\n";
		}
		  return s;
	}
	/**
	 * Checks if there is food on the board
	 * @return if there is food on the board
	 */
	public boolean checkIfFoodUp() {
		 for (int row = 0; row < 10; row++) {
			    for (int col = 0; col < 10; col++) {
			    	if (boardArray[row][col] instanceof FoodUp) {
			    		return true;
			    		}
			    	}
			    }
		 return false;
	}
	 @Override
	    public void paintComponent(Graphics g) {
		 g.setColor(Color.black);
	        super.paintComponent(g);
	        this.draw(g);
	        g.setColor(Color.black);
	        g.draw3DRect(300, 0, 100, 300, false);
	        g.drawString("SCORES", 325, 25);
	        g.drawString(("" + userScore), 325, 50);
	 }
	    @Override
	    public Dimension getPreferredSize() {
	        return new Dimension(400, 300);
	    }
	    
	    public int getScore() {
	    	return userScore;
	    }
	    public void setScore(int score) {
	    	userScore = score;
	    }
	    /**
	     * Checks if the board has a death up
	     * @param board
	     * @return if the board has a death up
	     */
	    public boolean containsDeathUp(GameObj[][] board) {
	    	 for (int row = 0; row < board.length; row++) {
				    for (int col = 0; col < board[row].length; col++) {
				    	if (board[row][col] instanceof DeathUp) {
				    		return true;}
				    }
	    }
	    	 return false;
	    }
	    /**
	     * Checks if the board has a winup
	     * @param board
	     * @return if the board has a winup
	     */
	    public boolean containsWinUp(GameObj[][] board) {
	    	 for (int row = 0; row < board.length; row++) {
				    for (int col = 0; col < board[row].length; col++) {
				    	if (board[row][col] instanceof DeathUp) {
				    		return true;}
				    }
	    }
	    return false;
	    }
	    /**
	     * Places a game object, s, into the board
	     * @param s
	     */
		public void intoTheBoard(GameObj s) {
			boardArray[s.getPx()][s.getPy()] = s;
		}
	    public Snake getSnake() {
	    	return snake;
	    }
	    public FoodUp getFoodUp() {
	    	return foodUp;
	    }
	    public WinUp getWinUp() {
	    	return winUp;
	    }
	    public DeathUp getDeathUp() {
	    	return deathUp;
	    }
	    public boolean isPlaying() {
	    	return playing;
	    }
    public static void main(String[] args) {
    	}
}
