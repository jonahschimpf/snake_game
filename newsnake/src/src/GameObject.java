import java.awt.Graphics;

public interface GameObject {
		public void draw(Graphics g);
		
		public String toString();
		
	//	public boolean intersects(GameObject g);
		
		public int getPx();
		
		public int getPy();
		
		public int getVx();
		
		public int getVy();
}
