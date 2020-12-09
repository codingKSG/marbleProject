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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import protocol.JFrameSet;

public class DiallogCity extends JFrame implements JFrameSet {
	private DiallogCity diallogCity = this;
	private final static String TAG = "DiallogCity : ";
	
	private String id;

	private GridBagConstraints gridBagCon;
	private JLabel textLabel, allLa, landLa, houseLa, buildingLa, hotelLa;
	private JPanel purchasedMenu, btnPanel, landPanel, housePanel, buildingPanel, hotelPanel;
	private JButton purchasedBtn, cancelBtn;
	private JCheckBox landCheck, houseCheck, buildingCheck, hotelCheck;

	private MyItemListener myItemListener;
	
	// Tile�� ���� ���� �޾ƿ� ���
	// TileNum�� ���ؼ� Tile�� �ĺ�
	// �ĺ��� Tile�� ���� ���� �޾ƿ´�.

	// ���̾� �αװ� Tile���� �޾Ƽ� ����ؾ��� ����
	private int tileType;
	private String tileName;
	private int tileNum;

	// ���̾� �αװ� CityTile���� �޾Ƽ� ����ؾ��� ����

	// ���Ž� �ʿ��� ��
	private int isPurchased;

	private int priceAll;
	private int priceLand;
	private int priceHouse;
	private int priceBuilding;
	private int priceHotel;

	// ���ݽ� �ʿ��� ��
	private String landOwner;
	private int fine;

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
		gridBagCon = new GridBagConstraints();

		landCheck = new JCheckBox();
		houseCheck = new JCheckBox();
		buildingCheck = new JCheckBox();
		hotelCheck = new JCheckBox();

		textLabel = new JLabel(tileName + " ��Ƽ");
		landLa = new JLabel(priceLand + "");
		houseLa = new JLabel(priceHouse + "");
		buildingLa = new JLabel(priceBuilding + "");
		hotelLa = new JLabel(priceHotel + "");
		allLa = new JLabel("�� ���� ������ : 0�� �Դϴ�.");

		purchasedBtn = new JButton("�����ϱ�");
		cancelBtn = new JButton("����ϱ�");

		landPanel = new JPanel();
		housePanel = new JPanel();
		buildingPanel = new JPanel();
		hotelPanel = new JPanel();
		purchasedMenu = new JPanel();
		btnPanel = new JPanel();
		
		myItemListener = new MyItemListener();
	}

	@Override
	public void setting() {
		setTitle(id + ": ��Ƽ Ÿ��");
		setSize(250, 250);
		setUndecorated(true);
		setLocationRelativeTo(null);

		purchasedMenu.setBackground(Color.LIGHT_GRAY);
		purchasedMenu.setLayout(new GridLayout(5, 1));
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
		btnPanel.add(purchasedBtn);
		btnPanel.add(cancelBtn);

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
		purchasedMenu.add(allLa);

		add(textLabel, BorderLayout.NORTH);
		add(purchasedMenu, BorderLayout.CENTER);
		add(btnPanel, BorderLayout.SOUTH);
	}

	@Override
	public void listener() {

		purchasedBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(priceAll);
				System.out.println(priceLand);
				System.out.println(priceHouse);
				System.out.println(priceBuilding);
				System.out.println(priceHotel);
			}
		});

		cancelBtn.addActionListener(new ActionListener() {

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
				if (e.getItem() == landCheck)
					priceAll = priceAll + priceLand;
				else if (e.getItem() == houseCheck)
					priceAll = priceAll + priceHouse;
				else if (e.getItem() == buildingCheck)
					priceAll = priceAll + priceBuilding;
				else
					priceAll = priceAll + priceHotel;
			} else {
				if (e.getItem() == landCheck)
					priceAll = priceAll - priceLand;
				else if (e.getItem() == houseCheck)
					priceAll = priceAll - priceHouse;
				else if (e.getItem() == buildingCheck)
					priceAll = priceAll - priceBuilding;
				else
					priceAll = priceAll - priceHotel;
			}
			allLa.setText("�� ���� ������ : " + priceAll + "�� �Դϴ�.");
		}
	}

	public static void main(String[] args) {
		new DiallogCity("test");
	}
}
