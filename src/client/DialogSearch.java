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
	
	// Tile�� ���� ���� �޾ƿ� ���
	// TileNum�� ���ؼ� Tile�� �ĺ�
	// �ĺ��� Tile�� ���� ���� �޾ƿ´�.

	// ���̾� �αװ� Tile���� �޾Ƽ� ����ؾ��� ����

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
		btnConfirm = new JButton("Ȯ��");
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
				labelText.add(new JLabel("���� �����ְ� �����ϴ�."));
				labelText.add(new JLabel("�� ���ź�� : " + tile.getPriceLand()));
				labelText.add(new JLabel("�� ���ź�� : " + tile.getPriceHouse()));
				labelText.add(new JLabel("���� ���ź�� : " + tile.getPriceBuilding()));
				labelText.add(new JLabel("ȣ�� ���ź�� : " + tile.getPriceHotel()));
			} else if (tile.getTileType() == 2) {
				labelText.setLayout(new GridLayout(2, 1));
				labelText.add(new JLabel("���� �����ְ� �����ϴ�."));
				labelText.add(new JLabel("�� ���ź�� : " + tile.getPriceLand()));
			}
		} else {
			if (tile.getTileType() == 1) {
				labelText.setLayout(new GridLayout(2, 1));
				labelText.add(new JLabel("���� ������ : " + tile.getLandOwner()));
				labelText.add(new JLabel("�� ���� : " + tile.getFine()));
			} else if (tile.getTileType() == 2) {
				labelText.setLayout(new GridLayout(2, 1));
				labelText.add(new JLabel("���� ������ : " + tile.getLandOwner()));
				labelText.add(new JLabel("�� ���� : " + tile.getFine()));
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
		
		// Ȯ���ϰ� ����
		btnConfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MarbleClient.isInfoOn = false;
				setVisible(false);
			}
		});
	}

	public static void main(String[] args) {
		new DialogSearch(new Tile("����", 0, 0, 0, 0));
	}
}
