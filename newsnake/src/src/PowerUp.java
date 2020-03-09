import java.util.List;

public interface PowerUp extends GameObject{
	
	//This should be the function that happens to the Snake when the PowerUp gets eaten
	public Snake effect(Snake snake, BoardArray court);
	
	//Is the PowerUp ready to be implemented?
	public boolean checkReady(GameObj[][] board, Snake snake);
	
	//Testing function
	public String toString();
}
