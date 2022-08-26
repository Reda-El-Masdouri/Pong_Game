import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Score extends Rectangle{
	
	static int GAME_WIDTH;
	static int GAME_HEIGHT;
	int player1; // hold the score of player 1
	int player2; // hold the score of player 2

	Score(int GAME_WIDTH, int GAME_HEIGHT){
		Score.GAME_WIDTH = GAME_WIDTH;
		Score.GAME_HEIGHT = GAME_HEIGHT;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.yellow);
		g.setFont(new Font("Consolas",Font.PLAIN,60));
		
		g.drawLine(GAME_WIDTH/2, 0, GAME_WIDTH/2, GAME_HEIGHT);
		g.setColor(Color.pink);
		g.drawString(String.valueOf(player1/10)+String.valueOf(player1%10), (int)(GAME_WIDTH * 0.25), GAME_HEIGHT/8);
		g.drawString(String.valueOf(player2/10)+String.valueOf(player2%10), (int)(GAME_WIDTH * 0.75), GAME_HEIGHT/8);
		
		// String.valueOf(player1/10)+String.valueOf(player1%10) for the 2 digit on the screen
		}
}
