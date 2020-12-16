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
	
	private String id; // 해당 땅을 밟은 플레이어 id
	private int playerMoney;

	private JLabel labelText, laAll, laLand;
	private JPanel panelMenu, panelBtn, panelLand;
	private JButton btnPurchased, btnCancel;
	private JCheckBox checkLand;

	private MyItemListener myItemListener;
	
	// Tile의 상태 값을 받아올 방법
	// TileNum을 통해서 Tile을 식별
	// 식별된 Tile의 상태 값을 받아온다.

	// 다이얼 로그가 Tile에게 받아서 출력해야할 값들
//	private String tileName; // 해당 타일의 이름
//	private int tileNum; // 해당 타일의 번호

	// 다이얼 로그가 CityTile에게 받아서 출력해야할 값들

	// 구매시 필요한 값
	private int[] isPurchased = {0}; // 땅 샀는지

	private int priceAll; // 전체 구매 비용
	private int priceLand; // 땅값

	// 벌금시 필요한 값
//	private String landOwner; // 소유한 플레이어
	private int fine; // 통행료 priceAll * 2

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
		laLand = new JLabel("땅 가격: " + MarbleClient.TILE.getPriceLand() + "");
		laAll = new JLabel("총 구입 가격은 : 0원 입니다.");

		btnPurchased = new JButton("구입하기");
		btnCancel = new JButton("취소하기");

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
			laAll.setText("현재 보유금액으로 구입이 불가능합니다.");
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
				MarbleClient.TILE.setFine(fine); // 임시값

				setVisible(false);
				MarbleClient.isDialogIsland = true;
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
			} else {
				if (e.getItem() == checkLand) {
					priceAll = priceAll - MarbleClient.TILE.getPriceLand();
					isPurchased[0] = 0;
					
					btnPurchased.setVisible(false);
				}
			}
			laAll.setText(",총 구입 가격은 : " + priceAll + "원 입니다.");
		}
	}
	private void checkDisable() {
		if (MarbleClient.TILE.getIsPurchased()[0] == 1) {
			isPurchased[0] = 1;
			checkLand.setVisible(false);
		}
	}
}
