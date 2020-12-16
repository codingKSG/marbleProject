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

	private String id; // �ش� ���� ���� �÷��̾� id
	private JLabel labelText, labelEvent; // Ÿ���̸�, �ȳ� ����
	private JPanel btnPanel;
	private JButton confirmBtn;

	// ���̾� �αװ� Tile���� �޾Ƽ� ����ؾ��� ����
	private String tileName; // �ش� Ÿ���� �̸�
	private int tileNum; // �ش� Ÿ���� ��ȣ
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
		confirmBtn = new JButton("Ȯ��");
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

		// Ȯ���ϰ� ����
		confirmBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MarbleClient.isDialogSpecial = true;

				setVisible(false);

			}
		});
	}
}
