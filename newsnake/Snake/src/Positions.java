/**
 * 
 * 
 * @author Jonah Schimpf
 * 
 * Description- Custom class that models a tuple (int, int) in java
 * 	implements Comparable because it must be in a list for getPositions in snake
 *
 *	Fields- x- row position
 *			y- column position
 */
class Positions implements Comparable<Positions>{
  final int x;
  final int y;
  Positions(int x, int y) {this.x=x;this.y=y;}
@Override
public int compareTo(Positions p) {
	int compareX = p.x;
	int compareY = p.y;
	if (compareX == x && compareY == y) {
		return 0;
	} else if (compareX + compareY > x + y) {
		return -1;
	} else {
		return 1;
	}
}
@Override
public boolean equals(Object o) {
	if (! (o instanceof Positions) ) {
		return false;
	}
	Positions p = (Positions) o;
	return (x == p.x && y == p.y);
}
  
public String toString() {
	String s = 
			"x: " + x + "  y: " + y;
	return s;
}
}