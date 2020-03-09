/**
 * 
 * @author Jonah Schimpf
 * 
 * Description- a custom class that models a score in snake. A score has the points
 * Received, as well as the user who accrued those points. Used for IO
 *
 */
public class Score implements Comparable<Score> {
	private int points;
	private String user;
	
	public Score(int points, String user) {
		this.points = points;
		this.user = user;
	}
	public int getPoints() {
		return points;
	}
	public String getUser() {
		return user;
	}
	public void setPoints(int score) {
		this.points = score;
	}
	@Override
	public int compareTo(Score sc) {
		return this.points -sc.points;
	}
	
}
