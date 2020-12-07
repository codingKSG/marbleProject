package test;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;

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

		setTitle("��ġ�� ���̾�α� �׽�Ʈ");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		System.out.println("�÷��̾� ��ġ : "+ player02.getPlayerLocation());

		setVisible(true);
	}

	@Override
	public void init() {
		player02 = new Test02Player();

		tile0 = new Test02Tile("��� Ÿ��", 0);
		tile0 = new Test02Tile("��Ƽ1 Ÿ��", 1);
		tile0 = new Test02Tile("��Ƽ2 Ÿ��", 2);
		tile0 = new Test02Tile("��3 Ÿ��", 3);
		tile0 = new Test02Tile("�����4 Ÿ��", 4);
		tile0 = new Test02Tile("��5 Ÿ��", 5);
		tile0 = new Test02Tile("��Ƽ6 Ÿ��", 6);
		tile0 = new Test02Tile("�����7 Ÿ��", 7);
		
		
		btn = new JButton("�ֻ��� ������");
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
			}
		});
	}

	public void dice() {
		Random dice = new Random();

		this.dice = dice.nextInt(6) + 1;

		System.out.println(this.dice);
		System.out.println("�÷��̾� ��ġ : "+ player02.getPlayerLocation());
	}

	public void playerMove() {
		
	}

	public static void main(String[] args) {
		new Test02App();
	}

}
