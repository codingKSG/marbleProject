package player;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Data;

@Data
public class Player extends JLabel {

	private Player player = this;
	private static final String TAG = "Player : ";

	private ImageIcon icPlayer; // �÷��̾� �̹��� ����
	private int x;
	private int y; // �÷��̾��� ��ǥ
	private String id;

	public Player(int x, int y, String id) {
		this.x = x;
		this.y = y;
		this.id = id;
		
		icPlayer = new ImageIcon("images/img_player01.png");
		setIcon(icPlayer); // �⺻�̹���(������)
		setSize(50, 50); // ũ�⼳��
		setLocation(x, y); // ������ǥ ����
	}

	public void moveAnimation(int newX, int newY) { // ������ �̵�
		System.out.println(TAG + "moveAnimation()");
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				setLocation(newX, newY); // ���ο� repaint() ����
			}
		}).start();
	}
}
