import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import javax.swing.JLabel;

import org.junit.Test;

/** 
 *  You can use this file (and others) to test your
 *  implementation.
 */

public class GameTest {
	/**This is the tests for snakePiece**/
	/*******************************************/
	
	/**
	 * This sets test the constructor of a snake piece that starts with a head
	 * current head. Its basically the constructor for the headPiece
	 */
	@Test
    public void testConstructorHead() {
      SnakePiece tail = new SnakePiece(null, null);
      //Shouldn't have a next pointer
      assertEquals(tail.getNext(), null);
      //Should't have a prev pointer
      assertEquals(tail.getPrev(), null);
      //SHOULD have row velocity
      assertEquals(tail.getVx(), 1);
      //Now we create the head, we need a FoodUp for this
      FoodUp food = new FoodUp();
      SnakePiece head = new SnakePiece(tail, food);
      //Now, we should be able to get to the tail from the head
      assertEquals(head.getNext(), tail);
      //And vice versa
      assertEquals(tail.getPrev(), head);
      //And we shouldn't have any next/prev pointers for tail/head
      assertEquals(tail.getNext(), null);
      assertEquals(head.getPrev(), null);
      //The velocity of the head of the snake should be the velocity of the tail
      assertEquals(head.getVx(), 1);
      //Now lets make this more complicated with a triple snake
      SnakePiece newHead = new SnakePiece(head, food);
      // The snake now looks like newHead -> head -> tail, lets test that
      assertEquals(newHead.getPrev(), null);
      assertEquals(newHead.getNext(), head);
      assertEquals(head.getPrev(), newHead);
      assertEquals(head.getNext(), tail);
      //tail shouldn't have changed at all
      assertEquals(tail.getNext(), null);
    }
	/**
	 * This tests the willIntersect(GameObj function), this should
	 * ONLY return true if it will intersect in the next time step
	 */
	@Test
    public void willIntersect() {
    	SnakePiece s = new SnakePiece(null, null);
    	//Set the snake to have position (1,2) and velocity (3, 0)
    	s.setPx(1);
    	s.setPy(2);
    	s.setVx(3);
    	s.setVy(0);
    	//This should intersect an object at (4, 2)
    	FoodUp f = new FoodUp();
    	f.setPx(4);
    	f.setPy(2);
    	assertTrue(s.willIntersect(f));
    	//Now it shouldn't intersect if its at the same spot
    	f.setPx(1);
    	f.setPy(2);
    	assertFalse(s.willIntersect(f));
    }
	/**
	 * This tests the getDirection() function found in gameObject
	 */
	@Test (expected = RuntimeException.class)
	public void getDirection() {
		SnakePiece s = new SnakePiece(null, null);
    	//Set the snake to have position (1,2) and velocity (3, 0)
    	s.setPx(1);
    	s.setPy(2);
    	s.setVx(3);
    	s.setVy(0);
    	//You should read Vx as ROW velocity, so up and down
    	//You should read Vy as Column velocity, so left and right
    	assertEquals(Direction.DOWN, s.getDirection());
    	s.setVx(-1);
    	assertEquals(Direction.UP, s.getDirection());
    	s.setVx(0);
    	s.setVy(1);
    	assertEquals(Direction.RIGHT, s.getDirection());
    	s.setVy(-1);
    	assertEquals(Direction.LEFT, s.getDirection());
    	//In an array, a SnakePiece should only have either Vx or Vy, otherwise throw
    	s.setVx(12);
    	s.getDirection();
	}
	/**This test Snake and its respective functions **/
	/*************************************************/
	
	/**
	 * This tests the Snake constructor. When called it creates a snake with two SnakePiece
	 */
	@Test
	public void testSnakeConstructor() {
		Snake tSnake = new Snake();
		SnakePiece head = tSnake.getHeadSnake();
		SnakePiece tail = tSnake.getLastSnake();
		//Head should next to tail, tail should prev to head
		assertEquals(head.getNext(), tail);
		assertEquals(tail.getPrev(), head);
		//I also have a two string function I would like to test
		assertEquals("head-> tail", tSnake.toString());
		//Testing the length function
		assertEquals(2, tSnake.getLength());
	}
	/**
	 * This tests the Snake constructor for more complicated Snakes
	 */
	@Test
	public void testComplicatedSnakeConstructor() {
		Snake tSnake = new Snake();
		SnakePiece head = tSnake.getHeadSnake();
		SnakePiece tail = tSnake.getLastSnake();
		//Normally you need a FoodPiece, for simplicity I have a testing function that does that
		tSnake.addSnakePiece();
		//Now the head shouldn't be the head
		assertFalse(head.equals(tSnake.getHeadSnake()));
		//Nothing should have happend to the tail
		assertEquals(tail, tSnake.getLastSnake());
		//Should be able to get to the tail from newHead
		SnakePiece newHead = tSnake.getHeadSnake();
		assertEquals(newHead.getNext(), head);
		assertEquals(head.getNext(), tail);
		assertEquals(3, tSnake.getLength());
		//To string test
		assertEquals("head-> piece-> tail", tSnake.toString());
	}
	/**
	 * This tests the advance function of this Snake
	 */
	@Test
	public void testAdvanceSnake() {
		Snake tSnake = new Snake();
		tSnake.addSnakePiece();
		SnakePiece head = tSnake.getHeadSnake();
		SnakePiece mid = head.getNext();
		SnakePiece tail = tSnake.getLastSnake();
		head.setVx(0);
		head.setVy(1);
		//The head is now going to right
		head.setPx(5);
		head.setPy(5);
		mid.setPx(5);
		mid.setPy(4);
		tail.setPx(5);
		tail.setPy(3);
		//This is a snake that looks like this:(5,3)-(5,4)-(5,5)~
		tSnake.advance(head.getDirection());
		//Snake should now have (5,4)-(5,5)-(5,6)
		assertEquals(6, head.getPy());
		assertEquals(5, mid.getPy());
		assertEquals(4, tail.getPy());
		head.setVx(-1);
		head.setVy(0);
		//The snake is now going up and looks like (5,4)-(5,5)-(5,6)~
		tSnake.advance(head.getDirection());
		assertEquals(4, head.getPx());
		assertEquals(5, mid.getPx());
		assertEquals(5, tail.getPx());
	}
	/**
	 * This is the test for willHitBoard
	 */
	@Test
	public void willHitBoard() {
		Snake tSnake = new Snake();
		SnakePiece head = tSnake.getHeadSnake();
		head.setPx(0);
		head.setVx(-2);
		assertTrue(tSnake.willHitBoard());
		head.setPx(10);
		head.setVx(2);
		assertTrue(tSnake.willHitBoard());
	}
	/**
	 * This is the test for hitSelf. Advance throws, a designed IllegaleArgumentException when this
	 * happens
	 */
	@Test (expected = IllegalArgumentException.class)
	public void hitSnake() {
		Snake tSnake = new Snake();
		tSnake.addSnakePiece();
		SnakePiece head = tSnake.getHeadSnake();
		SnakePiece mid = head.getNext();
		SnakePiece tail = tSnake.getLastSnake();
		head.setVx(0);
		head.setVy(-1);
		//The head is now going to left
		head.setPx(5);
		head.setPy(5);
		mid.setPx(5);
		mid.setPy(4);
		tail.setPx(5);
		tail.setPy(3);
		//This is a snake that looks like this:(5,3)-(5,4)-(5,5)~
		//It hasn't hit itself yet
		assertFalse(tSnake.hitSnake());
		tSnake.advance(head.getDirection());
		//Now the snake has move onto itself
		assertTrue(tSnake.hitSnake());
		//Because the length of the snake is 3, it should hit itself on next advance too
		tSnake.advance(head.getDirection());
		assertTrue(tSnake.hitSnake());
	}
	/**This test the core game state basically, or BoardArray***/
	@Test
	public void gameState() {
		BoardArray board = new BoardArray(new JLabel());
		//Calling reset instantiates the game
		board.reset();
		Snake snake = board.getSnake();
		//Remember we intentionally exclued the head, so the size should be 1
		assertEquals(1, snake.getPositions().size());
		//The snake should have length two
		assertEquals(2, snake.getLength());
		//By default, the snake looks like this (4,4)-(5,4)~
		//Put a food up in front of it and see what happens
		FoodUp f = board.getFoodUp();
		f.setPx(6);
		f.setPy(4);
		board.intoTheBoard(f);
		board.tick();
		assertEquals(3, snake.getLength());
		assertEquals(1, board.getScore());
		assertEquals(2, snake.getPositions().size());
	}
	@Test
	public void gameOver() {
			BoardArray board = new BoardArray(new JLabel());
			//Calling reset instantiates the game
			board.reset();
			// remember the snake starts off so advance call should make it game over
			assertTrue(board.isPlaying());
			board.advanceBoard(Direction.DOWN);
			board.advanceBoard(Direction.DOWN);
			board.advanceBoard(Direction.DOWN);
			board.advanceBoard(Direction.DOWN);
			board.advanceBoard(Direction.DOWN);
			board.advanceBoard(Direction.DOWN);
			board.advanceBoard(Direction.DOWN);
			board.tick();
			assertFalse(board.isPlaying());
			//If you run into a death up you lose
			BoardArray board2 = new BoardArray(new JLabel());
			board2.reset();
			assertTrue(board2.isPlaying());
			DeathUp DeathUp = board.getDeathUp();
			DeathUp.setPx(6);
			DeathUp.setPy(4);
			board.intoTheBoard(DeathUp);
			board.advanceBoard(Direction.DOWN);
			board.tick();
			assertFalse(board.isPlaying());
	}
	@Test
	public void winGame() {
		BoardArray board = new BoardArray(new JLabel());
		board.reset();
		assertTrue(board.isPlaying());
		board.setScore(98); //equivalent to a has won function
		board.tick();
		assertFalse(board.isPlaying());
		//another way to win the game, besides a score of 98
		BoardArray board2 = new BoardArray(new JLabel());
		board2.reset();
		assertTrue(board2.isPlaying());
		WinUp winUp = board.getWinUp();
		winUp.setPx(6);
		winUp.setPy(4);
		board.intoTheBoard(winUp);
		board.advanceBoard(Direction.DOWN);
		board.tick();
		assertFalse(board.isPlaying());
	}
	@Test
	/**
	 * This is a really vital function, so I figured I should test it as well
	 */
	public void testingRandomIntoBoard() {
		BoardArray board = new BoardArray(new JLabel());
		board.reset();
		assertTrue(board.isPlaying());
		FoodUp foodUp = board.getFoodUp();
		//Lets set the px and py and makes sure they change
		foodUp.setPx(8);
		foodUp.setPy(20);
		board.putRandomInBoard(foodUp);
		assertFalse(foodUp.getPx() == 8 && foodUp.getPy() == 20);
	}
}























