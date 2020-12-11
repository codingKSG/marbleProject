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
	
	private String id; // �ش� ���� ���� �÷��̾� id

	private JLabel textLabel, allLa, landLa;
	private JPanel menuPanel, btnPanel, landPanel;
	private JButton purchasedBtn, cancelBtn;
	private JCheckBox landCheck;

	private MyItemListener myItemListener;
	
	// Tile�� ���� ���� �޾ƿ� ���
	// TileNum�� ���ؼ� Tile�� �ĺ�
	// �ĺ��� Tile�� ���� ���� �޾ƿ´�.

	// ���̾� �αװ� Tile���� �޾Ƽ� ����ؾ��� ����
	private String tileName; // �ش� Ÿ���� �̸�
	private int tileNum; // �ش� Ÿ���� ��ȣ

	// ���̾� �αװ� CityTile���� �޾Ƽ� ����ؾ��� ����

	// ���Ž� �ʿ��� ��
	private int isPurchased; // �� �����

	private int priceAll; // ��ü ���� ���
	private int priceLand; // ����

	// ���ݽ� �ʿ��� ��
	private String landOwner; // ������ �÷��̾�
	private int fine; // ����� priceAll * 1.2

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

		textLabel = new JLabel(tileName + " ��");
		landLa = new JLabel(priceLand + "");
		allLa = new JLabel("�� ���� ������ : 0�� �Դϴ�.");

		purchasedBtn = new JButton("�����ϱ�");
		cancelBtn = new JButton("����ϱ�");

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

		// ���Ծ��ϰ� ���̾�α�â ����
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
			allLa.setText("�� ���� ������ : " + priceAll + "�� �Դϴ�.");
		}
	}

	public static void main(String[] args) {
		new DiallogIsland("test");
	}
}
