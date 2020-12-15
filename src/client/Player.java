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

	private ImageIcon icPlayer; // �÷��̾� �̹��� ����
	private int playerX;
	private int playerY; // �÷��̾��� ��ǥ
	private String imageSource;
	private int nowPlayerTile = 0;
	private String id;
	int money = 2500; // ���� ����
	int asset; // ���� ���ڻ� // �ǹ� + ����
	Vector<IsLandTile> playerCity; // ������ �ǹ�

	public Player(int x, int y, String imageSource) {
		this.playerX = x;
		this.playerY = y;
		this.imageSource = imageSource;

		icPlayer = new ImageIcon(imageSource);
		setIcon(icPlayer); // �⺻�̹���(������)
		setSize(30, 30); // ũ�⼳��
		setLocation(playerX, playerY); // ������ǥ ����
	}

	public void moveAnimation(int newX, int newY, int newPlayerTile) { // �̵�
		new Thread(new Runnable() {
			@Override
			public void run() {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					playerX = playerX +(newX-playerX)/4; //�� ĭ �̵��� �ѹ��� 1/4�� �̵�
					playerY = playerY+ (newY-playerY)/4;
					setLocation(playerX, playerY); // ���ο� repaint() ����
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					playerX = playerX +(newX-playerX)/4;
					playerY = playerY+ (newY-playerY)/4;
					setLocation(playerX, playerY); // ���ο� repaint() ����
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					playerX = playerX +(newX-playerX)/4;
					playerY = playerY+ (newY-playerY)/4;
					setLocation(playerX, playerY); // ���ο� repaint() ����
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					playerX = playerX +(newX-playerX)/4;
					playerY = playerY+ (newY-playerY)/4;
					setLocation(playerX, playerY); // ���ο� repaint() ����
				nowPlayerTile = newPlayerTile; //��ĭ �̵� �Ϸ�� Ÿ����ġ ����.
			}
		}).start();
	}
}