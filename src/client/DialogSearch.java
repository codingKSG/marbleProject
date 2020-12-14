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

public class DialogSearch extends JFrame implements JFrameSet {
	private DialogSearch diallogSpecial = this;
	private final static String TAG = "DiallogSpecial : ";
	
	private String id; // �ش� ���� ���� �÷��̾� id

	private JLabel labelText;
	private JPanel panelBtn;
	private JButton btnConfirm;
	
	// Tile�� ���� ���� �޾ƿ� ���
	// TileNum�� ���ؼ� Tile�� �ĺ�
	// �ĺ��� Tile�� ���� ���� �޾ƿ´�.

	// ���̾� �αװ� Tile���� �޾Ƽ� ����ؾ��� ����
	private String tileName; // �ش� Ÿ���� �̸�
	private int tileNum; // �ش� Ÿ���� ��ȣ


	public DialogSearch(String id) {
		this.id = id;

		init();
		setting();
		batch();
		listener();

		
		
		setVisible(true);
	}

	@Override
	public void init() {
		labelText = new JLabel(tileName);
		btnConfirm = new JButton("Ȯ��");
		panelBtn = new JPanel();
	}

	@Override
	public void setting() {
		setSize(250, 250);
		setUndecorated(true);
		setLocationRelativeTo(null);

		labelText.setHorizontalAlignment(JLabel.CENTER);

	}

	@Override
	public void batch() {		
		panelBtn.add(btnConfirm);
		
		add(labelText, BorderLayout.NORTH);
		add(panelBtn, BorderLayout.SOUTH);
	}

	@Override
	public void listener() {
		
		// Ȯ���ϰ� ����
		btnConfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);

			}
		});
	}

	public static void main(String[] args) {
		new DialogSearch("test");
	}
}
