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
		player02.playerMove(dice);

		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dice();
			}
		});
	}

	public void dice() {
		Random dice = new Random();

		this.dice = dice.nextInt(6) + 1;
		
		System.out.println(this.dice);
	}
	
	public void playerMove() {
		
	}

	public static void main(String[] args) {
		new Test02App();
	}

}
