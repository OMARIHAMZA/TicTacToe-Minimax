package omari.hamza;

public class Cell {

	private int x;
	private int y;
	private int heuristic;

	Cell(int x, int y, int heuristic) {
		this.x = x;
		this.y = y;
		this.heuristic = heuristic;
	}

	int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getHeuristic() {
		return heuristic;
	}

	public void setHeuristic(int heuristic) {
		this.heuristic = heuristic;
	}
}
