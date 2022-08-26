import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Ball extends Rectangle{
	Random random;
	int xVelocity;
	int yVelocity;
	int initialSpeed = 2;
	
	Ball(int x, int y, int width, int height){
		super(x,y,width,height);
		
		random = new Random();
		int randomXDirection = random.nextInt(2); // 0 -> left. 1 -> right.
		if(randomXDirection == 0) {
			randomXDirection--;
		}
		setXDirection(randomXDirection*initialSpeed);
		int randomYDirection = random.nextInt(2); // 0 -> down. 1 -> up.
		if(randomYDirection == 0) {
			randomYDirection--;
		}
		setYDirection(randomYDirection*initialSpeed);
	}
	
	public void setXDirection(int radomXDirection) {
		xVelocity = radomXDirection;
	}
	public void setYDirection(int radomYDirection) {
		yVelocity = radomYDirection;
	}
	public void move() {
		x += xVelocity;
		y += yVelocity;
	}
	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.fillOval(x, y, width, height);
	}
}
