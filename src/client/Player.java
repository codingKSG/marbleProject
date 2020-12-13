package client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

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
	int money; // ���� ����
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
//				System.out.println("������ x,y�� :" + playerX + "," + playerY);
				while (nowPlayerTile != newPlayerTile) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					playerX = newX;
					playerY = newY;
					nowPlayerTile = newPlayerTile;
					setLocation(playerX, playerY); // ���ο� repaint() ����
					System.out.println(TAG + "moveAnimation ����");
					System.out.println("������ x,y�� :" + playerX + "," + playerY);
				}
			}
		}).start();
	}
}