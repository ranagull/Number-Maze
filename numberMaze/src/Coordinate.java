
//Coordinate class
public class Coordinate {
	//Coordinate class holds x,y and prev variables.
	//X and Y corresponds to x and y in the board and prev refers to the coordinate previous to this coordinate.
	private int x;
	private int y;
	private int prev_x;
	private int prev_y;
	Coordinate prev;
	int type;
	public Coordinate(int x, int y,int per_x,int per_y) {
		this.x = x;
		this.y = y;
		this.prev_x = per_x;
		this.prev_y = per_y;
	}
	
	//Getters and setters.
	public Coordinate getPrev() {
		return prev;
	}
	public void setPrev(Coordinate prev) {
		this.prev = prev;
	}
	public Coordinate(int x , int y) {
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getPrevX() {
		return prev_x;
	}
	public void setPrevX(int prev_x) {
		this.prev_x = prev_x;
	}
	public int getPrevY() {
		return prev_y;
	}
	public void setPrevY(int prev_y) {
		this.prev_y = prev_y;
	}
	
	
	
}
