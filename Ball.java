
public class Ball {
	int x;
	int y;
	int vx;
	int vy;
	int boardWidth;
	int boardHeight;
	int diameter;
	public Ball(int x, int y, int vx, int vy, int boardWidth, int boardHeight, int diameter) {
		//creates ball object with given characteristics
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		this.diameter = diameter;
	}
	public void move() {
		//ball moves according to its horizontal and vertical velocities
		if (x >= boardWidth - diameter / 2 || x <= diameter / 2) {
			vx *= -1;
		}
		if (y <= diameter / 2) {
			vy *= -1;
		}
		x += vx;
		y += vy;
	}
}
