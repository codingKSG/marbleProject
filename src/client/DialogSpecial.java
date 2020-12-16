package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import protocol.JFrameSet;

public class DialogSpecial extends JDialog implements JFrameSet {
	private DialogSpecial diallogSpecial = this;
	private final static String TAG = "DiallogSpecial : ";

	private String id; // 해당 땅을 밟은 플레이어 id
	private JLabel labelText, labelEvent; // 타일이름, 안내 문구
	private JPanel btnPanel;
	private JButton confirmBtn;

	// 다이얼 로그가 Tile에게 받아서 출력해야할 값들
	private String tileName; // 해당 타일의 이름
	private int tileNum; // 해당 타일의 번호
	private String specialText;

	public DialogSpecial(String id, String specialText) {
		this.id = id;
		this.specialText = specialText;
		init();
		setting();
		batch();
		listener();

		setVisible(true);
	}

	@Override
	public void init() {
		labelEvent = new JLabel(specialText);
		labelText = new JLabel(tileName);
		confirmBtn = new JButton("확인");
		btnPanel = new JPanel();
	}

	@Override
	public void setting() {
		setSize(250, 250);
		setUndecorated(true);
		setLocationRelativeTo(null);

		labelText.setHorizontalAlignment(JLabel.CENTER);
		labelEvent.setHorizontalAlignment(JLabel.CENTER);
		
		setModal(true);
	}

	@Override
	public void batch() {
		btnPanel.add(confirmBtn);

		add(labelText, BorderLayout.NORTH);
		add(labelEvent, BorderLayout.CENTER);
		add(btnPanel, BorderLayout.SOUTH);
	}

	@Override
	public void listener() {

		// 확인하고 끄기
		confirmBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MarbleClient.isDialogSpecial = true;

				setVisible(false);

			}
		});
	}
}
