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
	
	private String id; // 해당 땅을 밟은 플레이어 id

	private JLabel labelText;
	private JPanel panelBtn;
	private JButton btnConfirm;
	
	// Tile의 상태 값을 받아올 방법
	// TileNum을 통해서 Tile을 식별
	// 식별된 Tile의 상태 값을 받아온다.

	// 다이얼 로그가 Tile에게 받아서 출력해야할 값들
	private String tileName; // 해당 타일의 이름
	private int tileNum; // 해당 타일의 번호


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
		btnConfirm = new JButton("확인");
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
		
		// 확인하고 끄기
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
