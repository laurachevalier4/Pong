
public class Bar {
	int x;
	int y;
	int vx;
	int barWidth;
	int barHeight;
	int boardWidth;
	public Bar(int x, int y, int vx, int barWidth, int barHeight, 
			int boardWidth) {
		//creates bar object
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.barWidth = barWidth;
		this.barHeight = barHeight;
		this.boardWidth = boardWidth;
	}
	public void move() {
		//moves bar horizontally using velocity vx
		x += vx;
		if (x >= boardWidth - barWidth) {
			x = boardWidth - barWidth;
		} else if (x <= 0) {
			x = 0;
		}
	}
	public void goLeft() {
		//adjusts x velocity so bar is moving left
		if (vx >= 0) {
			vx *= -1;
		}
	}
	public void goRight() {
		//adjusts x velocity so bar is moving right
		if (vx <= 0) {
			vx *= -1;
		}
	}
}
