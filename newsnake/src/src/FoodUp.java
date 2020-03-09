import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
/**
 * 
 * @author Jonah Schimpf
 * 
 * Description: This models a food piece in snake.
 * 
 * Fields: position, initial velocity, size, img
 * 
 * Methods: Snake effect(Snake, BoardArray) - adds an additional piece to the snake
 * 			Boolean checkReady(Snake, GameObj[][]) - checks if there should be a food up.
 * 													If there isn't a food up, add on.
 *			draw() - objects draw function
 *			toString() - objects toString function- not needed
 */
public class FoodUp extends GameObj implements PowerUp {
	private static final int P = 5;
	private static final int VX = 0;
	private static final int VY = 0;
	private static final int SIZE = 20;
	 public static final String IMG_FILE = "files/donut.png";
	 private static BufferedImage img;

	public FoodUp() {
		super(P, P, VX, VY, SIZE, SIZE);
		try {
			if (img == null) {
				img = ImageIO.read(new File(IMG_FILE));
			}
		}catch (IOException e) {
				System.out.println("Internal Error:" + e.getMessage());
			}
	}
	public FoodUp(int px, int py) {
		super(px, py, VX, VY, SIZE, SIZE);
	}
	@Override
	public void draw(Graphics g) {
		int modifiedCoordX = 30 * (getPx());
    	int modifiedCoordY = 30 * (getPy());
    	g.drawImage(img, modifiedCoordY, modifiedCoordX, SIZE, SIZE, null);
	}
	/**
	 * Adds a piece to the snake, this will be the new head of the snake
	 * 
	 * @param Snake snake, BoardArray b
	 * @return Snake that has been effected 
	 */
	@Override
	public Snake effect(Snake snake, BoardArray b) {
		//when eaten, just add a piece and generate a new snake behind the last SnakePiece
		snake.addSnakePiece(this);
		return snake;
	}
	/**
	 * checks if there is not a food up in the board, if there isn't add on.
	 * 
	 * @param GameObj[][] board, Snake snake
	 * @return boolean if its ready
	 */
	@Override
	public boolean checkReady(GameObj[][] board, Snake snake) {
		for (int i = 0; i < 10; i++) { 
			for (int j = 0; j < 10; j++) {
				//if a FoodUp already exist
				if (board[i][j] instanceof FoodUp) {
					return false;
				}
			}
		}
		//if there is no FoodUp
		return true;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	public static void main(String[] args) {
		FoodUp foodUp = new FoodUp();
		foodUp.setPx(30);
		System.out.println(foodUp.getPx());
	}
	
}