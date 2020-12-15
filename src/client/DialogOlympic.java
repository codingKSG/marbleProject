package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import object.Tile;
import protocol.JFrameSet;

public class DialogOlympic extends JDialog implements JFrameSet {
	private DialogOlympic diallogSpecial = this;
	private final static String TAG = "DialogOlympic : ";
	
	private String id;
	private JLabel labelTitle;
	private JPanel panelCenter, btnPanel;
	private JButton btnConfirm;
	
	private Vector<Tile> tileList;
	ButtonGroup group;
	
	// Tile�� ���� ���� �޾ƿ� ���
	// TileNum�� ���ؼ� Tile�� �ĺ�
	// �ĺ��� Tile�� ���� ���� �޾ƿ´�.

	// ���̾� �αװ� Tile���� �޾Ƽ� ����ؾ��� ����

	public DialogOlympic(Vector<Tile> tileList) {
		this.tileList = tileList;
		
		init();
		setting();
		batch();
		listener();
		
		setVisible(true);
	}

	@Override
	public void init() {
		labelTitle = new JLabel("�ø��� ����");
		btnPanel = new JPanel();
		btnConfirm = new JButton("���� �ι�!");
		panelCenter = new JPanel();
		group = new ButtonGroup();
		
		
		for (int i = 0; i < tileList.size(); i++) {
			JRadioButton jrb = null;
			jrb = new JRadioButton(tileList.get(i).getTileName());
			group.add(jrb);
			panelCenter.add(jrb);
		}
	}

	@Override
	public void setting() {
		setSize(250, 250);
		setUndecorated(true);
		setLocationRelativeTo(null);

		setModal(true);
		
		labelTitle.setHorizontalAlignment(JLabel.CENTER);
		
	}

	@Override
	public void batch() {
		btnPanel.add(btnConfirm);
		
		add(labelTitle, BorderLayout.NORTH);
		add(btnPanel, BorderLayout.SOUTH);
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
}
