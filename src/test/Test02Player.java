package test;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import lombok.Data;

@Data
public class Test02Player extends JPanel{
	private Test02Player test02Player = this;
	private final static String TAG = "TEST02PLAYER : ";
	
	private int playerLocation = 0;

	private ImageIcon player;
	private int x = 240;
	private int y = 240;
	
	public Test02Player() {
		player = new ImageIcon("images/img_player04.png");
		setSize(50,50);
		setLocation(x, y);
	}
	
	public void move(int dice) {
		this.playerLocation = this.playerLocation + dice;
		
		if(this.playerLocation >= 8) this.playerLocation = this.playerLocation - 8;
	}
	
}
