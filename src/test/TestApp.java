package test;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import protocol.JFrameSet;
import tile.IslandTile;

public class TestApp extends JFrame implements JFrameSet {
	private TestApp testApp = this;
	private final static String TAG = "Test : ";

	private JPanel tile0, tile1, tile2, tile3, tile4, tile5, tile6, tile7, tileCenter;

	private Container c;

	private JButton btn;
	
	private int dice;
	
	private TestPlayer player01;
	
	private JLabel laLocation;

	public TestApp() {
		init();
		setting();
		batch();
		listener();

		System.out.println();

		setVisible(true);
	}

	@Override
	public void init() {
		Player player01 = new Player();
		
		dice = 0;
		
		tile0 = new JPanel();
		tile1 = new JPanel();
		tile2 = new JPanel();
		tile3 = new JPanel();
		tile4 = new JPanel();
		tile5 = new JPanel();
		tile6 = new JPanel();
		tile7 = new JPanel();
		tileCenter = new JPanel();
	
		
		laLocation = new JLabel("No Mouse Event");
		
		MyMouseListener listener = new MyMouseListener();
		addMouseListener(listener);
		

		btn = new JButton("주시위 굴리기");

		c = getContentPane();
		setLayout(new GridLayout(3, 3, 5, 5));

		IslandTile tile0 = new IslandTile("tile0", 0);
		IslandTile tile1 = new IslandTile("tile1", 1);
		IslandTile tile2 = new IslandTile("tile2", 2);
		IslandTile tile3 = new IslandTile("tile3", 3);
		IslandTile tile4 = new IslandTile("tile4", 4);
		IslandTile tile5 = new IslandTile("tile5", 5);
		IslandTile tile6 = new IslandTile("tile6", 6);
		IslandTile tile7 = new IslandTile("tile7", 7);

	}

	@Override
	public void setting() {
		setTitle("test server");
		setSize(800, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		c.setBackground(Color.WHITE);

	}

	@Override
	public void batch() {
		tileCenter.add(btn);
		tileCenter.add(laLocation);

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
		
		
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {				
				Dice();
			}
		});
	}

	private class IslandTile {
		String name;
		int tileNum;

		public IslandTile(String name, int tileNum) {
			this.name = name;
			this.tileNum = tileNum;
		}
	}

	private class Player {
		int location = 0;

		public void location(int dice) {
			this.location = location + dice;
			if (location >= 8)
				location = location - 8;
		}
	}
	
	public void Dice() {
		Random dice = new Random();
		this.dice = dice.nextInt(6) + 1;
		System.out.println(this.dice);
	}
	
	class MyMouseListener extends MouseAdapter{
		@Override
		public void mousePressed(MouseEvent e) {
			laLocation.setText("[" + e.getX() + " , " + e.getY() + "]");
		}
	}

	public static void main(String[] args) {
		new TestApp();
		
		
	}
}
