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
		this.setIcon(icPlayer); // �⺻�̹���(������)
		setSize(50, 50); // ũ�⼳��
		setLocation(x, y); // ������ǥ ����
	}

	public void moveAnimation(int newX, int newY) { // ������ �̵�
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("������ x,y�� :" + x + "," + y);
				x = newX;
				y = newY;
				setLocation(x, y); // ���ο� repaint() ����
				System.out.println(TAG + "moveAnimation ����");
				System.out.println("������ x,y�� :" + x + "," + y);
			}
		}).start();
	}
}
