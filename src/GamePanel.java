import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable{

	static final int GAME_WIDTH = 1000; // static -> shared with all instances 
	static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.5555)); // final -> no changeable 
								// ratio '5/9 = 0.5555' between width and height in ping pong table
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
	static final int BALL_DIAMETER = 20;
	static final int PADDLE_WIDTH = 25;
	static final int PADDLE_HEIGHT = 100;
	private static final int BALL_DIAMETR = 0;
	
	Thread gameThread;
	Image image;
	Graphics graphics;
	Random random;
	Paddle paddle1, paddle2;
	Ball ball;
	Score score;
	
	
	GamePanel(){
		newPaddles();
		newBall();
		score = new Score(GAME_WIDTH, GAME_HEIGHT);
		this.setFocusable(true);// if we press a key it going have focus
		this.addKeyListener(new AL());
		this.setPreferredSize(SCREEN_SIZE); // a Dimension -> SCREEN_SIZE
		
		gameThread = new Thread(this);// 'this': because this panel implements Runnable. the second method to declare a thread
		gameThread.start();
	}
	
	public void newBall() {
		random = new Random();
		int randomPosYBall = random.nextInt(GAME_HEIGHT-BALL_DIAMETER); // for the middle ->(GAME_HEIGHT-BALL_DIAMETER)/2
		ball = new Ball((GAME_WIDTH-BALL_DIAMETER)/2,randomPosYBall,BALL_DIAMETER,BALL_DIAMETER);
	}
	public void newPaddles() {
		paddle1 = new Paddle(0, (GAME_HEIGHT/2)-(PADDLE_HEIGHT/2), PADDLE_WIDTH, PADDLE_HEIGHT, 1);
		paddle2 = new Paddle(GAME_WIDTH - PADDLE_WIDTH, (GAME_HEIGHT/2)-(PADDLE_HEIGHT/2), PADDLE_WIDTH, PADDLE_HEIGHT, 2);
	}
	public void paint(Graphics g) {
		image = createImage(getWidth(), getHeight()); 
		graphics = image.getGraphics();
		draw(graphics);// draw all the components
		g.drawImage(image, 0, 0, this);
	}
	public void draw(Graphics g) {
		paddle1.draw(g);
		paddle2.draw(g);
		ball.draw(g);
		score.draw(g);
	}
	public void move() {
		paddle1.move();// the paddles will have a smooth moving 
		paddle2.move();
		ball.move();
	}
	public void checkCollision() {
		// bounce ball off top and bottom window edges:
		if(ball.y <= 0) {
			ball.setYDirection(-ball.yVelocity);
		}
		if(ball.y >= (GAME_HEIGHT-BALL_DIAMETER)) {
			ball.setYDirection(-ball.yVelocity);
		}
		
		// bounces ball of paddles: we use the intersects method of Rectangle class
		if(ball.intersects(paddle1)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++; // optional for more difficulty
			if(ball.yVelocity>0) {
				ball.yVelocity++; // optional for more difficulty
			}
			else {
				ball.yVelocity--;
			}
			ball.setXDirection(ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		if(ball.intersects(paddle2)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++; // optional for difficulty
			if(ball.yVelocity>0) {
				ball.yVelocity++;
			}
			else {
				ball.yVelocity--;
			}
			ball.setXDirection(-ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		
		// stop paddles at window edges:
		if(paddle1.y <= 0) {
			paddle1.y = 0;
		}
		if(paddle1.y > (GAME_HEIGHT - PADDLE_HEIGHT)) {
			paddle1.y = GAME_HEIGHT - PADDLE_HEIGHT;
		}
		if(paddle2.y <= 0) {
			paddle2.y = 0;
		}
		if(paddle2.y > (GAME_HEIGHT - PADDLE_HEIGHT)) {
			paddle2.y = GAME_HEIGHT - PADDLE_HEIGHT;
		}
		
		// give a player 1 point and draw new paddles and ball
		if(ball.x <= 0) {
			score.player2++;
			newPaddles();
			newBall();
			System.out.println("player 2: "+score.player2);
		}
		if(ball.x >= GAME_WIDTH-BALL_DIAMETER) {
			score.player1++;
			newPaddles();
			newBall();
			System.out.println("player 1: "+score.player1);
		}
	}
	public void run() {// basic game loop but not the better
		// Game loop
		long lastTime = System.nanoTime();
		double amountOfThicks = 60.0;
		double ns = 1000000000/amountOfThicks; // nano-second
		double delta = 0;
		while(true) {
			long now =System.nanoTime();
			delta += (now - lastTime)/ns;
			lastTime = now;
			if(delta >= 1) {
				move();
				checkCollision();
				repaint();
				delta--;
			}
		}
	}
	// inner class for action listener
	public class AL extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			paddle1.keyPressed(e);
			paddle2.keyPressed(e);
		}
		public void keyReleased(KeyEvent e) {
			paddle1.keyReleased(e);
			paddle2.keyReleased(e);
		}
	}
}
