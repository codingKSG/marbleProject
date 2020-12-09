package client;

import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Data;
import object.IsLandTile;

@Data
public class Player extends JLabel {

	private Player player = this;
	private static final String TAG = "Player : ";

	private ImageIcon icPlayer; // 플레이어 이미지 구현
	private int playerX;
	private int playerY; // 플레이어의 좌표
	private int nowPlayerTile = 0;
	private String id;
	int money; // 보유 현금
	int asset; // 보유 총자산 // 건물 + 현금
	Vector<IsLandTile> playerCity; // 보유한 건물
	
	public Player(int x, int y, String id) {
		this.playerX = x;
		this.playerY = y;
		this.id = id;
		
		icPlayer = new ImageIcon("images/img_player01.png");
		setIcon(icPlayer); // 기본이미지(오른쪽)
		setSize(50, 50); // 크기설정
		setLocation(playerX, playerY); // 시작좌표 설정
	}

	public void moveAnimation(int newX, int newY, int newPlayerTile) { // 오른쪽 이동
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("변경전 x,y값 :" + playerX + "," + playerY);
				playerX = newX;
				playerY = newY;
				nowPlayerTile = newPlayerTile;
				setLocation(playerX, playerY); // 내부에 repaint() 존재
				System.out.println(TAG + "moveAnimation 실행");
				System.out.println("변경후 x,y값 :" + playerX + "," + playerY);
			}
		}).start();
	}
}
