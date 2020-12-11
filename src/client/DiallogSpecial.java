package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import protocol.JFrameSet;

public class DiallogSpecial extends JFrame implements JFrameSet {
	private DiallogSpecial diallogSpecial = this;
	private final static String TAG = "DiallogSpecial : ";
	
	private String id; // �ش� ���� ���� �÷��̾� id

	private JLabel textLabel;
	private JPanel btnPanel;
	private JButton confirmBtn;
	
	// Tile�� ���� ���� �޾ƿ� ���
	// TileNum�� ���ؼ� Tile�� �ĺ�
	// �ĺ��� Tile�� ���� ���� �޾ƿ´�.

	// ���̾� �αװ� Tile���� �޾Ƽ� ����ؾ��� ����
	private String tileName; // �ش� Ÿ���� �̸�
	private int tileNum; // �ش� Ÿ���� ��ȣ


	public DiallogSpecial(String id) {
		this.id = id;

		init();
		setting();
		batch();
		listener();

		
		
		setVisible(true);
	}

	@Override
	public void init() {
		textLabel = new JLabel(tileName);
		confirmBtn = new JButton("Ȯ��");
		btnPanel = new JPanel();
	}

	@Override
	public void setting() {
		setSize(250, 250);
		setUndecorated(true);
		setLocationRelativeTo(null);

		textLabel.setHorizontalAlignment(JLabel.CENTER);

	}

	@Override
	public void batch() {		
		btnPanel.add(confirmBtn);
		
		add(textLabel, BorderLayout.NORTH);
		add(btnPanel, BorderLayout.SOUTH);
	}

	@Override
	public void listener() {
		
		// Ȯ���ϰ� ����
		confirmBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);

			}
		});
	}

	public static void main(String[] args) {
		new DiallogSpecial("test");
	}
}
