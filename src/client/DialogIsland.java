package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import protocol.JFrameSet;

public class DialogIsland extends JDialog implements JFrameSet {
	private DialogIsland diallogIsland = this;
	private final static String TAG = "DiallogIsland : ";
	
	private String id; // �ش� ���� ���� �÷��̾� id
	private int playerMoney;

	private JLabel labelText, laAll, laLand;
	private JPanel panelMenu, panelBtn, panelLand;
	private JButton btnPurchased, btnCancel;
	private JCheckBox checkLand;

	private MyItemListener myItemListener;
	
	// Tile�� ���� ���� �޾ƿ� ���
	// TileNum�� ���ؼ� Tile�� �ĺ�
	// �ĺ��� Tile�� ���� ���� �޾ƿ´�.

	// ���̾� �αװ� Tile���� �޾Ƽ� ����ؾ��� ����
//	private String tileName; // �ش� Ÿ���� �̸�
//	private int tileNum; // �ش� Ÿ���� ��ȣ

	// ���̾� �αװ� CityTile���� �޾Ƽ� ����ؾ��� ����

	// ���Ž� �ʿ��� ��
	private int[] isPurchased = {0}; // �� �����

	private int priceAll; // ��ü ���� ���
	private int priceLand; // ����

	// ���ݽ� �ʿ��� ��
//	private String landOwner; // ������ �÷��̾�
	private int fine; // ����� priceAll * 2

	public DialogIsland(int playerMoney, String id) {
		this.id = id;
		this.playerMoney = playerMoney;
		
		init();
		setting();
		batch();
		listener();

		
		
		setVisible(true);
	}

	@Override
	public void init() {

		checkLand = new JCheckBox();

		labelText = new JLabel(MarbleClient.TILE.getTileName());
		laLand = new JLabel("�� ����: " + MarbleClient.TILE.getPriceLand() + "");
		laAll = new JLabel("�� ���� ������ : 0�� �Դϴ�.");

		btnPurchased = new JButton("�����ϱ�");
		btnCancel = new JButton("����ϱ�");

		panelLand = new JPanel();
		panelMenu = new JPanel();
		panelBtn = new JPanel();
		
		myItemListener = new MyItemListener();
	}

	@Override
	public void setting() {
		setSize(250, 250);
		setUndecorated(true);
		setLocationRelativeTo(null);
		
		setModal(true);

		panelMenu.setBackground(Color.LIGHT_GRAY);
		panelMenu.setLayout(new BorderLayout());
		panelLand.setLayout(new GridLayout(1, 2));

		labelText.setHorizontalAlignment(JLabel.CENTER);
		laLand.setHorizontalAlignment(JLabel.CENTER);
		laAll.setHorizontalAlignment(JLabel.CENTER);
		
		if(playerMoney > MarbleClient.TILE.getPriceLand()) {
			checkLand.setEnabled(true);
		}else {
			checkLand.setEnabled(false);
			laAll.setText("���� �����ݾ����� ������ �Ұ����մϴ�.");
		}
		
		checkDisable();
	}

	@Override
	public void batch() {		
		panelBtn.add(btnPurchased);
		panelBtn.add(btnCancel);

		panelLand.add(laLand);
		panelLand.add(checkLand);

		checkLand.addItemListener(myItemListener);

		panelMenu.add(panelLand, BorderLayout.CENTER);
		panelMenu.add(laAll, BorderLayout.SOUTH);

		add(labelText, BorderLayout.NORTH);
		add(panelMenu, BorderLayout.CENTER);
		add(panelBtn, BorderLayout.SOUTH);
	}

	@Override
	public void listener() {

		//
		btnPurchased.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fine = (MarbleClient.TILE.getPriceAll() + priceAll) * 7;
				MarbleClient.TILE.setPriceAll(MarbleClient.TILE.getPriceAll() + priceAll);
				MarbleClient.TILE.setLandOwner(id);
				MarbleClient.TILE.setIsPurchased(isPurchased);
				MarbleClient.TILE.setFine(fine); // �ӽð�

				setVisible(false);
				MarbleClient.isDialogIsland = true;
			}
		});

		// ���Ծ��ϰ� ���̾�α�â ����
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);

			}
		});
	}

	class MyItemListener implements ItemListener {
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				if (e.getItem() == checkLand) {
					priceAll = priceAll + MarbleClient.TILE.getPriceLand();
					isPurchased[0] = 1;
					
					btnPurchased.setVisible(true);
				}
			} else {
				if (e.getItem() == checkLand) {
					priceAll = priceAll - MarbleClient.TILE.getPriceLand();
					isPurchased[0] = 0;
					
					btnPurchased.setVisible(false);
				}
			}
			laAll.setText(",�� ���� ������ : " + priceAll + "�� �Դϴ�.");
		}
	}
	private void checkDisable() {
		if (MarbleClient.TILE.getIsPurchased()[0] == 1) {
			isPurchased[0] = 1;
			checkLand.setVisible(false);
		}
	}
}
