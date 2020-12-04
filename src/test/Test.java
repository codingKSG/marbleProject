package test;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import protocol.JFrameSet;
import tile.IslandTile;

public class Test extends JFrame implements JFrameSet{
	private Test test = this;
	private final static String TAG = "Test : ";
	
	private JPanel tile0 ,tile1, tile2, tile3, tile4, tile5, tile6, tile7, tileCenter;
	
	private Container c;
	
	Random dice = new Random();
	
	
	public Test() {
		init();
		setting();
		batch();
		listener();
		
		System.out.println();
		
		setVisible(true);
	}

	@Override
	public void init() {
		tile0 = new JPanel();
		tile1 = new JPanel();
		tile2 = new JPanel();
		tile3 = new JPanel();
		tile4 = new JPanel();
		tile5 = new JPanel();
		tile6 = new JPanel();
		tile7 = new JPanel();
		tileCenter = new JPanel();
		
		c = getContentPane();
		setLayout(new GridLayout(3,3,5,5));
		
		IslandTile seoul = new IslandTile(500, 400, 5000, null);
		
	}

	@Override
	public void setting() {
		setTitle("test server");
		setSize(300,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		tileCenter.setSize(150,150);
		
		c.setBackground(Color.BLACK);
		
	}

	@Override
	public void batch() {		
		
		add(tile4);
		add(tile5);
		add(tile6);
		add(tile3);
		add(tileCenter);
		add(tile7);
		add(tile2);
		add(tile1);
		add(tile0);
		
	}

	@Override
	public void listener() {
		
		
	}
	
	
	class groundLocation extends JPanel{
		
		
	}
	
	class player{
		int location = 0;
		
		void location(int dice) {
			this.location = location + dice;
			if(location == 8) location = 0;
		}
	}
	
	public static void main(String[] args) {
		new Test();
	}
}
