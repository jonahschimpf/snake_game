import java.awt.Color;
import java.awt.Graphics;

/**
 * 
 * @author Jonah Schimpf
 * 
 * Description- This models a winUp in Snake. If you eat this, you win!
 * 
 * Fields - position, velocity, size
 * Methods - effect(Snake, BoardArray) - you win, the score is 98
 * 			- checkReady(GameObj[][], Snake) - is it ready to be put in yet?
 *
 */
public class WinUp extends GameObj implements PowerUp{
	private static final int P = -1;
	private static final int VX = 0;
	private static final int VY = 0;
	private static final int SIZE = 20;
	public WinUp() {
		super(P, P, VX, VY, SIZE, SIZE);
	}
	public WinUp(int px, int py) {
		super(px, py, VX, VY, SIZE, SIZE);
	}
	/**
	 * If you interact with this piece, you win
	 * 
	 * @param snake, boardArray
	 * @return snake you have modified
	 */
	@Override
	public Snake effect(Snake snake, BoardArray b) {
		b.setScore(98);
		return snake;
	}
	/**
	 * Its ready on certain levels, or in a 1% chance
	 * 
	 * @param board, snake
	 * @return boolean if ready
	 */
	@Override
	public boolean checkReady(GameObj[][] board, Snake snake) {
		int ready = 1 + (int) (Math.random() * 100);
		int currentScore = snake.getLength() ;
		if (currentScore == 42 || currentScore == 4 || ready > 99) {
			for (int i = 0; i < 10; i++) { 
				for (int j = 0; j < 10; j++) {
					//if a WinUp already exist
					if (board[i][j] instanceof WinUp) {
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
		g.setColor(Color.yellow);
		int modifiedCoordX = 30 * (getPx());
    	int modifiedCoordY = 30 * (getPy());
    	g.fill3DRect(modifiedCoordY, modifiedCoordX, SIZE, SIZE, true);
	}
	@Override
	public String toString() {
		String s = this.getPx() + ", " + this.getPy();
		return s;
	}
}
