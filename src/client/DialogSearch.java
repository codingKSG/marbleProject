package client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import object.Tile;
import protocol.JFrameSet;

public class DialogSearch extends JFrame implements JFrameSet {
	private DialogSearch dialogSearch = this;
	private final static String TAG = "DialogSearch : ";
	
	private Tile tile;
	private JLabel labelTitle, labelText;
	private JPanel btnPanel;
	private JButton btnConfirm;
	
	// Tile의 상태 값을 받아올 방법
	// TileNum을 통해서 Tile을 식별
	// 식별된 Tile의 상태 값을 받아온다.

	// 다이얼 로그가 Tile에게 받아서 출력해야할 값들

	public DialogSearch(Tile tile) {
		this.tile = tile;
		
		init();
		setting();
		batch();
		listener();
		
		setVisible(true);
	}

	@Override
	public void init() {
		labelTitle = new JLabel(tile.getTileName());
		labelText = new JLabel();
		btnPanel = new JPanel();
		btnConfirm = new JButton("확인");
	}

	@Override
	public void setting() {
		setSize(250, 250);
		setUndecorated(true);
		setLocationRelativeTo(null);

		labelTitle.setHorizontalAlignment(JLabel.CENTER);
		labelText.setHorizontalAlignment(JLabel.CENTER);
		labelText.setVerticalAlignment(JLabel.CENTER);
		labelText.setFont(new Font("CookieRun BLACK", Font.ROMAN_BASELINE, 20));
		labelText.setLayout(new FlowLayout());
		
		if (tile.getLandOwner().equals("")) {
			if (tile.getTileType() == 1) {
				labelText.setLayout(new GridLayout(5, 1));
				labelText.add(new JLabel("현재 소유주가 없습니다."));
				labelText.add(new JLabel("땅 구매비용 : " + tile.getPriceLand()));
				labelText.add(new JLabel("집 구매비용 : " + tile.getPriceHouse()));
				labelText.add(new JLabel("빌딩 구매비용 : " + tile.getPriceBuilding()));
				labelText.add(new JLabel("호텔 구매비용 : " + tile.getPriceHotel()));
			} else if (tile.getTileType() == 2) {
				labelText.setLayout(new GridLayout(2, 1));
				labelText.add(new JLabel("현재 소유주가 없습니다."));
				labelText.add(new JLabel("섬 구매비용 : " + tile.getPriceLand()));
			}
		} else {
			if (tile.getTileType() == 1) {
				labelText.setLayout(new GridLayout(2, 1));
				labelText.add(new JLabel("현재 소유주 : " + tile.getLandOwner()));
				labelText.add(new JLabel("총 벌금 : " + tile.getFine()));
			} else if (tile.getTileType() == 2) {
				labelText.setLayout(new GridLayout(2, 1));
				labelText.add(new JLabel("현재 소유주 : " + tile.getLandOwner()));
				labelText.add(new JLabel("총 벌금 : " + tile.getFine()));
			}
		}
	}

	@Override
	public void batch() {
		btnPanel.add(btnConfirm);
		
		add(labelTitle, BorderLayout.NORTH);
		add(labelText, BorderLayout.CENTER);
		add(btnPanel, BorderLayout.SOUTH);
	}

	@Override
	public void listener() {
		
		// 확인하고 끄기
		btnConfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MarbleClient.isInfoOn = false;
				setVisible(false);
			}
		});
	}

	public static void main(String[] args) {
		new DialogSearch(new Tile("하이", 0, 0, 0, 0));
	}
}
