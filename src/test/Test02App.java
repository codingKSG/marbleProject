package test;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.border.Border;

import protocol.JFrameSet;

public class Test02App extends JFrame implements JFrameSet {
	private Test02App test02App = this;
	private final static String TAG = "TEST02APP : ";
	private Test02Player player02;
	private Test02Tile tile0, tile1, tile2, tile3, tile4, tile5, tile6, tile7;

	private Container c, gameBoard;

	private int dice;

	private JButton btn;

	public Test02App() {
		init();
		setting();
		batch();
		listener();

		setTitle("위치별 다이얼로그 테스트");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setVisible(true);
	}

	@Override
	public void init() {
		player02 = new Test02Player();

		tile0 = new Test02Tile("출발 타일", 0);
		tile0 = new Test02Tile("시티1 타일", 1);
		tile0 = new Test02Tile("시티2 타일", 2);
		tile0 = new Test02Tile("섬3 타일", 3);
		tile0 = new Test02Tile("스페셜4 타일", 4);
		tile0 = new Test02Tile("섬5 타일", 5);
		tile0 = new Test02Tile("시티6 타일", 6);
		tile0 = new Test02Tile("스페셜7 타일", 7);
		
		btn = new JButton("주사위 굴리기");
		gameBoard = new Container();

	}

	@Override
	public void setting() {
		c = getContentPane();
		gameBoard.setLayout(null);

		gameBoard.add(player02);

	}

	@Override
	public void batch() {
		add(gameBoard, BorderLayout.CENTER);
		add(btn, BorderLayout.SOUTH);

	}

	@Override
	public void listener() {

		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dice();
				player02.move(dice);
				System.out.println("플레이어 위치 : " + player02.getPlayerLocation());
				playerMove();
				
			}
		});
	}

	public void dice() {
		Random dice = new Random();

		this.dice = dice.nextInt(6) + 1;

		System.out.println(this.dice);
	}

	public void playerMove() {
		if(player02.getPlayerLocation() == 3 || player02.getPlayerLocation() == 5) {
			System.out.println("섬 타일입니다.");
			new DiallogIsland();
		}else if(player02.getPlayerLocation() == 1 || player02.getPlayerLocation() == 2 || player02.getPlayerLocation() == 6) {
			System.out.println("시티 타일입니다.");
			new DiallogCity();
		}else if(player02.getPlayerLocation() == 4 || player02.getPlayerLocation() == 7) {
			System.out.println("스페셜 타일입니다.");
			new DiallogSpecial();
		}else if(player02.getPlayerLocation() == 0){
			System.out.println("출발 타일입니다.");
			new DiallogStart();
		}
	}


	public static void main(String[] args) {
		new Test02App();
	}

}
