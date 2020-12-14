package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.google.gson.Gson;

import object.CityTile;
import object.IsLandTile;
import object.SpecialTile;
import object.Tile;
import protocol.JFrameSet;
import protocol.Protocol;
import protocol.RequestDto;

public class MarbleClient extends JFrame implements JFrameSet {

	private MarbleClient marbleClient = this;
	private final static String TAG = "MarbleClient : ";
	static Tile TILE;
	
	static int nowPrice;
	static boolean isDialogCity = false;
	static boolean isDialogFine = false;
	private int[] allPurcharsed = { 1, 1, 1, 1 };

	private ClientPlayerThread cpt;
	private Socket socket;
	private String id;
	private int dice1;
	private int dice2;
	private int nowPlayerTile = 0;
	private int playerX = 240;
	private int playerY = 240;
	private String playerImageSource;
	private boolean isTurn = false; // ���� �÷��̾��� ������
	boolean isPlaying = true; // �÷��̾� ���� ����

	private JLabel board0, board1, board2, board3, board4, board5, board6, board7, board8, board9, board10, board11,
			board12, board13, board14, board15, board16, board17, board18, board19, board20, board21, board22, board23;
	private JLabel board1CityName, board3CityName, board4CityName, board5CityName; // Line0 �����̸���
	private JLabel board1Centerla, board3Centerla, board4Centerla, board5Centerla; // Line0 ���Ͷ�
	private JLabel board1BldImage, board3BldImage, board4BldImage, board5BldImage; // Line0 �ǹ��̹�����

	private RotatedLabel board7CityName, board8CityName, board9CityName, board11CityName; // Line1 �����̸���
	private RotatedLabel board7Centerla, board8Centerla, board9Centerla, board11Centerla; // Line1 ���Ͷ�
	private JLabel board7BldImage, board8BldImage, board9BldImage, board11BldImage; // Line1 �ǹ��̹�����

	private JLabel board13CityName, board14CityName, board15CityName, board17CityName; // Line2 �����̸���
	private JLabel board13Centerla, board14Centerla, board15Centerla, board17Centerla; // Line2 ���Ͷ�
	private JLabel board13BldImage, board14BldImage, board15BldImage, board17BldImage; // Line2 �ǹ��̹�����

	private RotatedLabel board20CityName, board21CityName, board22CityName, board23CityName; // Line3 �����̸���
	private RotatedLabel board20Centerla, board21Centerla, board22Centerla, board23Centerla; // Line3 ���Ͷ�
	private JLabel board20BldImage, board21BldImage, board22BldImage, board23BldImage; // Line3 �ǹ��̹�����

	private ArrayList<JLabel> boardLine0, boardLine1, boardLine2, boardLine3; // 0:�ؿ��� 1:���� 2:���� 3:������

	private Container c;
	// �ֻ��������� ��ư, ���۹�ư
	private JButton btnDiceRoll, btnStart;
	// �߰� ����
	private JLabel boardCenter;
	// ������ �÷��̾�â, ä��â
	private JPanel player1Info, player2Info, player3Info, player4Info, playerChatPanel;
	private JLabel player1Img, player2Img, player3Img, player4Img;
	private JLabel player1Money, player2Money, player3Money, player4Money;
	private JLabel player1Id, player2Id, player3Id, player4Id;
	// ������ ä��â
	private ScrollPane scChatList;
	private JTextArea playerChatList;
	private JTextField playerChatField;
	// �÷��̾�(����) �̹��� ��ü - �÷������� �ƴϸ� �Ⱥ���
	private Player player1, player2, player3, player4;
	private int playerNum;

	// �ֻ��� �� �̹��� ���� ��
	private JLabel laDice1, laDice2;

	Random dice = new Random();
	private Vector<Tile> tileList; // Ÿ�� ��ü�� ��� ����
	int[] arrayinit = {0,0,0,0};

	public MarbleClient(String id) {
		this.id = id;

		connect();

		init();

		setting();

		batch();

		listener();

		setVisible(true);
	}

	@Override
	public void init() {
		// ���κ� ��Ƽ/���Ϸ��� Ÿ���� ��� ����Ʈ
		boardLine0 = new ArrayList<>();
		boardLine1 = new ArrayList<>();
		boardLine2 = new ArrayList<>();
		boardLine3 = new ArrayList<>();

		// ���� new
		board0 = new JLabel();
		board1 = new JLabel();
		board2 = new JLabel();
		board3 = new JLabel();
		board4 = new JLabel();
		board5 = new JLabel();
		board6 = new JLabel();
		board7 = new JLabel();
		board8 = new JLabel();
		board9 = new JLabel();
		board10 = new JLabel();
		board11 = new JLabel();
		board12 = new JLabel();
		board13 = new JLabel();
		board14 = new JLabel();
		board15 = new JLabel();
		board16 = new JLabel();
		board17 = new JLabel();
		board18 = new JLabel();
		board19 = new JLabel();
		board20 = new JLabel();
		board21 = new JLabel();
		board22 = new JLabel();
		board23 = new JLabel();
		// �Ʒ� ����( board2�� ������̶� ���� )
		boardLine0.add(board1);
		boardLine0.add(board3);
		boardLine0.add(board4);
		boardLine0.add(board5);
		// ���� ����( board10�� ������̶� ���� )
		boardLine1.add(board7);
		boardLine1.add(board8);
		boardLine1.add(board9);
		boardLine1.add(board11);
		// ���� ����( board16�� ������̶� ���� )
		boardLine2.add(board13);
		boardLine2.add(board14);
		boardLine2.add(board15);
		boardLine2.add(board17);
		// ������ ����( board19�� ������̶� ���� )
		boardLine3.add(board20);
		boardLine3.add(board21);
		boardLine3.add(board22);
		boardLine3.add(board23);

		// ���庰 ���� �̸�
		board1CityName = new JLabel("ȫ��");
		board3CityName = new JLabel("����");
		board4CityName = new JLabel("���ֵ�");
		board5CityName = new JLabel("ī�̷�");
		board7CityName = new RotatedLabel("�Ͽ���");
		board8CityName = new RotatedLabel("�õ��");
		board9CityName = new RotatedLabel("���Ŀ��");
		board11CityName = new RotatedLabel("����");
		board13CityName = new JLabel("��ũ��");
		board14CityName = new JLabel("������");
		board15CityName = new JLabel("����");
		board17CityName = new JLabel("�θ�");
		board20CityName = new RotatedLabel("����");
		board21CityName = new RotatedLabel("�ĸ�");
		board22CityName = new RotatedLabel("����");
		board23CityName = new RotatedLabel("����");

		board1BldImage = new JLabel();
		board3BldImage = new JLabel();
		board4BldImage = new JLabel();
		board5BldImage = new JLabel();
		board7BldImage = new JLabel();
		board8BldImage = new JLabel();
		board9BldImage = new JLabel();
		board11BldImage = new JLabel();
		board13BldImage = new JLabel();
		board14BldImage = new JLabel();
		board15BldImage = new JLabel();
		board17BldImage = new JLabel();
		board20BldImage = new JLabel();
		board21BldImage = new JLabel();
		board22BldImage = new JLabel();
		board23BldImage = new JLabel();

		board1Centerla = new JLabel("X20");
		board3Centerla = new JLabel("X20");
		board4Centerla = new JLabel("X20");
		board5Centerla = new JLabel("X20");
		board7Centerla = new RotatedLabel("X20");
		board8Centerla = new RotatedLabel("X20");
		board9Centerla = new RotatedLabel("X20");
		board11Centerla = new RotatedLabel("X20");
		board13Centerla = new JLabel("X20");
		board14Centerla = new JLabel("X20");
		board15Centerla = new JLabel("X20");
		board17Centerla = new JLabel("X20");
		board20Centerla = new RotatedLabel("X20");
		board21Centerla = new RotatedLabel("X20");
		board22Centerla = new RotatedLabel("X20");
		board23Centerla = new RotatedLabel("X20");

		// �߰� ����
		boardCenter = new JLabel();
		// �ֻ��� �� �̹���
		laDice1 = new JLabel("");
		laDice2 = new JLabel("");
		// �ֻ��� ��ư, ���� ��ư
		btnDiceRoll = new JButton("�ֻ��� ������");
		btnStart = new JButton("���� ����");
		// ���� ����Ʈ
		c = getContentPane();
		// �÷��̾� ��ü �̹���
		player1 = new Player(695, 695, "images/img_player01.png");
		player2 = new Player(735, 695, "images/img_player02.png");
		player3 = new Player(695, 735, "images/img_player03.png");
		player4 = new Player(735, 735, "images/img_player04.png");
		// ������ �÷��̾� ����â
		player1Info = new JPanel();
		player2Info = new JPanel();
		player3Info = new JPanel();
		player4Info = new JPanel();
		// �÷��̾� ����â ����
		player1Img = new JLabel();
		player2Img = new JLabel();
		player3Img = new JLabel();
		player4Img = new JLabel();
		// �÷��̾� ����â ���̵�
		player1Id = new JLabel();
		player2Id = new JLabel();
		player3Id = new JLabel();
		player4Id = new JLabel();
		// �÷��̾� ����â ��
		player1Money = new JLabel();
		player2Money = new JLabel();
		player3Money = new JLabel();
		player4Money = new JLabel();
		// ������ ä��â
		playerChatPanel = new JPanel();
		scChatList = new ScrollPane();
		playerChatList = new JTextArea();
		playerChatField = new JTextField(20);

		tileList = new Vector<>();
		Tile T0 = new SpecialTile("����", 0, 0, 650, 650);
		Tile T1 = new CityTile("ȫ��", 1, 1, 550, 650, null, 0, arrayinit , 20, 24, 30, 36, 0);
		Tile T2 = new SpecialTile("�����", 2, 3, 450, 650);
		Tile T3 = new CityTile("����", 3, 1, 350, 650, null, 0, arrayinit, 24, 28, 34, 40, 0);
		Tile T4 = new IsLandTile("���ֵ�", 4, 2, 250, 650, null, 0, arrayinit, 45);
		Tile T5 = new CityTile("ī�̷�", 5, 1, 150, 650, null, 0, arrayinit, 27, 35, 41, 48, 0);
		Tile T6 = new SpecialTile("���ε�", 6, 3, 0, 650);
		Tile T7 = new IsLandTile("�Ͽ���", 7, 2, 0, 550, null, 0, arrayinit, 65);
		Tile T8 = new CityTile("�õ��", 8, 1, 0, 450, null, 0, arrayinit, 30, 38, 45, 52, 0);
		Tile T9 = new CityTile("���Ŀ��", 9, 1, 0, 350, null, 0, arrayinit, 32, 40, 47, 55, 0);
		Tile T10 = new SpecialTile("�����", 10, 3, 0, 250);
		Tile T11 = new CityTile("����", 11, 1, 0, 150, null, 0, arrayinit, 35, 43, 51, 59, 0);
		Tile T12 = new SpecialTile("�ø���", 12, 3, 0, 0);
		Tile T13 = new CityTile("��ũ��", 13, 1, 150, 0, null, 0, arrayinit, 37, 46, 54, 63, 0);
		Tile T14 = new CityTile("������", 14, 1, 250, 0, null, 0, arrayinit, 40, 50, 59, 68, 0);
		Tile T15 = new IsLandTile("����", 15, 2, 350, 0, null, 0, arrayinit, 80);
		Tile T16 = new SpecialTile("�����", 16, 3, 450, 0);
		Tile T17 = new CityTile("�θ�", 17, 1, 550, 0, null, 0, arrayinit, 43, 54, 65, 74, 0);
		Tile T18 = new SpecialTile("���迩��", 18, 3, 650, 0);
		Tile T19 = new SpecialTile("�����", 19, 3, 650, 150);
		Tile T20 = new CityTile("����", 20, 1, 650, 250, null, 0, arrayinit, 45, 58, 70, 79, 0);
		Tile T21 = new CityTile("�ĸ�", 21, 1, 650, 350, null, 0, arrayinit, 47, 62, 74, 84, 0);
		Tile T22 = new CityTile("����", 22, 1, 650, 450, null, 0, arrayinit, 50, 65, 79, 89, 0);
		Tile T23 = new IsLandTile("����", 23, 2, 650, 550, null, 0, arrayinit, 100);
		tileList.add(T0);
		tileList.add(T1);
		tileList.add(T2);
		tileList.add(T3);
		tileList.add(T4);
		tileList.add(T5);
		tileList.add(T6);
		tileList.add(T7);
		tileList.add(T8);
		tileList.add(T9);
		tileList.add(T10);
		tileList.add(T11);
		tileList.add(T12);
		tileList.add(T13);
		tileList.add(T14);
		tileList.add(T15);
		tileList.add(T16);
		tileList.add(T17);
		tileList.add(T18);
		tileList.add(T19);
		tileList.add(T20);
		tileList.add(T21);
		tileList.add(T22);
		tileList.add(T23);
	}

	@Override
	public void setting() {

		setTitle("Marble Client" + " : " + id);
		setSize(1000, 840);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		board0.setBorder(new LineBorder(new Color(0, 0, 0)));
		board1.setBorder(new LineBorder(new Color(0, 0, 0)));
		board2.setBorder(new LineBorder(new Color(0, 0, 0)));
		board3.setBorder(new LineBorder(new Color(0, 0, 0)));
		board4.setBorder(new LineBorder(new Color(0, 0, 0)));
		board5.setBorder(new LineBorder(new Color(0, 0, 0)));
		board6.setBorder(new LineBorder(new Color(0, 0, 0)));
		board7.setBorder(new LineBorder(new Color(0, 0, 0)));
		board8.setBorder(new LineBorder(new Color(0, 0, 0)));
		board9.setBorder(new LineBorder(new Color(0, 0, 0)));
		board10.setBorder(new LineBorder(new Color(0, 0, 0)));
		board11.setBorder(new LineBorder(new Color(0, 0, 0)));
		board12.setBorder(new LineBorder(new Color(0, 0, 0)));
		board13.setBorder(new LineBorder(new Color(0, 0, 0)));
		board14.setBorder(new LineBorder(new Color(0, 0, 0)));
		board15.setBorder(new LineBorder(new Color(0, 0, 0)));
		board16.setBorder(new LineBorder(new Color(0, 0, 0)));
		board17.setBorder(new LineBorder(new Color(0, 0, 0)));
		board18.setBorder(new LineBorder(new Color(0, 0, 0)));
		board19.setBorder(new LineBorder(new Color(0, 0, 0)));
		board20.setBorder(new LineBorder(new Color(0, 0, 0)));
		board21.setBorder(new LineBorder(new Color(0, 0, 0)));
		board22.setBorder(new LineBorder(new Color(0, 0, 0)));
		board23.setBorder(new LineBorder(new Color(0, 0, 0)));

		// ���� ���� ���̾ƿ� = null
		for (int i = 0; i < boardLine0.size(); i++) {
			boardLine0.get(i).setLayout(null);
		}
		for (int i = 0; i < boardLine1.size(); i++) {
			boardLine1.get(i).setLayout(null);
		}
		for (int i = 0; i < boardLine2.size(); i++) {
			boardLine2.get(i).setLayout(null);
		}
		for (int i = 0; i < boardLine3.size(); i++) {
			boardLine3.get(i).setLayout(null);
		}

		// ������ �÷��̾�â
		player1Info.setBorder(new LineBorder(new Color(0, 0, 0)));
		player2Info.setBorder(new LineBorder(new Color(0, 0, 0)));
		player3Info.setBorder(new LineBorder(new Color(0, 0, 0)));
		player4Info.setBorder(new LineBorder(new Color(0, 0, 0)));
		// �÷��̾�â ĳ���� �̹���
		player1Img.setIcon(new ImageIcon("images/img_player01_info.png"));
		player2Img.setIcon(new ImageIcon("images/img_player02_info.png"));
		player3Img.setIcon(new ImageIcon("images/img_player03_info.png"));
		player4Img.setIcon(new ImageIcon("images/img_player04_info.png"));
		// ������ ä��â
		playerChatPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		playerChatPanel.setLayout(new BorderLayout());
		playerChatList.setEditable(false);

		c.setLayout(null);
		// ������ �÷��̾�â
		player1Info.setBounds(800, 0, 200, 100);
		player1Info.setBackground(new Color(153, 102, 255));
		player1Info.setLayout(null);

		player2Info.setBounds(800, 100, 200, 100);
		player2Info.setBackground(new Color(153, 153, 255));
		player2Info.setLayout(null);

		player3Info.setBounds(800, 200, 200, 100);
		player3Info.setBackground(new Color(153, 255, 255));
		player3Info.setLayout(null);

		player4Info.setBounds(800, 300, 200, 100);
		player4Info.setBackground(new Color(102, 255, 51));
		player4Info.setLayout(null);
		// �÷��̾�1 �̹���, ���̵�, ���� ��
		player1Img.setBounds(10, 10, 80, 80);
		player1Img.setBorder(new LineBorder(new Color(163, 112, 255)));
		player1Img.setVisible(false);
		player1Id.setBounds(100, 10, 80, 20);
		player1Id.setFont(new Font("CookieRun BLACK", Font.BOLD, 17));
		player1Money.setBounds(100, 75, 80, 20);
		player1Money.setFont(new Font("CookieRun BLACK", Font.BOLD, 14));
		// �÷��̾�2 �̹���, ���̵�, ���� ��
		player2Img.setBounds(10, 10, 80, 80);
		player2Img.setBorder(new LineBorder(new Color(163, 163, 255)));
		player2Img.setVisible(false);
		player2Id.setBounds(100, 10, 80, 20);
		player2Id.setFont(new Font("CookieRun BLACK", Font.BOLD, 17));
		player2Money.setBounds(100, 75, 80, 20);
		player2Money.setFont(new Font("CookieRun BLACK", Font.BOLD, 14));
		// �÷��̾�3 �̹���, ���̵�, ���� ��
		player3Img.setBounds(10, 10, 80, 80);
		player3Img.setBorder(new LineBorder(new Color(173, 255, 255)));
		player3Img.setVisible(false);
		player3Id.setBounds(100, 10, 80, 20);
		player3Id.setFont(new Font("CookieRun BLACK", Font.BOLD, 17));
		player3Money.setBounds(100, 75, 80, 20);
		player3Money.setFont(new Font("CookieRun BLACK", Font.BOLD, 14));
		// �÷��̾�4 �̹���, ���̵�, ���� ��
		player4Img.setBounds(10, 10, 80, 80);
		player4Img.setBorder(new LineBorder(new Color(112, 255, 61)));
		player4Img.setVisible(false);
		player4Id.setBounds(100, 10, 80, 20);
		player4Id.setFont(new Font("CookieRun BLACK", Font.BOLD, 17));
		player4Money.setBounds(100, 75, 80, 20);
		player4Money.setFont(new Font("CookieRun BLACK", Font.BOLD, 14));
		// ������ ä��â
		playerChatPanel.setBounds(800, 400, 200, 400);
		scChatList.setSize(180, 370);
		playerChatField.setSize(200, 30);
		// �߰� ����
		boardCenter.setIcon(new ImageIcon("images/bg_board.png"));
		boardCenter.setBounds(150, 150, 500, 500);
		// �𼭸� ���ǵ� �̹���
		board0.setBackground(new Color(204, 204, 153));
		board6.setIcon(new ImageIcon("images/board_island.png"));
		board12.setIcon(new ImageIcon("images/board_olympic.png"));
		board18.setIcon(new ImageIcon("images/board_travel.png"));
		// Ȳ�� ī�� �̹���
		board1.setIcon(new ImageIcon("images/bg_key_bottom.png"));
		board2.setIcon(new ImageIcon("images/bg_key_bottom.png"));
		board3.setIcon(new ImageIcon("images/bg_key_bottom.png"));
		board4.setIcon(new ImageIcon("images/bg_key_bottom.png"));
		board5.setIcon(new ImageIcon("images/bg_key_bottom.png"));
		board10.setIcon(new ImageIcon("images/bg_key_left.png"));
		board16.setIcon(new ImageIcon("images/bg_key_top.png"));
		board19.setIcon(new ImageIcon("images/bg_key_right.png"));
		// �ֻ��� �̹���
		laDice1.setBounds(155, 80, 90, 90);
		laDice2.setBounds(265, 80, 90, 90);
		// �ֻ��� ������ ��ư
		btnDiceRoll.setBounds(200, 300, 100, 50);
		btnDiceRoll.setVisible(false);
		// ���۹�ư
		btnStart.setBounds(200, 300, 100, 50);
		btnStart.setVisible(true); //*false�� 
		// ���۹��� ~ ���ε�
		board0.setBounds(650, 650, 150, 150); // ���۹���
		board1.setBounds(550, 650, 100, 150);
		board2.setBounds(450, 650, 100, 150);
		board3.setBounds(350, 650, 100, 150);
		board4.setBounds(250, 650, 100, 150);
		board5.setBounds(150, 650, 100, 150);
		board6.setBounds(0, 650, 150, 150); // ���ε�
		// ���ε� ~ �ø���
		board7.setBounds(0, 550, 150, 100);
		board8.setBounds(0, 450, 150, 100);
		board9.setBounds(0, 350, 150, 100);
		board10.setBounds(0, 250, 150, 100);
		board11.setBounds(0, 150, 150, 100);
		board12.setBounds(0, 0, 150, 150); // �ø���
		// �ø��� ~ ���迩��
		board13.setBounds(150, 0, 100, 150);
		board14.setBounds(250, 0, 100, 150);
		board15.setBounds(350, 0, 100, 150);
		board16.setBounds(450, 0, 100, 150);
		board17.setBounds(550, 0, 100, 150);
		board18.setBounds(650, 0, 150, 150); // ���迩��
		// ���迩�� ~ ���۹��� ����
		board19.setBounds(650, 150, 150, 100);
		board20.setBounds(650, 250, 150, 100);
		board21.setBounds(650, 350, 150, 100);
		board22.setBounds(650, 450, 150, 100);
		board23.setBounds(650, 550, 150, 100); // ���۹��� �ٷ� ��ĭ

		// ���Ǻ� �ǹ��̹��� �� ����
		// Line 0
		board1BldImage.setBounds(0, 0, 100, 40);
		board3BldImage.setBounds(0, 0, 100, 40);
		board4BldImage.setBounds(0, 0, 100, 40);
		board5BldImage.setBounds(0, 0, 100, 40);
		
		// Line 1
		board7BldImage.setBounds(0, 0, 40, 100);
		board8BldImage.setBounds(0, 0, 40, 100);
		board9BldImage.setBounds(0, 0, 40, 100);
		board11BldImage.setBounds(0, 0, 40, 100);
		
		// Line 2
		board13BldImage.setBounds(0, 0, 100, 40);
		board14BldImage.setBounds(0, 0, 100, 40);
		board15BldImage.setBounds(0, 0, 100, 40);
		board17BldImage.setBounds(0, 0, 100, 40);
		
		// Line 3
		board20BldImage.setBounds(0, 0, 40, 100);
		board21BldImage.setBounds(0, 0, 40, 100);
		board22BldImage.setBounds(0, 0, 40, 100);
		board23BldImage.setBounds(0, 0, 40, 100);
		
		// ���Ǻ� ���� �� ����
		// Line 0
		board1Centerla.setBounds(0, 40, 100, 70);
		board1Centerla.setFont(new Font("CookieRun BLACK", Font.BOLD, 45));
		board1Centerla.setHorizontalAlignment(JLabel.CENTER);

		board3Centerla.setBounds(0, 40, 100, 70);
		board3Centerla.setFont(new Font("CookieRun BLACK", Font.BOLD, 45));
		board3Centerla.setHorizontalAlignment(JLabel.CENTER);

		board4Centerla.setBounds(0, 40, 100, 70);
		board4Centerla.setFont(new Font("CookieRun BLACK", Font.BOLD, 45));
		board4Centerla.setHorizontalAlignment(JLabel.CENTER);

		board5Centerla.setBounds(0, 40, 100, 70);
		board5Centerla.setFont(new Font("CookieRun BLACK", Font.BOLD, 45));
		board5Centerla.setHorizontalAlignment(JLabel.CENTER);
		
		// Line 1
		board7Centerla.setBounds(40, 0, 70, 100);
		board8Centerla.setBounds(40, 0, 70, 100);
		board9Centerla.setBounds(40, 0, 70, 100);
		board11Centerla.setBounds(40, 0, 70, 100);
		
		// Line 2
		board13Centerla.setBounds(0, 40, 100, 70);
		board13Centerla.setFont(new Font("CookieRun BLACK", Font.BOLD, 45));
		board13Centerla.setHorizontalAlignment(JLabel.CENTER);

		board14Centerla.setBounds(0, 40, 100, 70);
		board14Centerla.setFont(new Font("CookieRun BLACK", Font.BOLD, 45));
		board14Centerla.setHorizontalAlignment(JLabel.CENTER);

		board15Centerla.setBounds(0, 40, 100, 70);
		board15Centerla.setFont(new Font("CookieRun BLACK", Font.BOLD, 45));
		board15Centerla.setHorizontalAlignment(JLabel.CENTER);

		board17Centerla.setBounds(0, 40, 100, 70);
		board17Centerla.setFont(new Font("CookieRun BLACK", Font.BOLD, 45));
		board17Centerla.setHorizontalAlignment(JLabel.CENTER);
		
		// Line 3
		board20Centerla.setBounds(40, 0, 70, 100);
		board21Centerla.setBounds(40, 0, 70, 100);
		board22Centerla.setBounds(40, 0, 70, 100);
		board23Centerla.setBounds(40, 0, 70, 100);
		
		// ���Ǻ� �̸� �� ����
		// Line 0
		board1CityName.setBounds(0, 110, 100, 40);
		board1CityName.setFont(new Font("CookieRun BLACK", Font.BOLD, 20));
		board1CityName.setHorizontalAlignment(JLabel.CENTER);

		board3CityName.setBounds(0, 110, 100, 40);
		board3CityName.setFont(new Font("CookieRun BLACK", Font.BOLD, 20));
		board3CityName.setHorizontalAlignment(JLabel.CENTER);

		board4CityName.setBounds(0, 110, 100, 40);
		board4CityName.setFont(new Font("CookieRun BLACK", Font.BOLD, 20));
		board4CityName.setHorizontalAlignment(JLabel.CENTER);

		board5CityName.setBounds(0, 110, 100, 40);
		board5CityName.setFont(new Font("CookieRun BLACK", Font.BOLD, 20));
		board5CityName.setHorizontalAlignment(JLabel.CENTER);
		
		// Line 1
		board7CityName.setBounds(0, 0, 40, 100);
		board8CityName.setBounds(0, 0, 40, 100);
		board9CityName.setBounds(0, 0, 40, 100);
		board11CityName.setBounds(0, 0, 40, 100);
		
		// Line 2
		board13CityName.setBounds(0, 110, 100, 40);
		board13CityName.setFont(new Font("CookieRun BLACK", Font.BOLD, 20));
		board13CityName.setHorizontalAlignment(JLabel.CENTER);

		board14CityName.setBounds(0, 110, 100, 40);
		board14CityName.setFont(new Font("CookieRun BLACK", Font.BOLD, 20));
		board14CityName.setHorizontalAlignment(JLabel.CENTER);

		board15CityName.setBounds(0, 110, 100, 40);
		board15CityName.setFont(new Font("CookieRun BLACK", Font.BOLD, 20));
		board15CityName.setHorizontalAlignment(JLabel.CENTER);

		board17CityName.setBounds(0, 110, 100, 40);
		board17CityName.setFont(new Font("CookieRun BLACK", Font.BOLD, 20));
		board17CityName.setHorizontalAlignment(JLabel.CENTER);

		// Line 3
		board20CityName.setBounds(110, 0, 40, 100);
		board21CityName.setBounds(110, 0, 40, 100);
		board22CityName.setBounds(110, 0, 40, 100);
		board23CityName.setBounds(110, 0, 40, 100);
		
		// ������ �÷��̾� ĳ���� �̹��� ���ܳ���
//		player1.setVisible(false);
//		player2.setVisible(false);
//		player3.setVisible(false);
//		player4.setVisible(false);
	}

	@Override
	public void batch() {
		// ���� ��ġ
		add(board0);
		add(board1);
		add(board2);
		add(board3);
		add(board4);
		add(board5);
		add(board6);
		add(board7);
		add(board8);
		add(board9);
		add(board10);
		add(board11);
		add(board12);
		add(board13);
		add(board14);
		add(board15);
		add(board16);
		add(board17);
		add(board18);
		add(board19);
		add(board20);
		add(board21);
		add(board22);
		add(board23);
		// �ֻ��� ������, ���� ��ư ��ġ
		add(btnDiceRoll);
		add(btnStart);
		// �߰� ����
		add(boardCenter);
		// �ֻ��� ������, ���� ��ư ��ġ
		boardCenter.add(btnDiceRoll);
		boardCenter.add(btnStart);
		// �ֻ��� �� ��ġ => �̹��� ���� ����
		boardCenter.add(laDice1);
		boardCenter.add(laDice2);
		// ������ �÷��̾�â
		add(player1Info);
		add(player2Info);
		add(player3Info);
		add(player4Info);
		// �÷��̾�â �̹���
		player1Info.add(player1Img);
		player2Info.add(player2Img);
		player3Info.add(player3Img);
		player4Info.add(player4Img);
		// �÷��̾�â ���̵�
		player1Info.add(player1Id);
		player2Info.add(player2Id);
		player3Info.add(player3Id);
		player4Info.add(player4Id);
		// �÷��̾�â �����Ӵ�
		player1Info.add(player1Money);
		player2Info.add(player2Money);
		player3Info.add(player3Money);
		player4Info.add(player4Money);
		// ������ ä��â
		add(scChatList);
		add(playerChatPanel);
		playerChatPanel.add(scChatList, BorderLayout.CENTER);
		scChatList.add(playerChatList);
		playerChatPanel.add(playerChatField, BorderLayout.SOUTH);

		// ���Ǻ� �ǹ��̹��� �� ����
		// Line 0
		board1.add(board1BldImage);
		board3.add(board3BldImage);
		board4.add(board4BldImage);
		board5.add(board5BldImage);
		// Line 1
		board7.add(board7BldImage);
		board8.add(board8BldImage);
		board9.add(board9BldImage);
		board11.add(board11BldImage);
		// Line 2
		board13.add(board13BldImage);
		board14.add(board14BldImage);
		board15.add(board15BldImage);
		board17.add(board17BldImage);
		// Line 3
		board20.add(board20BldImage);
		board21.add(board21BldImage);
		board22.add(board22BldImage);
		board23.add(board23BldImage);
		
		// ���Ǻ� ���� �� ����
		// Line 0
		board1.add(board1Centerla);
		board3.add(board3Centerla);
		board4.add(board4Centerla);
		board5.add(board5Centerla);
		// Line 1
		board7.add(board7Centerla);
		board8.add(board8Centerla);
		board9.add(board9Centerla);
		board11.add(board11Centerla);
		// Line 2
		board13.add(board13Centerla);
		board14.add(board14Centerla);
		board15.add(board15Centerla);
		board17.add(board17Centerla);
		// Line 3
		board20.add(board20Centerla);
		board21.add(board21Centerla);
		board22.add(board22Centerla);
		board23.add(board23Centerla);
		
		// ���Ǻ� �̸� �� ����
		// Line 0
		board1.add(board1CityName);
		board3.add(board3CityName);
		board4.add(board4CityName);
		board5.add(board5CityName);
		// Line 1
		board7.add(board7CityName);
		board8.add(board8CityName);
		board9.add(board9CityName);
		board11.add(board11CityName);
		// Line 2
		board13.add(board13CityName);
		board14.add(board14CityName);
		board15.add(board15CityName);
		board17.add(board17CityName);
		// Line 3
		board20.add(board20CityName);
		board21.add(board21CityName);
		board22.add(board22CityName);
		board23.add(board23CityName);
		
		// �÷��̾� ĳ���� �̹���
		add(player1, 5);
		add(player2, 5);
		add(player3, 5);
		add(player4, 5);
	}

	@Override
	public void listener() {
		btnDiceRoll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cpt.playerRoll();
			}
		});

		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnStart.setVisible(false);
				cpt.dto.setGubun(Protocol.GAME);
				cpt.dto.setType(Protocol.GAMESTART);
				String gameStart = cpt.gson.toJson(cpt.dto);
				cpt.writer.println(gameStart);
			}
		});

		playerChatField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendChat();
			}
		});
		c.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("X : " + e.getX());
				System.out.println("Y : " + e.getY());
			}
		});
	}

	class ClientPlayerThread extends Thread {

		private Socket socket;
		private BufferedReader reader;
		private PrintWriter writer;
		private Gson gson;
		private String output;
		private RequestDto dto;
		private String id;

		// ������ ������ ������ ����
		public ClientPlayerThread(Socket socket, String id) {
			this.socket = socket;
			this.id = id;
			this.dto = new RequestDto();
			this.gson = new Gson();
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new PrintWriter(socket.getOutputStream(), true);

				// ���̵� ���� -> ������ �ش� Ŭ���̾�Ʈ�� ID���� �Ѱ���
				dto.setType(Protocol.IDSET);
				dto.setId(this.id);
				String idSet = gson.toJson(dto);
				writer.println(idSet);

//				dto.setType(Protocol.PLAYERSET);
//				dto.setId(this.id);
//				String playerSet = gson.toJson(dto);
//				writer.println(playerSet);

				// ������ playerList�� ũ�Ⱑ 4�� �̻��̸� ���� ������ ���� �ʰ� ��.
				dto.setType(Protocol.PLAYERNUMCHECK);
				dto.setId(this.id);
				String playerNumCheck = gson.toJson(dto);
				writer.println(playerNumCheck);

				// ���� �� �ҷ����� ���� ������ ����
				new Thread(new ClientPlayerReader(reader, writer)).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private void playerRoll() {
			int tempDice1 = dice.nextInt(6) + 1;
			int tempDice2 = dice.nextInt(6) + 1;
			dice1 = tempDice1;
			dice2 = tempDice2;
			int newPlayerTile = (int) ((nowPlayerTile + dice1 + dice2) % 24);

			dto.setGubun(Protocol.GAME);
			dto.setType(Protocol.DICEROLL);
			dto.setId(id);
			dto.setDice1(dice1);
			dto.setDice2(dice2);

			output = gson.toJson(dto);
			writer.println(output);

			System.out.println(TAG + "playerRoll ����");
			move(newPlayerTile);
		}

		private void move(int newPlayerTile) {

			dto.setId(id);
			dto.setGubun(Protocol.GAME);
			dto.setType(Protocol.MOVE);
			dto.setNewPlayerTile(newPlayerTile);
			dto.setDice1(dice1);
			dto.setDice2(dice2);
			nowPlayerTile = newPlayerTile;

			output = gson.toJson(dto);
			writer.println(output);
			System.out.println(TAG + "MOVE �����");
			// ���� ���� �����̴� ���
			// ���� �Ʒ��� ���̾�α� ���� ���
			dto.setGubun(Protocol.GAME);
			dto.setType(Protocol.DIALOGREQUEST);
			dto.setId(id);
			dto.setNowPlayerTile(nowPlayerTile); // Ÿ�� ��ȣ �ѱ�

			writer.println(gson.toJson(dto));

		}
	}

	class ClientPlayerReader extends Thread {
		private BufferedReader reader;
		private PrintWriter writer;

		public ClientPlayerReader(BufferedReader reader, PrintWriter writer) {
			this.reader = reader;
			this.writer = writer;
		}

		@Override
		public void run() {
			try {
				String text = null;
				Gson gson = new Gson();
				RequestDto dto = new RequestDto();
				while ((text = reader.readLine()) != null) {
					dto = gson.fromJson(text, RequestDto.class);

					if (dto.getType().equals(Protocol.GAMEHOST)) {
						System.out.println(TAG + "GAMEHOST ����");
						btnStart.setVisible(true);
					}

					// �̹� �����ϴ� ID�� ID�� �����ϰ� ��.
//		               if (dto.getType().equals(Protocol.IDCHECK)) {
//		                  JOptionPane.showMessageDialog(null, "�̹� �����ϴ� ID�Դϴ�.\n �ٸ� ID�� ������ּ��� !");
//		                  System.exit(0);
//		                  setVisible(false);
//		               }

//		                Ŭ���̾�Ʈ �� �÷��̾� ��ü�� ID�� �ֱ�
					if (dto.getType().equals(Protocol.PLAYERSET)) {
						if (dto.getPlayer1() != null) {
							player1.setId(dto.getPlayer1());
							player1.setVisible(true);
							player1Img.setVisible(true);
							player1Id.setText(player1.getId());
							player1Money.setText(Integer.toString(5000));
						}
						if (dto.getPlayer2() != null) {
							player2.setId(dto.getPlayer2());
							player2.setVisible(true);
							player2Img.setVisible(true);
							player2Id.setText(player2.getId());
							player2Money.setText(Integer.toString(5000));
						}
						if (dto.getPlayer3() != null) {
							player3.setId(dto.getPlayer3());
							player3.setVisible(true);
							player3Img.setVisible(true);
							player3Id.setText(player3.getId());
							player3Money.setText(Integer.toString(5000));
						}
						if (dto.getPlayer4() != null) {
							player4.setId(dto.getPlayer4());
							player4.setVisible(true);
							player4Img.setVisible(true);
							player4Id.setText(player4.getId());
							player4Money.setText(Integer.toString(5000));
						}

						btnStart.setVisible(false);
						btnDiceRoll.setVisible(true);
					}
					// �÷������̰ų� 4�� �ʰ��ϸ� ���α׷� ����.(�����Ұ�)
					if (dto.getType().equals(Protocol.PLAYERNUMCHECK)) {
						if (dto.getPlayerNum() == 4) {
							JOptionPane.showMessageDialog(null, "���� �÷������� ������ ���ų� ������ �̹� �÷������Դϴ�.\n ���߿� �õ����ּ���.");
							System.exit(0);
							setDaemon(false);
						}
					}
					// �ֻ��������� ����
					if (dto.getType().equals(Protocol.DICEROLL)) {
						System.out.println(dto.getId() + "DICEROLL ����");
					}
					// �����̱� ����, �ֻ��� �̹��� ���� ����
					if (dto.getType().equals(Protocol.MOVE)) {
						// ���� �ֻ������� ���� Ŭ���̾�Ʈ�� �̹����� ����
						laDice1.setIcon(diceShow(dto.getDice1()));
						laDice2.setIcon(diceShow(dto.getDice2()));
						int nextx,nexty,nextnum; //����ĭ�� x, y, tilenum
						if (dto.getId().equals(player1.getId())) {
							//�ֻ����� ���� ���� ��ŭ �̵����� �ʾҴٸ�
							while(player1.getNowPlayerTile() != dto.getNewPlayerTile()) { 
								nextx = tileList.get((player1.getNowPlayerTile()+1)%24).getTileX();
								nexty = tileList.get((player1.getNowPlayerTile()+1)%24).getTileY();
								nextnum = tileList.get((player1.getNowPlayerTile()+1)%24).getTileNum();
								player1.moveAnimation(nextx+30,nexty+30,nextnum);
								Thread.sleep(100); //moveAnimation �����尡 ������ ���� ���.
							}
						} else if (dto.getId().equals(player2.getId())) {
							while(player2.getNowPlayerTile() != dto.getNewPlayerTile()) {
								nextx = tileList.get((player2.getNowPlayerTile()+1)%24).getTileX();
								nexty = tileList.get((player2.getNowPlayerTile()+1)%24).getTileY();
								nextnum = tileList.get((player2.getNowPlayerTile()+1)%24).getTileNum();
								player2.moveAnimation(nextx+30,nexty+60,nextnum);
								Thread.sleep(100);
							}
						} else if (dto.getId().equals(player3.getId())) {
							while(player3.getNowPlayerTile() != dto.getNewPlayerTile()) {
								nextx = tileList.get((player3.getNowPlayerTile()+1)%24).getTileX();
								nexty = tileList.get((player3.getNowPlayerTile()+1)%24).getTileY();
								nextnum = tileList.get((player3.getNowPlayerTile()+1)%24).getTileNum();
								player3.moveAnimation(nextx+60,nexty+30,nextnum);
								Thread.sleep(100);
							}
						} else if (dto.getId().equals(player4.getId())) {
							while(player4.getNowPlayerTile() != dto.getNewPlayerTile()) {
								nextx = tileList.get((player4.getNowPlayerTile()+1)%24).getTileX();
								nexty = tileList.get((player4.getNowPlayerTile()+1)%24).getTileY();
								nextnum = tileList.get((player4.getNowPlayerTile()+1)%24).getTileNum();
								player4.moveAnimation(nextx+60,nexty+60,nextnum);
								Thread.sleep(100);
							}
						}
					}
					// �ش� �÷��̾ ���̾�α� Ÿ���� �������� �ش� Ÿ�� ���� �޾ƿ�.
					if (dto.getType().equals(Protocol.DIALOGREQUEST)) {
						if (dto.getId().equals(id)) {
							TILE = dto.getTileInfo();
							System.out.println(TAG + "���⸦ ������ ����" + TILE.getLandOwner());
							if (TILE.getTileType() == 0) {

							} else if (TILE.getTileType() == 1) {
								if (dto.getTileInfo().getLandOwner().equals("")
										|| dto.getTileInfo().getLandOwner().equals(id)) {
									if (dto.getTileInfo().getIsPurchased().equals(allPurcharsed)) {

									} else {
										nowPrice = TILE.getPriceAll();
										new DiallogCity(id);

										// isPurchased �� allPurchased �� �ٸ� ��� ������ Ÿ�� ���� �� ����
										new Thread(new Runnable() {
											@Override
											public void run() {
												while (true) {
													try {
														Thread.sleep(1000);
														if (isDialogCity == true) {
															RequestDto tempDto = new RequestDto();

															tempDto.setType(Protocol.DIALOGUPDATE);
															tempDto.setTileInfo(TILE);
															writer.println(gson.toJson(tempDto));

															tempDto.setType(Protocol.PLAYERPURCHASED);
															tempDto.setId(id);
															tempDto.setNewprice(TILE.getPriceAll() - nowPrice);
															writer.println(gson.toJson(tempDto));

															isDialogCity = false;
															break;
														}
													} catch (InterruptedException e) {
														e.printStackTrace();
													}
												}
											}
										}).start();
									}
								} else {
									new DiallogFine(id);

									new Thread(new Runnable() {
										@Override
										public void run() {
											while (true) {
												try {
													Thread.sleep(1000);
													if (isDialogFine == true) {
														RequestDto tempDto = new RequestDto();
														tempDto.setType(Protocol.PLAYERFINE);
														tempDto.setId(id);
														tempDto.setTileFine(TILE.getFine());
														tempDto.setTileOwnerId(TILE.getLandOwner());
														writer.println(gson.toJson(tempDto));

														isDialogFine = false;
														break;
													}
												} catch (InterruptedException e) {
													e.printStackTrace();
												}
											}
										}
									}).start();
								}
							}
						}
					}
					if (dto.getType().equals(Protocol.PLAYERPURCHASED)) {

						if (dto.getId().equals(player1.getId())) {
							player1.setMoney(player1.getMoney() - dto.getNewprice());
							player1Money.setText(Integer.toString(player1.getMoney()));
						} else if (dto.getId().equals(player2.getId())) {
							player2.setMoney(player2.getMoney() - dto.getNewprice());
							player2Money.setText(Integer.toString(player2.getMoney()));
						} else if (dto.getId().equals(player3.getId())) {
							player3.setMoney(player3.getMoney() - dto.getNewprice());
							player3Money.setText(Integer.toString(player3.getMoney()));
						} else if (dto.getId().equals(player4.getId())) {
							player4.setMoney(player4.getMoney() - dto.getNewprice());
							player4Money.setText(Integer.toString(player4.getMoney()));
						}

					}

					
					if (dto.getType().equals(Protocol.PLAYERFINE)) {

						// Ÿ�� ���� �÷��̾�� ����� ��ŭ ���� ����
						if (dto.getTileOwnerId().equals(player1.getId())) {
							player1.setMoney(player1.getMoney() + dto.getTileFine());
							player1Money.setText(Integer.toString(player1.getMoney()));
						} else if (dto.getTileOwnerId().equals(player2.getId())) {
							player2.setMoney(player2.getMoney() + dto.getTileFine());
							player2Money.setText(Integer.toString(player2.getMoney()));
						} else if (dto.getTileOwnerId().equals(player3.getId())) {
							player3.setMoney(player3.getMoney() + dto.getTileFine());
							player3Money.setText(Integer.toString(player3.getMoney()));
						} else if (dto.getTileOwnerId().equals(player4.getId())) {
							player4.setMoney(player4.getMoney() + dto.getTileFine());
							player4Money.setText(Integer.toString(player4.getMoney()));
						}

						// �ɸ� �÷��̾�� ����� ��ŭ ���� ����
						if (dto.getId().equals(player1.getId())) {
							player1.setMoney(player1.getMoney() - dto.getTileFine());
							player1Money.setText(Integer.toString(player1.getMoney()));
						} else if (dto.getId().equals(player2.getId())) {
							player2.setMoney(player2.getMoney() - dto.getTileFine());
							player2Money.setText(Integer.toString(player2.getMoney()));
						} else if (dto.getId().equals(player3.getId())) {
							player3.setMoney(player3.getMoney() - dto.getTileFine());
							player3Money.setText(Integer.toString(player3.getMoney()));
						} else if (dto.getId().equals(player4.getId())) {
							player4.setMoney(player4.getMoney() - dto.getTileFine());
							player4Money.setText(Integer.toString(player4.getMoney()));
						}

					}
					// ä�� �ý��� ����
					if (dto.getType().equals(Protocol.CHAT)) {
						playerChatList.append(dto.getText());
						scChatList.getVAdjustable().setValue(scChatList.getVAdjustable().getMaximum());
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void connect() {
		String host = Protocol.HOST;
		int port = Protocol.PORT;
		try {
			socket = new Socket(host, port);
			cpt = new ClientPlayerThread(socket, id);
			cpt.start();
			System.out.println(TAG + id + "���� ����");
		} catch (Exception e) {
			System.out.println(TAG + id + "���� ����");

		}
	}

	private void sendChat() {
		String playerChat = playerChatField.getText();
		cpt.dto.setGubun(Protocol.CHAT);
		cpt.dto.setType(Protocol.CHAT);
		cpt.dto.setText(playerChat);
		cpt.dto.setId(id);
		cpt.writer.println(cpt.gson.toJson(cpt.dto));
		playerChatField.setText("");
	}

	private ImageIcon diceShow(int dice) {
		ImageIcon result = null;
		if (dice == 1) {
			result = new ImageIcon("images/dice1.png");
		} else if (dice == 2) {
			result = new ImageIcon("images/dice2.png");
		} else if (dice == 3) {
			result = new ImageIcon("images/dice3.png");
		} else if (dice == 4) {
			result = new ImageIcon("images/dice4.png");
		} else if (dice == 5) {
			result = new ImageIcon("images/dice5.png");
		} else {
			result = new ImageIcon("images/dice6.png");
		}
		return result;
	}
	private class RotatedLabel extends JLabel {
		char[] tmpTextList;
		
		public RotatedLabel(String text) {
			tmpTextList = new char[text.length()];
			setLayout(new GridLayout(text.length(), 1));
			for (int i = 0; i < text.length(); i++) {
				tmpTextList[i] = text.charAt(i);
				JLabel tmpla = new JLabel(Character.toString(tmpTextList[i]));
				tmpla.setFont((new Font("CookieRun BLACK", Font.BOLD, 20)));
				tmpla.setHorizontalAlignment(JLabel.CENTER);
				tmpla.setVerticalAlignment(JLabel.CENTER);
				add(tmpla);
			}
		}
	}
	public static void main(String[] args) {
		new MarbleClient("����1");
	}
}
