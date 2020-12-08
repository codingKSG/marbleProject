package player;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Data;

@Data
public class Player extends JLabel {

	private Player player = this;
	private static final String TAG = "Player : ";

	private ImageIcon icPlayer; // 플레이어 이미지 구현
	private int x;
	private int y; // 플레이어의 좌표
	private String id;

	public Player(int x, int y, String id) {
		this.x = x;
		this.y = y;
		this.id = id;
		
		icPlayer = new ImageIcon("images/img_player01.png");
		setIcon(icPlayer); // 기본이미지(오른쪽)
		setSize(50, 50); // 크기설정
		setLocation(x, y); // 시작좌표 설정
	}

	public void moveAnimation(int newX, int newY) { // 오른쪽 이동
		System.out.println(TAG + "moveAnimation()");
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				setLocation(newX, newY); // 내부에 repaint() 존재
			}
		}).start();
	}
}
