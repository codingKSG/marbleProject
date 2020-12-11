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
	
	private String id; // 해당 땅을 밟은 플레이어 id

	private JLabel textLabel;
	private JPanel btnPanel;
	private JButton confirmBtn;
	
	// Tile의 상태 값을 받아올 방법
	// TileNum을 통해서 Tile을 식별
	// 식별된 Tile의 상태 값을 받아온다.

	// 다이얼 로그가 Tile에게 받아서 출력해야할 값들
	private String tileName; // 해당 타일의 이름
	private int tileNum; // 해당 타일의 번호


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
		confirmBtn = new JButton("확인");
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
		
		// 확인하고 끄기
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
