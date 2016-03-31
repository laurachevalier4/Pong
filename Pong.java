import processing.core.PApplet;
public class Pong extends PApplet{
	
	final int WIDTH = 400, HEIGHT = 400;
		
	/**
	 * The background color of the window
	 */
	final int BACKGROUND_COLOR = 0;
	
	/**
	 * The diameter of the ball
	 */
	final int BALL_DIAMETER = 20;
	
	/**
	 * The paddle's x velocity
	 */
	final int PADDLE_VX = 3;
	
	/**
	 * The paddle's width
	 */
	final int PADDLE_WIDTH = 75;
	
	/**
	 * The paddle's height
	 */	
	final int PADDLE_HEIGHT = 15;

	/**
	 * The ball's max velocity
	 */	
	final int MAX_VELOCITY = 5;
	
	/**
	 * The ball's min velocity
	 */		
	final int MIN_VELOCITY = 2;	
	
	/**
	 * The ball instance representing the game's ball
	 */
	Ball ball;
	
	/**
	 * The Bar instance that represents the game's paddle
	 */
	Bar paddle = new Bar(WIDTH / 2 - PADDLE_WIDTH / 2, HEIGHT - PADDLE_HEIGHT, 
			PADDLE_VX, PADDLE_WIDTH, PADDLE_HEIGHT, WIDTH);
	
    /**
     * Allow processing to be run as application rather than just applet
     */
    public static void main(String[] args) {
        PApplet.main("Pong");
    }
                    
    /**
     * Sets height and width of sketch, initalizes game.
     */
	public void setup() {
		size(WIDTH, HEIGHT);
		noStroke();
		reset();
	}
    /**
     * "Main" draw method... controls animation, clears screen, draws
     * elements and checks for collision.
     */
	public void draw() {
		background(BACKGROUND_COLOR);
		ball.move();
		paddle.move();
	
		// check if there's a collision... if there is, bounce the ball
		// in the opposite direction
		if (intersects(ball, paddle)) {
			System.out.println("intersects!");
			if ((ball.y + ball.diameter / 2 > paddle.y && !(ballXOnBar(ball, paddle )) 
					|| ball.y + ball.diameter / 2 >= HEIGHT - 5)) {
				/*
				 * If ball's y coordinate is lower than the height of the
				 * paddle, it should be hitting the side
				 * which means the horizontal velocity would change
				 * but ball.vy would stay same.
				 * No matter what, if ball's bottom x value is above a certain point near 
				 * the height of the screen, the ball's downward velocity 
				 * will stay the same
				 */
				ball.vx *= -1;
				ball.x += ball.vx * 2;
				//^Keeps ball from getting stuck on side / prevents glitch
				System.out.println("changing x velocity");
			} else {
				if (ball.y + ball.diameter / 2 > paddle.y && ball.y <= HEIGHT) {
					ball.y = paddle.y - ball.diameter / 2;
				}
				Math.abs(ball.vy); //accounts for glitch:
				//if ball hits board, it always goes back up
				ball.vy *= -1;
				System.out.println("changing y velocity");
			}
		}
		ellipse(ball.x, ball.y, ball.diameter, ball.diameter);
		rect(paddle.x, paddle.y, paddle.barWidth, paddle.barHeight);
	}
	
    /**
     * Reacts to left, right and (r)eset keys. Left and right change
     * the direction of the paddle.
     */
	public void keyPressed() {
		if(keyCode == LEFT) {
			paddle.goLeft();

		} else if(keyCode == RIGHT) {
			paddle.goRight();

		}
		
		if(key == 'r') {
			reset();
		}
	}
	
	/**
	 * brings the ball back to the center of the screen, with a
	 * random velocity for x and y... and add the paddle to the
	 * center bottom of the window, with an initial velocity
	 * going right
	 */
	public void reset() {
		ball = new Ball(WIDTH / 2 - DIAMETER / 2, HEIGHT / 2 -
				DIAMETER / 2, randSign() * randInt(MIN_VELOCITY, MAX_VELOCITY), 
				randSign() * randInt(MIN_VELOCITY, MAX_VELOCITY), WIDTH, 
				HEIGHT, BALL_DIAMETER);
		paddle = new Bar(WIDTH / 2 - PADDLE_WIDTH / 2, HEIGHT - PADDLE_HEIGHT, 
				PADDLE_VX, PADDLE_WIDTH, PADDLE_HEIGHT, WIDTH);
	}
	
	
	/* *
	 * tests if the ball intersects with the paddle
	 * @param b the ball
	 * @param p the paddle
	 * @return true if the ball and paddle intersect
	 */
	boolean intersects(Ball b, Bar p) {
		boolean intersects = false;
		double paddleIntersect = 0;
		//^x value on the paddle closest to where the ball hits
		if (b.x + b.diameter / 2 <= p.x) {
			//ball to left of bar
			paddleIntersect = p.x;
		} else if (b.x - b.diameter / 2 >= p.x + p.barWidth) {
			//ball to right of bar
			paddleIntersect = p.x + p.barWidth;
		} else if (b.x + b.diameter / 2 >= p.x && b.x - b.diameter / 2 <= p.x + p.barWidth) {
			//ball on paddle
			paddleIntersect = b.x;
		}
		for (int i = p.y; i < HEIGHT; i++) {
			//for loop accounts for ball hitting paddle at different y values
			if (distanceFormula(b, p, paddleIntersect, i)) {
				intersects = true;
				break;
			}
		}
		return intersects;
		}
	
	/* *
	 * generates a random integer from a, up to but not including b
	 * @param a the start number
	 * @param b goes up to, but does not include this number
	 * @return a random integer from a to b
	 */
	private int randInt(int a, int b) {
		return (int) (Math.random() * (b - a) + a);
	}
	
	/* *
	 * generates a 1 or -1
	 * @return either 1 or -1
	 */
	private int randSign() {
		int randInt = (int)Math.random() * 2;
		if (randInt == 0) {
			randInt = -1;
		}
		return randInt;
		}

	public boolean ballXOnBar(Ball b, Bar p) {
		//returns true if ball aligns with paddle's x values and is as low as bar
		if (b.x - b.diameter / 2 < p.x + p.barWidth && b.x + b.diameter / 2 > p.x && b.y + b.diameter / 2 >= p.y) {
			return true;
		} else {
			return false;
		}
	}
	public boolean distanceFormula(Ball b, Bar p, double x2, double y2) {
		//determine distance between point in ball and point on bar
		if (Math.sqrt(Math.pow(b.x - x2, 2) + Math.pow(b.y - y2, 2)) <= 10) {
			return true;
		}
		else {
			return false;
		}
	}
}

