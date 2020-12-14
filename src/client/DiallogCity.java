package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import protocol.JFrameSet;

public class DiallogCity extends JFrame implements JFrameSet {
	private DiallogCity diallogCity = this;
	private final static String TAG = "DiallogCity : ";
	
	private String id; // �ش� ���� ���� �÷��̾� id
	
	private JLabel textLabel, allLa, landLa, houseLa, buildingLa, hotelLa;
	private JPanel purchasedMenu, menuPanel, btnPanel, landPanel, housePanel, buildingPanel, hotelPanel;
	private JButton btnPurchased, btnCancel;
	private JCheckBox landCheck, houseCheck, buildingCheck, hotelCheck;

	private MyItemListener myItemListener;
	
	// Tile�� ���� ���� �޾ƿ� ���
	// TileNum�� ���ؼ� Tile�� �ĺ�
	// �ĺ��� Tile�� ���� ���� �޾ƿ´�.

	// ���̾� �αװ� Tile���� �޾Ƽ� ����ؾ��� ����
//	private String tileName; // �ش� Ÿ���� �̸�
//	private int tileNum; // �ش� Ÿ���� ��ȣ
	
	// ���̾� �αװ� CityTile���� �޾Ƽ� ����ؾ��� ����

	// ���Ž� �ʿ��� ��
	private int[] isPurchased = {0, 0, 0, 0}; // ��/ ��/ ����/ ȣ�� �����

	private int priceAll; // ��ü ���� ���
	private int fine;
//	private int priceLand; // ����
//	private int priceHouse; // ����
//	private int priceBuilding; // ������
//	private int priceHotel; // ȣ�ڰ�

	// ���ݽ� �ʿ��� ��
//	private String landOwner; // ������ �÷��̾�
//	private int fine; // ����� priceAll * 1.2

	public DiallogCity(String id) {
		this.id = id;

		init();
		setting();
		batch();
		listener();
		
		setVisible(true);
	}
	
	@Override
	public void init() {
		landCheck = new JCheckBox();
		houseCheck = new JCheckBox();
		buildingCheck = new JCheckBox();
		hotelCheck = new JCheckBox();

		textLabel = new JLabel(MarbleClient.TILE.getTileName() + " ��Ƽ");
		landLa = new JLabel("�� ����: " + MarbleClient.TILE.getPriceLand() + "");
		houseLa = new JLabel("�� ����: " + MarbleClient.TILE.getPriceHouse() + "");
		buildingLa = new JLabel("���� ����: " + MarbleClient.TILE.getPriceBuilding() + "");
		hotelLa = new JLabel("ȣ�� ����: " + MarbleClient.TILE.getPriceHotel() + "");
		allLa = new JLabel("�� ���� ������ : 0�� �Դϴ�.");

		btnPurchased = new JButton("�����ϱ�");
		btnCancel = new JButton("����ϱ�");

		landPanel = new JPanel();
		housePanel = new JPanel();
		buildingPanel = new JPanel();
		hotelPanel = new JPanel();
		menuPanel = new JPanel();
		purchasedMenu = new JPanel();
		btnPanel = new JPanel();
		
		myItemListener = new MyItemListener();
	}

	@Override
	public void setting() {
		setSize(250, 250);
		setUndecorated(true);
		setLocationRelativeTo(null);

		menuPanel.setBackground(Color.LIGHT_GRAY);
		
		menuPanel.setLayout(new BorderLayout());
		purchasedMenu.setLayout(new GridLayout(4, 1));
		landPanel.setLayout(new GridLayout(1, 2));
		housePanel.setLayout(new GridLayout(1, 2));
		buildingPanel.setLayout(new GridLayout(1, 2));
		hotelPanel.setLayout(new GridLayout(1, 2));

		textLabel.setHorizontalAlignment(JLabel.CENTER);
		landLa.setHorizontalAlignment(JLabel.CENTER);
		houseLa.setHorizontalAlignment(JLabel.CENTER);
		buildingLa.setHorizontalAlignment(JLabel.CENTER);
		hotelLa.setHorizontalAlignment(JLabel.CENTER);
		allLa.setHorizontalAlignment(JLabel.CENTER);
		
		btnPurchased.setVisible(false);

		checkDisable();
	}

	@Override
	public void batch() {		
		btnPanel.add(btnPurchased);
		btnPanel.add(btnCancel);

		landPanel.add(landLa);
		landPanel.add(landCheck);

		housePanel.add(houseLa);
		housePanel.add(houseCheck);

		buildingPanel.add(buildingLa);
		buildingPanel.add(buildingCheck);

		hotelPanel.add(hotelLa);
		hotelPanel.add(hotelCheck);
		
		landCheck.addItemListener(myItemListener);
		houseCheck.addItemListener(myItemListener);
		buildingCheck.addItemListener(myItemListener);
		hotelCheck.addItemListener(myItemListener);

		purchasedMenu.add(landPanel);
		purchasedMenu.add(housePanel);
		purchasedMenu.add(buildingPanel);
		purchasedMenu.add(hotelPanel);
		
		menuPanel.add(purchasedMenu, BorderLayout.CENTER);
		menuPanel.add(allLa, BorderLayout.SOUTH);

		add(textLabel, BorderLayout.NORTH);
		add(menuPanel, BorderLayout.CENTER);
		add(btnPanel, BorderLayout.SOUTH);
	}

	@Override
	public void listener() {

		//
		btnPurchased.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fine = priceAll * 2;
				MarbleClient.TILE.setPriceAll(priceAll);
				MarbleClient.TILE.setLandOwner(id);
				MarbleClient.TILE.setIsPurchased(isPurchased);
				MarbleClient.TILE.setFine(fine); // �ӽð�

				setVisible(false);
				MarbleClient.isDialogCity = true;
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
				if (e.getItem() == landCheck) {
					priceAll = priceAll + MarbleClient.TILE.getPriceLand();
					isPurchased[0] = 1;
					
					btnPurchased.setVisible(true);
				}
				else if (e.getItem() == houseCheck) {
					priceAll = priceAll + MarbleClient.TILE.getPriceHouse();
					isPurchased[1] = 1;
				}
				else if (e.getItem() == buildingCheck) {
					priceAll = priceAll + MarbleClient.TILE.getPriceBuilding();
					isPurchased[2] = 1;
				}
				else {
					priceAll = priceAll + MarbleClient.TILE.getPriceHotel();
					isPurchased[3] = 1;
				}
			} else {
				if (e.getItem() == landCheck) {
					priceAll = priceAll - MarbleClient.TILE.getPriceLand();
					isPurchased[0] = 0;
					
					btnPurchased.setVisible(false);
				}
				else if (e.getItem() == houseCheck) {
					priceAll = priceAll - MarbleClient.TILE.getPriceHouse();
					isPurchased[1] = 0;
				}
				else if (e.getItem() == buildingCheck) {
					priceAll = priceAll - MarbleClient.TILE.getPriceBuilding();
					isPurchased[2] = 0;
				}
				else {
					priceAll = priceAll - MarbleClient.TILE.getPriceHotel();
					isPurchased[3] = 0;
				}
			}
			allLa.setText(",�� ���� ������ : " + priceAll + "�� �Դϴ�.");
		}
	}
	
	private void checkDisable() {
		if (MarbleClient.TILE.getIsPurchased()[0] == 1) {
			isPurchased[0] = 1;
			landCheck.setVisible(false);
			btnPurchased.setVisible(true);
		}
		if (MarbleClient.TILE.getIsPurchased()[1] == 1) {
			isPurchased[1] = 1;
			houseCheck.setVisible(false);
		}
		if (MarbleClient.TILE.getIsPurchased()[2] == 1) {
			isPurchased[2] = 1;
			buildingCheck.setVisible(false);
		}
		if (MarbleClient.TILE.getIsPurchased()[3] == 1) {
			isPurchased[3] = 1;
			hotelCheck.setVisible(false);
		}

	}
}
