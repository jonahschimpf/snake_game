import java.awt.Color;
import java.awt.Graphics;
/**
 * 
 * @author Jonah Schimpf
 * 
 *	Description: Models a deathUp in Snake. If you eat this, you lose!
 *
 *	Fields- position, velocity , size
 *	Methods - effect(Snake, BoardArray) - kills the snake
 *			- checkready(GameObj[][], Snake) - check if the board is ready for a deathUp
 *			- draw() - object draw method
 */
public class DeathUp extends GameObj implements PowerUp {
	private static final int P = -1;
	private static final int VX = 0;
	private static final int VY = 0;
	private static final int SIZE = 20;
	public DeathUp() {
		super(P, P, VX, VY, SIZE, SIZE);
	}
	public DeathUp(int px, int py) {
		super(px, py, VX, VY, SIZE, SIZE);
	}
	/**
	 * If you eat this, you lose! Effect places the head of the snake out of bounds
	 * ending the game
	 * 
	 * @param Snake, BoardArray
	 * @return Snake - the snake you have modified 
	 */
	@Override
	public Snake effect(Snake snake, BoardArray b) {
		SnakePiece head = snake.getHeadSnake();
		head.setPx(10000000);
		return snake;
	}
	/**
	 * We only want the death up to show up in certain levels, 4, 42, 80
	 * 
	 * @param board Snake
	 * @return boolean if its ready
	 */
	@Override
	public boolean checkReady(GameObj[][] board, Snake snake) {
		//only generate for certain "levels" of the snake 8, 42, 80
		int length = snake.getLength();
		if (length == 4 || length == 42 || length == 80) {
			for (int i = 0; i < 10; i++) { 
				for (int j = 0; j < 10; j++) {
					//if a FoodUp already exist
					if (board[i][j] instanceof DeathUp) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.black);
		int modifiedCoordX = 30 * (getPx());
    	int modifiedCoordY = 30 * (getPy());
    	g.fill3DRect(modifiedCoordY, modifiedCoordX, SIZE, SIZE, true);
		
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
}
