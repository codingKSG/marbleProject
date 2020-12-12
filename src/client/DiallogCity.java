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
	
	private String id; // 해당 땅을 밟은 플레이어 id
	
	private JLabel textLabel, allLa, landLa, houseLa, buildingLa, hotelLa;
	private JPanel purchasedMenu, menuPanel, btnPanel, landPanel, housePanel, buildingPanel, hotelPanel;
	private JButton btnPurchased, btnCancel;
	private JCheckBox landCheck, houseCheck, buildingCheck, hotelCheck;

	private MyItemListener myItemListener;
	
	// Tile의 상태 값을 받아올 방법
	// TileNum을 통해서 Tile을 식별
	// 식별된 Tile의 상태 값을 받아온다.

	// 다이얼 로그가 Tile에게 받아서 출력해야할 값들
//	private String tileName; // 해당 타일의 이름
//	private int tileNum; // 해당 타일의 번호
	
	// 다이얼 로그가 CityTile에게 받아서 출력해야할 값들

	// 구매시 필요한 값
	private int[] isPurchased = {0, 0, 0, 0}; // 땅/ 집/ 빌딩/ 호텔 샀는지
	
	

	private int priceAll; // 전체 구매 비용
//	private int priceLand; // 땅값
//	private int priceHouse; // 집값
//	private int priceBuilding; // 빌딩값
//	private int priceHotel; // 호텔값

	// 벌금시 필요한 값
//	private String landOwner; // 소유한 플레이어
//	private int fine; // 통행료 priceAll * 1.2

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

		textLabel = new JLabel(MarbleClient.cityTile.getTileName() + " 시티");
		landLa = new JLabel(MarbleClient.cityTile.getPriceLand() + "");
		houseLa = new JLabel(MarbleClient.cityTile.getPriceHouse() + "");
		buildingLa = new JLabel(MarbleClient.cityTile.getPriceBuilding() + "");
		hotelLa = new JLabel(MarbleClient.cityTile.getPriceHotel() + "");
		allLa = new JLabel("총 구입 가격은 : 0원 입니다.");

		btnPurchased = new JButton("구입하기");
		btnCancel = new JButton("취소하기");

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

		// 구입안하고 다이얼로그창 끄기
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
			allLa.setText(",총 구입 가격은 : " + priceAll + "원 입니다.");
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
