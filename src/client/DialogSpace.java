package client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import object.Tile;
import protocol.JFrameSet;

public class DialogSpace extends JDialog implements JFrameSet {
	private DialogSpace diallogSpace = this;
	private final static String TAG = "DialogSpace : ";
	
	private String id;
	private JLabel labelTitle;
	private JPanel panelCenter, btnPanel;
	private JButton btnConfirm;
	
	private Vector<Tile> tileList;
	private Vector<Tile> playerTileList;
	private Vector<JRadioButton> jrbList;
	private ButtonGroup group;
	
	// Tile의 상태 값을 받아올 방법
	// TileNum을 통해서 Tile을 식별
	// 식별된 Tile의 상태 값을 받아온다.

	// 다이얼 로그가 Tile에게 받아서 출력해야할 값들

	public DialogSpace(String id, Vector<Tile> tileList) {
		this.id = id;
		this.tileList = tileList;
		
		init();
		setting();
		batch();
		listener();
		
		setVisible(true);
	}

	@Override
	public void init() {
		labelTitle = new JLabel("세계여행");
		btnPanel = new JPanel();
		btnConfirm = new JButton("이곳으로 이동!");
		panelCenter = new JPanel();
		group = new ButtonGroup();
		playerTileList = new Vector<>();
		jrbList = new Vector<>();
		
		for (int i = 0; i < tileList.size(); i++) {
				if ((tileList.get(i).getTileType() != 6)) {
					playerTileList.add(tileList.get(i));
				}
			}
		
		for (int i = 0; i < playerTileList.size(); i++) {
			if (playerTileList.size() % 2 == 0) {
				panelCenter.setLayout(new GridLayout(playerTileList.size()/2, 2));
			} else {
				panelCenter.setLayout(new GridLayout(playerTileList.size()/3, 3));
			}
			
			JRadioButton jrb = new JRadioButton(playerTileList.get(i).getTileName());
			group.add(jrb);
			panelCenter.add(jrb);
			jrbList.add(jrb);
		}
	}

	@Override
	public void setting() {
		setSize(250, 450);
		setUndecorated(true);
		setLocationRelativeTo(null);

		setModal(true);
		
		labelTitle.setHorizontalAlignment(JLabel.CENTER);
		setModal(true);
	}

	@Override
	public void batch() {
		btnPanel.add(btnConfirm);
		
		add(labelTitle, BorderLayout.NORTH);
		add(panelCenter, BorderLayout.CENTER);
		add(btnPanel, BorderLayout.SOUTH);
	}

	@Override
	public void listener() {
		
		// 확인하고 끄기
		btnConfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedName = "";
				int selectedNum = 0;
				
				for (int i = 0; i < jrbList.size(); i++) {
					if (jrbList.get(i).isSelected()) {
						selectedName = jrbList.get(i).getText();
						break;
					}
				}
				
				for (int j = 0; j < playerTileList.size(); j++) {
					if (playerTileList.get(j).getTileName().equals(selectedName)) {
						selectedNum = playerTileList.get(j).getTileNum();
						break;
					}
				}
				
				MarbleClient.spaceTileNum = selectedNum;
				MarbleClient.isSpace = true;
				setVisible(false);
			}
		});
	}
}
