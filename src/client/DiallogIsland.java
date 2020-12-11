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

public class DiallogIsland extends JFrame implements JFrameSet {
	private DiallogIsland diallogIsland = this;
	private final static String TAG = "DiallogIsland : ";
	
	private String id; // 해당 땅을 밟은 플레이어 id

	private JLabel textLabel, allLa, landLa;
	private JPanel menuPanel, btnPanel, landPanel;
	private JButton purchasedBtn, cancelBtn;
	private JCheckBox landCheck;

	private MyItemListener myItemListener;
	
	// Tile의 상태 값을 받아올 방법
	// TileNum을 통해서 Tile을 식별
	// 식별된 Tile의 상태 값을 받아온다.

	// 다이얼 로그가 Tile에게 받아서 출력해야할 값들
	private String tileName; // 해당 타일의 이름
	private int tileNum; // 해당 타일의 번호

	// 다이얼 로그가 CityTile에게 받아서 출력해야할 값들

	// 구매시 필요한 값
	private int isPurchased; // 땅 샀는지

	private int priceAll; // 전체 구매 비용
	private int priceLand; // 땅값

	// 벌금시 필요한 값
	private String landOwner; // 소유한 플레이어
	private int fine; // 통행료 priceAll * 1.2

	public DiallogIsland(String id) {
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

		textLabel = new JLabel(tileName + " 섬");
		landLa = new JLabel(priceLand + "");
		allLa = new JLabel("총 구입 가격은 : 0원 입니다.");

		purchasedBtn = new JButton("구입하기");
		cancelBtn = new JButton("취소하기");

		landPanel = new JPanel();
		menuPanel = new JPanel();
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
		landPanel.setLayout(new GridLayout(1, 2));

		textLabel.setHorizontalAlignment(JLabel.CENTER);
		landLa.setHorizontalAlignment(JLabel.CENTER);
		allLa.setHorizontalAlignment(JLabel.CENTER);
	}

	@Override
	public void batch() {		
		btnPanel.add(purchasedBtn);
		btnPanel.add(cancelBtn);

		landPanel.add(landLa);
		landPanel.add(landCheck);

		landCheck.addItemListener(myItemListener);

		menuPanel.add(landPanel, BorderLayout.CENTER);
		menuPanel.add(allLa, BorderLayout.SOUTH);

		add(textLabel, BorderLayout.NORTH);
		add(menuPanel, BorderLayout.CENTER);
		add(btnPanel, BorderLayout.SOUTH);
	}

	@Override
	public void listener() {

		//
		purchasedBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				landOwner = id;
			}
		});

		// 구입안하고 다이얼로그창 끄기
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
			} else {
				if (e.getItem() == landCheck)
					priceAll = priceAll - priceLand;
			}
			allLa.setText("총 구입 가격은 : " + priceAll + "원 입니다.");
		}
	}

	public static void main(String[] args) {
		new DiallogIsland("test");
	}
}
