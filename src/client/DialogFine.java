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

import object.Tile;
import protocol.JFrameSet;

public class DialogFine extends JFrame implements JFrameSet {
	private DialogFine diallogSpecial = this;
	private final static String TAG = "DiallogSpecial : ";
	
	private String id; // �ش� ���� ���� �÷��̾� id

	private JLabel textLabel, labelFine;
	private JPanel btnPanel;
	private JButton payBtn;
	
	// Tile�� ���� ���� �޾ƿ� ���
	// TileNum�� ���ؼ� Tile�� �ĺ�
	// �ĺ��� Tile�� ���� ���� �޾ƿ´�.

	// ���̾� �αװ� Tile���� �޾Ƽ� ����ؾ��� ����
	private String tileName; // �ش� Ÿ���� �̸�
	private int tileNum; // �ش� Ÿ���� ��ȣ


	public DialogFine(String id) {
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
		labelFine = new JLabel("������ " + MarbleClient.TILE.getFine() + "�� �Դϴ�.");
		payBtn = new JButton("����� �����ϱ�");
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
		btnPanel.add(payBtn);
		
		add(textLabel, BorderLayout.NORTH);
		add(labelFine, BorderLayout.CENTER);
		add(btnPanel, BorderLayout.SOUTH);
	}

	@Override
	public void listener() {
		
		// Ȯ���ϰ� ����
		payBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MarbleClient.isDialogFine = true;
				
				setVisible(false);

			}
		});
	}

}
