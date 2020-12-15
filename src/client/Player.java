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
	private String imageSource;
	private int nowPlayerTile = 0;
	private String id;
	int money = 2500; // 보유 현금
	int asset; // 보유 총자산 // 건물 + 현금
	Vector<IsLandTile> playerCity; // 보유한 건물

	public Player(int x, int y, String imageSource) {
		this.playerX = x;
		this.playerY = y;
		this.imageSource = imageSource;

		icPlayer = new ImageIcon(imageSource);
		setIcon(icPlayer); // 기본이미지(오른쪽)
		setSize(30, 30); // 크기설정
		setLocation(playerX, playerY); // 시작좌표 설정
	}

	public void moveAnimation(int newX, int newY, int newPlayerTile) { // 이동
		new Thread(new Runnable() {
			@Override
			public void run() {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					playerX = playerX +(newX-playerX)/4; //한 칸 이동시 한번만 1/4씩 이동
					playerY = playerY+ (newY-playerY)/4;
					setLocation(playerX, playerY); // 내부에 repaint() 존재
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					playerX = playerX +(newX-playerX)/4;
					playerY = playerY+ (newY-playerY)/4;
					setLocation(playerX, playerY); // 내부에 repaint() 존재
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					playerX = playerX +(newX-playerX)/4;
					playerY = playerY+ (newY-playerY)/4;
					setLocation(playerX, playerY); // 내부에 repaint() 존재
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					playerX = playerX +(newX-playerX)/4;
					playerY = playerY+ (newY-playerY)/4;
					setLocation(playerX, playerY); // 내부에 repaint() 존재
				nowPlayerTile = newPlayerTile; //한칸 이동 완료시 타일위치 변경.
			}
		}).start();
	}
}