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

		setTitle("¿ßƒ°∫∞ ¥Ÿ¿ÃæÛ∑Œ±◊ ≈◊Ω∫∆Æ");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setVisible(true);
	}

	@Override
	public void init() {
		player02 = new Test02Player();

		tile0 = new Test02Tile("√‚πﬂ ≈∏¿œ", 0);
		tile0 = new Test02Tile("Ω√∆º1 ≈∏¿œ", 1);
		tile0 = new Test02Tile("Ω√∆º2 ≈∏¿œ", 2);
		tile0 = new Test02Tile("º∂3 ≈∏¿œ", 3);
		tile0 = new Test02Tile("Ω∫∆‰º»4 ≈∏¿œ", 4);
		tile0 = new Test02Tile("º∂5 ≈∏¿œ", 5);
		tile0 = new Test02Tile("Ω√∆º6 ≈∏¿œ", 6);
		tile0 = new Test02Tile("Ω∫∆‰º»7 ≈∏¿œ", 7);
		
		btn = new JButton("¡÷ªÁ¿ß ±º∏Æ±‚");
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
<<<<<<< HEAD
				
				player02.move(dice);
				System.out.println("«√∑π¿ÃæÓ ¿ßƒ° : "+ player02.getPlayerLocation());
=======
				player02.move(dice);
				System.out.println("«√∑π¿ÃæÓ ¿ßƒ° : " + player02.getPlayerLocation());
				playerMove();
				
>>>>>>> ÍπÄÏÉÅÍ∏∏
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
			System.out.println("º∂ ≈∏¿œ¿‘¥œ¥Ÿ.");
			new DiallogIsland();
		}else if(player02.getPlayerLocation() == 1 || player02.getPlayerLocation() == 2 || player02.getPlayerLocation() == 6) {
			System.out.println("Ω√∆º ≈∏¿œ¿‘¥œ¥Ÿ.");
			new DiallogCity();
		}else if(player02.getPlayerLocation() == 4 || player02.getPlayerLocation() == 7) {
			System.out.println("Ω∫∆‰º» ≈∏¿œ¿‘¥œ¥Ÿ.");
			new DiallogSpecial();
		}else if(player02.getPlayerLocation() == 0){
			System.out.println("√‚πﬂ ≈∏¿œ¿‘¥œ¥Ÿ.");
			new DiallogStart();
		}
	}


	public static void main(String[] args) {
		new Test02App();
	}

}
