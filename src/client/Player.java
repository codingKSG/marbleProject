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
	private int nowPlayerTile = 0;
	private String id;
	int money; // ���� ����
	int asset; // ���� ���ڻ� // �ǹ� + ����
	Vector<IsLandTile> playerCity; // ������ �ǹ�
	
	public Player(int x, int y, String id) {
		this.playerX = x;
		this.playerY = y;
		this.id = id;
		
		icPlayer = new ImageIcon("images/img_player01.png");
		setIcon(icPlayer); // �⺻�̹���(������)
		setSize(50, 50); // ũ�⼳��
		setLocation(playerX, playerY); // ������ǥ ����
	}

	public void moveAnimation(int newX, int newY, int newPlayerTile) { // ������ �̵�
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("������ x,y�� :" + playerX + "," + playerY);
				playerX = newX;
				playerY = newY;
				nowPlayerTile = newPlayerTile;
				setLocation(playerX, playerY); // ���ο� repaint() ����
				System.out.println(TAG + "moveAnimation ����");
				System.out.println("������ x,y�� :" + playerX + "," + playerY);
			}
		}).start();
	}
}
