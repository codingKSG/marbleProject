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

import object.CityTile;
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
//	private int priceLand; // ����
//	private int priceHouse; // ����
//	private int priceBuilding; // ������
//	private int priceHotel; // ȣ�ڰ�

	// ���ݽ� �ʿ��� ��
//	private String landOwner; // ������ �÷��̾�
//	private int fine; // ����� priceAll * 1.2

	public DiallogCity(String id, CityTile cityTile) {
		this.id = id;
		MarbleClient.cityTile = cityTile;

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

		textLabel = new JLabel(MarbleClient.cityTile.getTileName() + " ��Ƽ");
		landLa = new JLabel(MarbleClient.cityTile.getPriceLand() + "");
		houseLa = new JLabel(MarbleClient.cityTile.getPriceHouse() + "");
		buildingLa = new JLabel(MarbleClient.cityTile.getPriceBuilding() + "");
		hotelLa = new JLabel(MarbleClient.cityTile.getPriceHotel() + "");
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
				MarbleClient.cityTile.setPriceAll(priceAll);
				MarbleClient.cityTile.setLandOwner(id);
				MarbleClient.cityTile.setIsPurchased(isPurchased);
				
				setVisible(false);
				
				System.out.println(MarbleClient.cityTile.getPriceAll());
				System.out.println(MarbleClient.cityTile.getLandOwner());
				System.out.println(MarbleClient.cityTile.getIsPurchased()[0]);
				System.out.println(MarbleClient.cityTile.getIsPurchased()[1]);
				System.out.println(MarbleClient.cityTile.getIsPurchased()[2]);
				System.out.println(MarbleClient.cityTile.getIsPurchased()[3]);
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
					priceAll = priceAll + MarbleClient.cityTile.getPriceLand();
					isPurchased[0] = 1;
				}
				else if (e.getItem() == houseCheck) {
					priceAll = priceAll + MarbleClient.cityTile.getPriceHouse();
					isPurchased[1] = 1;
				}
				else if (e.getItem() == buildingCheck) {
					priceAll = priceAll + MarbleClient.cityTile.getPriceBuilding();
					isPurchased[2] = 1;
				}
				else {
					priceAll = priceAll + MarbleClient.cityTile.getPriceHotel();
					isPurchased[3] = 1;
				}
			} else {
				if (e.getItem() == landCheck) {
					priceAll = priceAll - MarbleClient.cityTile.getPriceLand();
					isPurchased[0] = 0;
				}
				else if (e.getItem() == houseCheck) {
					priceAll = priceAll - MarbleClient.cityTile.getPriceHouse();
					isPurchased[1] = 0;
				}
				else if (e.getItem() == buildingCheck) {
					priceAll = priceAll - MarbleClient.cityTile.getPriceBuilding();
					isPurchased[2] = 0;
				}
				else {
					priceAll = priceAll - MarbleClient.cityTile.getPriceHotel();
					isPurchased[3] = 0;
				}
			}
			allLa.setText(",�� ���� ������ : " + priceAll + "�� �Դϴ�.");
		}
	}
	
	void checkDisable() {
		if(MarbleClient.cityTile.getIsPurchased()[0] == 1) {
			
			landCheck.setEnabled(false);
		}
	}
	
	public static void main(String[] args) {		
		new DiallogCity("test", MarbleClient.cityTile);
		
	}

	
}
