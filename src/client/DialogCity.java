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

public class DialogCity extends JFrame implements JFrameSet {
	private DialogCity diallogCity = this;
	private final static String TAG = "DiallogCity : ";
	
	private String id; // 해당 땅을 밟은 플레이어 id
	
	private JLabel labelText, laAll, laLand, laHouse, laBuilding, laHotel;
	private JPanel purchasedMenu, panelMenu, panelBtn, panelLand, panelHouse, panelBuilding, panelHotel;
	private JButton btnPurchased, btnCancel;
	private JCheckBox checkLand, checkHouse, checkBuilding, checkHotel;

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
	private int fine;
//	private int priceLand; // 땅값
//	private int priceHouse; // 집값
//	private int priceBuilding; // 빌딩값
//	private int priceHotel; // 호텔값

	// 벌금시 필요한 값
//	private String landOwner; // 소유한 플레이어
//	private int fine; // 통행료 priceAll * 1.2

	public DialogCity(String id) {
		this.id = id;

		init();
		setting();
		batch();
		listener();
		
		setVisible(true);
	}
	
	@Override
	public void init() {
		checkLand = new JCheckBox();
		checkHouse = new JCheckBox();
		checkBuilding = new JCheckBox();
		checkHotel = new JCheckBox();

		labelText = new JLabel(MarbleClient.TILE.getTileName() + " 시티");
		laLand = new JLabel("땅 가격: " + MarbleClient.TILE.getPriceLand() + "");
		laHouse = new JLabel("집 가격: " + MarbleClient.TILE.getPriceHouse() + "");
		laBuilding = new JLabel("빌딩 가격: " + MarbleClient.TILE.getPriceBuilding() + "");
		laHotel = new JLabel("호텔 가격: " + MarbleClient.TILE.getPriceHotel() + "");
		laAll = new JLabel("총 구입 가격은 : 0원 입니다.");

		btnPurchased = new JButton("구입하기");
		btnCancel = new JButton("취소하기");

		panelLand = new JPanel();
		panelHouse = new JPanel();
		panelBuilding = new JPanel();
		panelHotel = new JPanel();
		panelMenu = new JPanel();
		purchasedMenu = new JPanel();
		panelBtn = new JPanel();
		
		myItemListener = new MyItemListener();
	}

	@Override
	public void setting() {
		setSize(250, 250);
		setUndecorated(true);
		setLocationRelativeTo(null);

		panelMenu.setBackground(Color.LIGHT_GRAY);
		
		panelMenu.setLayout(new BorderLayout());
		purchasedMenu.setLayout(new GridLayout(4, 1));
		panelLand.setLayout(new GridLayout(1, 2));
		panelHouse.setLayout(new GridLayout(1, 2));
		panelBuilding.setLayout(new GridLayout(1, 2));
		panelHotel.setLayout(new GridLayout(1, 2));

		labelText.setHorizontalAlignment(JLabel.CENTER);
		laLand.setHorizontalAlignment(JLabel.CENTER);
		laHouse.setHorizontalAlignment(JLabel.CENTER);
		laBuilding.setHorizontalAlignment(JLabel.CENTER);
		laHotel.setHorizontalAlignment(JLabel.CENTER);
		laAll.setHorizontalAlignment(JLabel.CENTER);
		
		btnPurchased.setVisible(false);

		checkDisable();
	}

	@Override
	public void batch() {		
		panelBtn.add(btnPurchased);
		panelBtn.add(btnCancel);

		panelLand.add(laLand);
		panelLand.add(checkLand);

		panelHouse.add(laHouse);
		panelHouse.add(checkHouse);

		panelBuilding.add(laBuilding);
		panelBuilding.add(checkBuilding);

		panelHotel.add(laHotel);
		panelHotel.add(checkHotel);
		
		checkLand.addItemListener(myItemListener);
		checkHouse.addItemListener(myItemListener);
		checkBuilding.addItemListener(myItemListener);
		checkHotel.addItemListener(myItemListener);

		purchasedMenu.add(panelLand);
		purchasedMenu.add(panelHouse);
		purchasedMenu.add(panelBuilding);
		purchasedMenu.add(panelHotel);
		
		panelMenu.add(purchasedMenu, BorderLayout.CENTER);
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
				fine = (MarbleClient.TILE.getPriceAll() + priceAll) * 2;
				MarbleClient.TILE.setPriceAll(MarbleClient.TILE.getPriceAll() + priceAll);
				MarbleClient.TILE.setLandOwner(id);
				MarbleClient.TILE.setIsPurchased(isPurchased);
				MarbleClient.TILE.setFine(fine); // 임시값

				setVisible(false);
				MarbleClient.isDialogCity = true;
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
				if (e.getItem() == checkLand) {
					priceAll = priceAll + MarbleClient.TILE.getPriceLand();
					isPurchased[0] = 1;
					
					btnPurchased.setVisible(true);
				}
				else if (e.getItem() == checkHouse) {
					priceAll = priceAll + MarbleClient.TILE.getPriceHouse();
					isPurchased[1] = 1;
				}
				else if (e.getItem() == checkBuilding) {
					priceAll = priceAll + MarbleClient.TILE.getPriceBuilding();
					isPurchased[2] = 1;
				}
				else {
					priceAll = priceAll + MarbleClient.TILE.getPriceHotel();
					isPurchased[3] = 1;
				}
			} else {
				if (e.getItem() == checkLand) {
					priceAll = priceAll - MarbleClient.TILE.getPriceLand();
					isPurchased[0] = 0;
					
					btnPurchased.setVisible(false);
				}
				else if (e.getItem() == checkHouse) {
					priceAll = priceAll - MarbleClient.TILE.getPriceHouse();
					isPurchased[1] = 0;
				}
				else if (e.getItem() == checkBuilding) {
					priceAll = priceAll - MarbleClient.TILE.getPriceBuilding();
					isPurchased[2] = 0;
				}
				else {
					priceAll = priceAll - MarbleClient.TILE.getPriceHotel();
					isPurchased[3] = 0;
				}
			}
			laAll.setText(",총 구입 가격은 : " + priceAll + "원 입니다.");
		}
	}
	
	private void checkDisable() {
		if (MarbleClient.TILE.getIsPurchased()[0] == 1) {
			isPurchased[0] = 1;
			checkLand.setVisible(false);
			btnPurchased.setVisible(true);
		}
		if (MarbleClient.TILE.getIsPurchased()[1] == 1) {
			isPurchased[1] = 1;
			checkHouse.setVisible(false);
		}
		if (MarbleClient.TILE.getIsPurchased()[2] == 1) {
			isPurchased[2] = 1;
			checkBuilding.setVisible(false);
		}
		if (MarbleClient.TILE.getIsPurchased()[3] == 1) {
			isPurchased[3] = 1;
			checkHotel.setVisible(false);
		}
	}
}
