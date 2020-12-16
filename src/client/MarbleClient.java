package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.ScrollPane;
import java.awt.Toolkit;
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
	static boolean isInfoOn = false;
	static int olympicTileNum;
	static int nowPrice;
	static boolean isOlympic = false;
	static boolean isDialogCity = false;
	static boolean isDialogIsland = false;
	static boolean isDialogFine = false;
	private int[] allPurchasedCity = { 1, 1, 1, 1 };
	private int[] allPurchasedIsland = { 1 };

	private ClientPlayerThread cpt;
	private Socket socket;
	private String id;
	private int dice1;
	private int dice2;
	private int nowPlayerTile = 0;
	private int playerX = 240;
	private int playerY = 240;
	private boolean isTurn = false; // 현재 플레이어의 턴인지
	boolean isPlaying = true; // 플레이어 생존 여부
	int isDouble = 0; // 더블 여부
	int totalTurn = 1;
	int isResting = 0;

	private JLabel board0, board1, board2, board3, board4, board5, board6, board7, board8, board9, board10, board11,
			board12, board13, board14, board15, board16, board17, board18, board19, board20, board21, board22, board23;
	private JLabel board1CityName, board3CityName, board4CityName, board5CityName; // Line0 도시이름라벨
	private JLabel board1Centerla, board3Centerla, board4Centerla, board5Centerla; // Line0 센터라벨
	private JLabel board1BldImage, board3BldImage, board4BldImage, board5BldImage; // Line0 건물이미지라벨

	private RotatedLabel board7CityName, board8CityName, board9CityName, board11CityName; // Line1 도시이름라벨
	private RotatedLabel board7Centerla, board8Centerla, board9Centerla, board11Centerla; // Line1 센터라벨
	private JLabel board7BldImage, board8BldImage, board9BldImage, board11BldImage; // Line1 건물이미지라벨

	private JLabel board13CityName, board14CityName, board15CityName, board17CityName; // Line2 도시이름라벨
	private JLabel board13Centerla, board14Centerla, board15Centerla, board17Centerla; // Line2 센터라벨
	private JLabel board13BldImage, board14BldImage, board15BldImage, board17BldImage; // Line2 건물이미지라벨

	private RotatedLabel board20CityName, board21CityName, board22CityName, board23CityName; // Line3 도시이름라벨
	private RotatedLabel board20Centerla, board21Centerla, board22Centerla, board23Centerla; // Line3 센터라벨
	private JLabel board20BldImage, board21BldImage, board22BldImage, board23BldImage; // Line3 건물이미지라벨

	private ArrayList<JLabel> boardLine0, boardLine1, boardLine2, boardLine3; // 0:밑에줄 1:왼쪽 2:위쪽 3:오른쪽

	private Container c;
	// 주사위굴리기 버튼, 시작버튼
	private JButton btnDiceRoll, btnStart, btnEndTurn;
	// 중간 보드
	private JLabel boardCenter;
	// 오른쪽 플레이어창, 채팅창
	private JPanel player1Info, player2Info, player3Info, player4Info, playerChatPanel;
	private JLabel player1Img, player2Img, player3Img, player4Img;
	private JLabel player1Money, player2Money, player3Money, player4Money;
	private JLabel player1Id, player2Id, player3Id, player4Id;
	// 오른쪽 채팅창
	private ScrollPane scChatList;
	private JTextArea playerChatList;
	private JTextField playerChatField;
	// 플레이어(유저) 이미지 객체 - 플레잉중이 아니면 안보임
	private Player player1, player2, player3, player4;
	private int playerNum;
	// 구매하기 이전 isPurchased
	private int[] nowBuild;
	// 구매하기 이후 isPurchased
	private int[] newBuild;

	// 주사위 값 이미지 띄우는 라벨
	private JLabel laDice1, laDice2;

	// 타일값 받아오는 리스트
	private Vector<Tile> tileList;

	Random dice = new Random();

	int[] arrayinit = { 0, 0, 0, 0 };

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
		// 커서변경
		noClickCursor();
		
		// 라인별 시티/아일랜드 타일을 담는 리스트
		boardLine0 = new ArrayList<>();
		boardLine1 = new ArrayList<>();
		boardLine2 = new ArrayList<>();
		boardLine3 = new ArrayList<>();

		// 서버로부터 받은 타일 리스트를 담는 리스트
		tileList = new Vector<>();

		// 발판 new
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
		// 아랫 라인( board2는 스페셜이라서 제외 )
		boardLine0.add(board1);
		boardLine0.add(board3);
		boardLine0.add(board4);
		boardLine0.add(board5);
		// 왼쪽 라인( board10는 스페셜이라서 제외 )
		boardLine1.add(board7);
		boardLine1.add(board8);
		boardLine1.add(board9);
		boardLine1.add(board11);
		// 윗쪽 라인( board16는 스페셜이라서 제외 )
		boardLine2.add(board13);
		boardLine2.add(board14);
		boardLine2.add(board15);
		boardLine2.add(board17);
		// 오른쪽 라인( board19는 스페셜이라서 제외 )
		boardLine3.add(board20);
		boardLine3.add(board21);
		boardLine3.add(board22);
		boardLine3.add(board23);

		// 보드별 도시 이름
		board1CityName = new JLabel("홍콩");
		board3CityName = new JLabel("도쿄");
		board4CityName = new JLabel("제주도");
		board5CityName = new JLabel("카이로");
		board7CityName = new RotatedLabel("하와이");
		board8CityName = new RotatedLabel("시드니");
		board9CityName = new RotatedLabel("상파울로");
		board11CityName = new RotatedLabel("퀘백");
		board13CityName = new JLabel("모스크바");
		board14CityName = new JLabel("베를린");
		board15CityName = new JLabel("독도");
		board17CityName = new JLabel("로마");
		board20CityName = new RotatedLabel("런던");
		board21CityName = new RotatedLabel("파리");
		board22CityName = new RotatedLabel("뉴욕");
		board23CityName = new RotatedLabel("서울");

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

		board1Centerla = new JLabel();
		board3Centerla = new JLabel();
		board4Centerla = new JLabel();
		board5Centerla = new JLabel();
		board7Centerla = new RotatedLabel();
		board8Centerla = new RotatedLabel();
		board9Centerla = new RotatedLabel();
		board11Centerla = new RotatedLabel();
		board13Centerla = new JLabel();
		board14Centerla = new JLabel();
		board15Centerla = new JLabel();
		board17Centerla = new JLabel();
		board20Centerla = new RotatedLabel();
		board21Centerla = new RotatedLabel();
		board22Centerla = new RotatedLabel();
		board23Centerla = new RotatedLabel();

		// 중간 보드
		boardCenter = new JLabel();
		// 주사위 눈 이미지
		laDice1 = new JLabel("");
		laDice2 = new JLabel("");
		// 주사위 버튼, 시작 버튼
		btnDiceRoll = new JButton("주사위 굴리기");
		btnStart = new JButton("게임 시작");
		btnEndTurn = new JButton("턴 종료");
		// 메인 컨텐트
		c = getContentPane();
		// 플레이어 객체 이미지
		player1 = new Player(695, 695, "images/img_player01.png");
		player2 = new Player(735, 695, "images/img_player02.png");
		player3 = new Player(695, 735, "images/img_player03.png");
		player4 = new Player(735, 735, "images/img_player04.png");
		// 오른쪽 플레이어 정보창
		player1Info = new JPanel();
		player2Info = new JPanel();
		player3Info = new JPanel();
		player4Info = new JPanel();
		// 플레이어 정보창 사진
		player1Img = new JLabel();
		player2Img = new JLabel();
		player3Img = new JLabel();
		player4Img = new JLabel();
		// 플레이어 정보창 아이디
		player1Id = new JLabel();
		player2Id = new JLabel();
		player3Id = new JLabel();
		player4Id = new JLabel();
		// 플레이어 정보창 돈
		player1Money = new JLabel();
		player2Money = new JLabel();
		player3Money = new JLabel();
		player4Money = new JLabel();
		// 오른쪽 채팅창
		playerChatPanel = new JPanel();
		scChatList = new ScrollPane();
		playerChatList = new JTextArea();
		playerChatField = new JTextField(20);

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

		board1CityName.setBorder(new LineBorder(new Color(0, 0, 0)));
		board3CityName.setBorder(new LineBorder(new Color(0, 0, 0)));
		board4CityName.setBorder(new LineBorder(new Color(0, 0, 0)));
		board5CityName.setBorder(new LineBorder(new Color(0, 0, 0)));
		board7CityName.setBorder(new LineBorder(new Color(0, 0, 0)));
		board8CityName.setBorder(new LineBorder(new Color(0, 0, 0)));
		board9CityName.setBorder(new LineBorder(new Color(0, 0, 0)));
		board11CityName.setBorder(new LineBorder(new Color(0, 0, 0)));
		board13CityName.setBorder(new LineBorder(new Color(0, 0, 0)));
		board14CityName.setBorder(new LineBorder(new Color(0, 0, 0)));
		board15CityName.setBorder(new LineBorder(new Color(0, 0, 0)));
		board17CityName.setBorder(new LineBorder(new Color(0, 0, 0)));
		board20CityName.setBorder(new LineBorder(new Color(0, 0, 0)));
		board21CityName.setBorder(new LineBorder(new Color(0, 0, 0)));
		board22CityName.setBorder(new LineBorder(new Color(0, 0, 0)));
		board23CityName.setBorder(new LineBorder(new Color(0, 0, 0)));

		// 발판 내부 레이아웃 = null
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

		// 오른쪽 플레이어창
		player1Info.setBorder(new LineBorder(new Color(0, 0, 0)));
		player2Info.setBorder(new LineBorder(new Color(0, 0, 0)));
		player3Info.setBorder(new LineBorder(new Color(0, 0, 0)));
		player4Info.setBorder(new LineBorder(new Color(0, 0, 0)));
		// 플레이어창 캐릭터 이미지
		player1Img.setIcon(new ImageIcon("images/img_player01_info.png"));
		player2Img.setIcon(new ImageIcon("images/img_player02_info.png"));
		player3Img.setIcon(new ImageIcon("images/img_player03_info.png"));
		player4Img.setIcon(new ImageIcon("images/img_player04_info.png"));
		// 오른쪽 채팅창
		playerChatPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		playerChatPanel.setLayout(new BorderLayout());
		playerChatList.setEditable(false);

		c.setLayout(null);
		// 오른쪽 플레이어창
		player1Info.setBounds(800, 0, 200, 100);
		player1Info.setBackground(new Color(254, 236, 203));
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
		// 플레이어1 이미지, 아이디, 보유 돈
		player1Img.setBounds(10, 10, 80, 80);
		player1Img.setBorder(new LineBorder(new Color(254, 246, 213)));
		player1Img.setVisible(false);
		player1Id.setBounds(100, 10, 80, 20);
		player1Id.setFont(new Font("CookieRun BLACK", Font.BOLD, 17));
		player1Money.setBounds(100, 75, 80, 20);
		player1Money.setFont(new Font("CookieRun BLACK", Font.BOLD, 14));
		// 플레이어2 이미지, 아이디, 보유 돈
		player2Img.setBounds(10, 10, 80, 80);
		player2Img.setBorder(new LineBorder(new Color(163, 163, 255)));
		player2Img.setVisible(false);
		player2Id.setBounds(100, 10, 80, 20);
		player2Id.setFont(new Font("CookieRun BLACK", Font.BOLD, 17));
		player2Money.setBounds(100, 75, 80, 20);
		player2Money.setFont(new Font("CookieRun BLACK", Font.BOLD, 14));
		// 플레이어3 이미지, 아이디, 보유 돈
		player3Img.setBounds(10, 10, 80, 80);
		player3Img.setBorder(new LineBorder(new Color(173, 255, 255)));
		player3Img.setVisible(false);
		player3Id.setBounds(100, 10, 80, 20);
		player3Id.setFont(new Font("CookieRun BLACK", Font.BOLD, 17));
		player3Money.setBounds(100, 75, 80, 20);
		player3Money.setFont(new Font("CookieRun BLACK", Font.BOLD, 14));
		// 플레이어4 이미지, 아이디, 보유 돈
		player4Img.setBounds(10, 10, 80, 80);
		player4Img.setBorder(new LineBorder(new Color(112, 255, 61)));
		player4Img.setVisible(false);
		player4Id.setBounds(100, 10, 80, 20);
		player4Id.setFont(new Font("CookieRun BLACK", Font.BOLD, 17));
		player4Money.setBounds(100, 75, 80, 20);
		player4Money.setFont(new Font("CookieRun BLACK", Font.BOLD, 14));
		// 오른쪽 채팅창
		playerChatPanel.setBounds(800, 400, 200, 400);
		scChatList.setSize(180, 370);
		playerChatField.setSize(200, 30);
		// 중간 보드
		boardCenter.setIcon(new ImageIcon("images/bg_board.png"));
		boardCenter.setBounds(150, 150, 500, 500);
		// 모서리 발판들 이미지
		board0.setBackground(new Color(204, 204, 153));
		board6.setIcon(new ImageIcon("images/board_island.png"));
		board12.setIcon(new ImageIcon("images/board_olympic.png"));
		board18.setIcon(new ImageIcon("images/board_travel.png"));
		// 황금 카드 이미지
		board2.setIcon(new ImageIcon("images/bg_key_bottom.png"));
		board10.setIcon(new ImageIcon("images/bg_key_left.png"));
		board16.setIcon(new ImageIcon("images/bg_key_top.png"));
		board19.setIcon(new ImageIcon("images/bg_key_right.png"));
		// 주사위 이미지
		laDice1.setBounds(155, 80, 90, 90);
		laDice2.setBounds(265, 80, 90, 90);
		// 주사위 굴리기 버튼
		btnDiceRoll.setBounds(200, 300, 100, 50);
		btnDiceRoll.setVisible(false);
		// 시작버튼
		btnStart.setBounds(200, 300, 100, 50);
		btnStart.setVisible(false);
		// 턴종료 버튼
		btnEndTurn.setBounds(200, 300, 100, 50);
		btnEndTurn.setVisible(false);
		// 시작발판 ~ 무인도
		board0.setBounds(650, 650, 150, 150); // 시작발판
		board1.setBounds(550, 650, 100, 150);
		board2.setBounds(450, 650, 100, 150);
		board3.setBounds(350, 650, 100, 150);
		board4.setBounds(250, 650, 100, 150);
		board5.setBounds(150, 650, 100, 150);
		board6.setBounds(0, 650, 150, 150); // 무인도
		// 무인도 ~ 올림픽
		board7.setBounds(0, 550, 150, 100);
		board8.setBounds(0, 450, 150, 100);
		board9.setBounds(0, 350, 150, 100);
		board10.setBounds(0, 250, 150, 100);
		board11.setBounds(0, 150, 150, 100);
		board12.setBounds(0, 0, 150, 150); // 올림픽
		// 올림픽 ~ 세계여행
		board13.setBounds(150, 0, 100, 150);
		board14.setBounds(250, 0, 100, 150);
		board15.setBounds(350, 0, 100, 150);
		board16.setBounds(450, 0, 100, 150);
		board17.setBounds(550, 0, 100, 150);
		board18.setBounds(650, 0, 150, 150); // 세계여행
		// 세계여행 ~ 시작발판 이전
		board19.setBounds(650, 150, 150, 100);
		board20.setBounds(650, 250, 150, 100);
		board21.setBounds(650, 350, 150, 100);
		board22.setBounds(650, 450, 150, 100);
		board23.setBounds(650, 550, 150, 100); // 시작발판 바로 윗칸

		// 발판별 건물이미지 라벨 설정
		// Line 0
		board1BldImage.setBounds(0, 0, 100, 40);
		board3BldImage.setBounds(0, 0, 100, 40);
		board4BldImage.setBounds(0, 0, 100, 40);
		board5BldImage.setBounds(0, 0, 100, 40);

		// Line 1
		board7BldImage.setBounds(110, 0, 40, 100);
		board8BldImage.setBounds(110, 0, 40, 100);
		board9BldImage.setBounds(110, 0, 40, 100);
		board11BldImage.setBounds(110, 0, 40, 100);

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

		// 발판별 센터 라벨 설정
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

		// 발판별 이름 라벨 설정
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

		// 시작전 플레이어 캐릭터 이미지 숨겨놓기
		player1.setVisible(false);
		player2.setVisible(false);
		player3.setVisible(false);
		player4.setVisible(false);
	}

	@Override
	public void batch() {
		// 발판 배치
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
		// 중간 보드
		add(boardCenter);
		// 주사위 굴리기, 시작 버튼 배치
		boardCenter.add(btnDiceRoll);
		boardCenter.add(btnStart);
		boardCenter.add(btnEndTurn);
		// 주사위 값 배치
		boardCenter.add(laDice1);
		boardCenter.add(laDice2);
		// 오른쪽 플레이어창
		add(player1Info);
		add(player2Info);
		add(player3Info);
		add(player4Info);
		// 플레이어창 이미지
		player1Info.add(player1Img);
		player2Info.add(player2Img);
		player3Info.add(player3Img);
		player4Info.add(player4Img);
		// 플레이어창 아이디
		player1Info.add(player1Id);
		player2Info.add(player2Id);
		player3Info.add(player3Id);
		player4Info.add(player4Id);
		// 플레이어창 보유머니
		player1Info.add(player1Money);
		player2Info.add(player2Money);
		player3Info.add(player3Money);
		player4Info.add(player4Money);
		// 오른쪽 채팅창
		add(scChatList);
		add(playerChatPanel);
		playerChatPanel.add(scChatList, BorderLayout.CENTER);
		scChatList.add(playerChatList);
		playerChatPanel.add(playerChatField, BorderLayout.SOUTH);

		// 발판별 건물이미지 라벨 삽입
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

		// 발판별 센터 라벨 삽입
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

		// 발판별 이름 라벨 삽입
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

		// 플레이어 캐릭터 이미지
		add(player1, 5);
		add(player2, 5);
		add(player3, 5);
		add(player4, 5);
	}

	@Override
	public void listener() {
		// 커서 변경 리스너
		c.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				onClickCursor();
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				noClickCursor();
			}
		});
		
		// 주사위굴리기 버튼 리스너
		btnDiceRoll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cpt.playerRoll();
				btnDiceRoll.setVisible(false);
			}
		});
		
		// 턴종료 버튼 리스너
		btnEndTurn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isDouble = 0;
				cpt.dto.setType(Protocol.ENDTURN);
				cpt.dto.setId(id);
				cpt.writer.println(cpt.gson.toJson(cpt.dto));

				cpt.dto.setType(Protocol.NEXTTURN);
				cpt.dto.setId(id);
				cpt.writer.println(cpt.gson.toJson(cpt.dto));

				if (player1.getMoney() < 0) {
					if (player1.getId().equals(id)) {
						new Thread(new Runnable() {
							@Override
							public void run() {
								while (true) {
									RequestDto tempDto = new RequestDto();

									tempDto.setType(Protocol.PLAYERLISTOUT);
									tempDto.setId(id);
									cpt.writer.println(cpt.gson.toJson(tempDto));

									break;
								}
							}
						}).start();
					}
				} else if (player2.getMoney() < 0) {
					if (player2.getId().equals(id)) {
						new Thread(new Runnable() {
							@Override
							public void run() {
								while (true) {
									RequestDto tempDto = new RequestDto();

									tempDto.setType(Protocol.PLAYERLISTOUT);
									tempDto.setId(id);
									cpt.writer.println(cpt.gson.toJson(tempDto));

									break;
								}
							}
						}).start();
					}
				} else if (player3.getMoney() < 0) {
					if (player3.getId().equals(id)) {
						new Thread(new Runnable() {
							@Override
							public void run() {
								while (true) {
									RequestDto tempDto = new RequestDto();

									tempDto.setType(Protocol.PLAYERLISTOUT);
									tempDto.setId(id);
									cpt.writer.println(cpt.gson.toJson(tempDto));

									break;
								}
							}
						}).start();
					}
				} else if (player4.getMoney() < 0) {
					if (player4.getId().equals(id)) {
						new Thread(new Runnable() {
							@Override
							public void run() {
								while (true) {
									RequestDto tempDto = new RequestDto();

									tempDto.setType(Protocol.PLAYERLISTOUT);
									tempDto.setId(id);
									cpt.writer.println(cpt.gson.toJson(tempDto));

									break;
								}
							}
						}).start();
					}
				}
				btnEndTurn.setVisible(false);
				if (isResting == 1) {
					isResting++;
				} else if (isResting == 2) {
					isResting = 0;
				}
			}
		});
		
		// 시작버튼 리스너
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnStart.setVisible(false);
				// 서버에서 타일 정보 받아오기
				cpt.dto.setGubun(Protocol.GAME);
				cpt.dto.setType(Protocol.TILELISTPULL);
				cpt.writer.println(cpt.gson.toJson(cpt.dto));

				// 게임시작
				cpt.dto.setGubun(Protocol.GAME);
				cpt.dto.setType(Protocol.GAMESTART);
				String gameStart = cpt.gson.toJson(cpt.dto);
				cpt.writer.println(gameStart);
			}
		});
		
		// 채팅 리스너
		playerChatField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendChat();
			}
		});
		
		// 타일 정보 보여주는 리스너
		board1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isInfoOn == false) {
					new DialogSearch(tileList.get(1));
					isInfoOn = true;
				}
			}
		});
		board3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isInfoOn == false) {
					new DialogSearch(tileList.get(3));
					isInfoOn = true;
				}
			}
		});
		board4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isInfoOn == false) {
					new DialogSearch(tileList.get(4));
					isInfoOn = true;
				}
			}
		});
		board5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isInfoOn == false) {
					new DialogSearch(tileList.get(5));
					isInfoOn = true;
				}
			}
		});
		board7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isInfoOn == false) {
					new DialogSearch(tileList.get(7));
					isInfoOn = true;
				}
			}
		});
		board8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isInfoOn == false) {
					new DialogSearch(tileList.get(8));
					isInfoOn = true;
				}
			}
		});
		board9.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isInfoOn == false) {
					new DialogSearch(tileList.get(9));
					isInfoOn = true;
				}
			}
		});
		board11.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isInfoOn == false) {
					new DialogSearch(tileList.get(11));
					isInfoOn = true;
				}
			}
		});
		board13.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isInfoOn == false) {
					new DialogSearch(tileList.get(13));
					isInfoOn = true;
				}
			}
		});
		board14.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isInfoOn == false) {
					new DialogSearch(tileList.get(14));
					isInfoOn = true;
				}
			}
		});
		board15.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isInfoOn == false) {
					new DialogSearch(tileList.get(15));
					isInfoOn = true;
				}
			}
		});
		board17.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isInfoOn == false) {
					new DialogSearch(tileList.get(17));
					isInfoOn = true;
				}
			}
		});
		board20.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isInfoOn == false) {
					new DialogSearch(tileList.get(20));
					isInfoOn = true;
				}
			}
		});
		board21.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isInfoOn == false) {
					new DialogSearch(tileList.get(21));
					isInfoOn = true;
				}
			}
		});
		board22.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isInfoOn == false) {
					new DialogSearch(tileList.get(22));
					isInfoOn = true;
				}
			}
		});
		board23.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isInfoOn == false) {
					new DialogSearch(tileList.get(23));
					isInfoOn = true;
				}
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

		// 서버로 보내는 스레드 실행
		public ClientPlayerThread(Socket socket, String id) {
			this.socket = socket;
			this.id = id;
			this.dto = new RequestDto();
			this.gson = new Gson();
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new PrintWriter(socket.getOutputStream(), true);

				// 아이디 설정 -> 서버에 해당 클라이언트의 ID값을 넘겨줌
				dto.setType(Protocol.IDSET);
				dto.setId(this.id);
				String idSet = gson.toJson(dto);
				writer.println(idSet);

//				dto.setType(Protocol.PLAYERSET);
//				dto.setId(this.id);
//				String playerSet = gson.toJson(dto);
//				writer.println(playerSet);

				// 서버의 playerList의 크기가 4명 이상이면 서버 연결이 되지 않게 함.
				dto.setType(Protocol.PLAYERNUMCHECK);
				dto.setId(this.id);
				String playerNumCheck = gson.toJson(dto);
				writer.println(playerNumCheck);

				// 서버 값 불러오는 리더 스레드 실행
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
			
			if (dice1 == dice2) {
				isDouble += 1;
			} else {
				isDouble = 0;
			}

			dto.setGubun(Protocol.GAME);
			dto.setType(Protocol.DICEROLL);
			dto.setId(id);
			dto.setDice1(dice1);
			dto.setDice2(dice2);

			output = gson.toJson(dto);
			writer.println(output);
			
			if ((isResting >= 1) && (isDouble == 0)) {
				isTurn = false;
				btnEndTurn.setVisible(true);
				return;
			}
			
			int newPlayerTile = (int) ((nowPlayerTile + dice1 + dice2) % 24);

			move(newPlayerTile);
		}

		private void move(int newPlayerTile) {
			int tempNowTile = nowPlayerTile;
			
			if ((nowPlayerTile >= 12) && (newPlayerTile < 12)) {
				dto.setType(Protocol.MONTHLY);
				dto.setId(id);
				writer.println(gson.toJson(dto));
			}
			
			
			dto.setId(id);
			dto.setGubun(Protocol.GAME);
			dto.setType(Protocol.MOVE);
			dto.setNewPlayerTile(newPlayerTile);
			dto.setDice1(dice1);
			dto.setDice2(dice2);
			nowPlayerTile = newPlayerTile;

			output = gson.toJson(dto);
			writer.println(output);
			System.out.println(TAG + "MOVE 실행됨");
			// 여기 위는 움직이는 통신
			// 여기 아래는 다이얼로그 띄우는 통신
			dto.setGubun(Protocol.GAME);
			dto.setType(Protocol.DIALOGREQUEST);
			dto.setId(id);
			dto.setNowPlayerTile(nowPlayerTile); // 타일 번호 넘김

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
						System.out.println(TAG + "GAMEHOST 받음");
						btnStart.setVisible(true);
					}

					// 이미 존재하는 ID면 ID를 변경하게 함.
					if (dto.getType().equals(Protocol.IDCHECK)) {
						JOptionPane.showMessageDialog(null, "이미 존재하는 ID입니다.\n 다른 ID를 사용해주세요 !");
						System.exit(0);
						setVisible(false);
					}

//		                클라이언트 내 플레이어 객체에 ID값 넣기
					if (dto.getType().equals(Protocol.PLAYERSET)) {
						if (dto.getPlayer1() != null) {
							player1.setId(dto.getPlayer1());
							player1.setVisible(true);
							player1Img.setVisible(true);
							player1Id.setText(player1.getId());
							player1Money.setText(Integer.toString(player1.money));
						}
						if (dto.getPlayer2() != null) {
							player2.setId(dto.getPlayer2());
							player2.setVisible(true);
							player2Img.setVisible(true);
							player2Id.setText(player2.getId());
							player2Money.setText(Integer.toString(player2.money));
						}
						if (dto.getPlayer3() != null) {
							player3.setId(dto.getPlayer3());
							player3.setVisible(true);
							player3Img.setVisible(true);
							player3Id.setText(player3.getId());
							player3Money.setText(Integer.toString(player3.money));
						}
						if (dto.getPlayer4() != null) {
							player4.setId(dto.getPlayer4());
							player4.setVisible(true);
							player4Img.setVisible(true);
							player4Id.setText(player4.getId());
							player4Money.setText(Integer.toString(player4.money));
						}

						btnStart.setVisible(false);
					}
					// 플레이중이거나 4명 초과하면 프로그램 종료.(참여불가)
					if (dto.getType().equals(Protocol.PLAYERNUMCHECK)) {
						if (dto.getPlayerNum() == 4) {
							JOptionPane.showMessageDialog(null, "현재 플레이중인 유저가 많거나 게임이 이미 플레이중입니다.\n 나중에 시도해주세요.");
							System.exit(0);
							setDaemon(false);
						}
					}

					if (dto.getType().equals(Protocol.TILELISTPULL)) {
						tileList = dto.getTileList();
					}
					// isPlaying 이 true 일 때만
					// 플레이어 턴 부여
					if (dto.getType().equals(Protocol.TURN)) {
						if (dto.getTurnId().equals(id)) {
							btnDiceRoll.setVisible(true);
						}
					}

					if (dto.getType().equals(Protocol.PLAYERLISTOUT)) {
						if (dto.getId().equals(id)) {
							isPlaying = false;
						}
					}

					// 주사위굴리기 구현
					if (dto.getType().equals(Protocol.DICEROLL)) {
						System.out.println(dto.getId() + "DICEROLL 받음");
						// 받은 주사위값을 통해 클라이언트에 이미지로 띄우기
						laDice1.setIcon(diceShow(dto.getDice1()));
						laDice2.setIcon(diceShow(dto.getDice2()));
						laDice1.setVisible(true);
						laDice2.setVisible(true);
					}
					// 움직이기 구현, 주사위 이미지 띄우기 구현
					if (dto.getType().equals(Protocol.MOVE)) {
						int nextx,nexty,nextnum; //다음칸의 x, y, tilenum

						if (dto.getId().equals(player1.getId())) {
							// 주사위를 굴려 나온 만큼 이동하지 않았다면
							while (player1.getNowPlayerTile() != dto.getNewPlayerTile()) {
								nextx = tileList.get((player1.getNowPlayerTile() + 1) % 24).getTileX();
								nexty = tileList.get((player1.getNowPlayerTile() + 1) % 24).getTileY();
								nextnum = tileList.get((player1.getNowPlayerTile() + 1) % 24).getTileNum();
								player1.moveAnimation(nextx + 30, nexty + 30, nextnum);
								Thread.sleep(100); // moveAnimation 스레드가 끝날때 까지 대기.
							}
						} else if (dto.getId().equals(player2.getId())) {
							while (player2.getNowPlayerTile() != dto.getNewPlayerTile()) {
								nextx = tileList.get((player2.getNowPlayerTile() + 1) % 24).getTileX();
								nexty = tileList.get((player2.getNowPlayerTile() + 1) % 24).getTileY();
								nextnum = tileList.get((player2.getNowPlayerTile() + 1) % 24).getTileNum();
								player2.moveAnimation(nextx + 30, nexty + 60, nextnum);
								Thread.sleep(100);
							}
						} else if (dto.getId().equals(player3.getId())) {
							while (player3.getNowPlayerTile() != dto.getNewPlayerTile()) {
								nextx = tileList.get((player3.getNowPlayerTile() + 1) % 24).getTileX();
								nexty = tileList.get((player3.getNowPlayerTile() + 1) % 24).getTileY();
								nextnum = tileList.get((player3.getNowPlayerTile() + 1) % 24).getTileNum();
								player3.moveAnimation(nextx + 60, nexty + 30, nextnum);
								Thread.sleep(100);
							}
						} else if (dto.getId().equals(player4.getId())) {
							while (player4.getNowPlayerTile() != dto.getNewPlayerTile()) {
								nextx = tileList.get((player4.getNowPlayerTile() + 1) % 24).getTileX();
								nexty = tileList.get((player4.getNowPlayerTile() + 1) % 24).getTileY();
								nextnum = tileList.get((player4.getNowPlayerTile() + 1) % 24).getTileNum();
								player4.moveAnimation(nextx + 60, nexty + 60, nextnum);
								Thread.sleep(100);
							}
						}
						
						laDice1.setVisible(false);
						laDice2.setVisible(false);
					}
					// 월급
					if (dto.getType().equals(Protocol.MONTHLY)) {
						if ((player1.getId() != null) && (player1.getId().equals(dto.getId()))) {
							player1.setMoney(player1.getMoney()+dto.getSalary());
							player1Money.setText(Integer.toString(player1.getMoney()));
						} else if ((player2.getId() != null) && (player2.getId().equals(dto.getId()))) {
							player2.setMoney(player2.getMoney()+dto.getSalary());
							player2Money.setText(Integer.toString(player2.getMoney()));
						} else if ((player3.getId() != null) && (player3.getId().equals(dto.getId()))) {
							player3.setMoney(player3.getMoney()+dto.getSalary());
							player3Money.setText(Integer.toString(player3.getMoney()));
						} else if ((player4.getId() != null) && (player4.getId().equals(dto.getId()))) {
							player4.setMoney(player4.getMoney()+dto.getSalary());
							player4Money.setText(Integer.toString(player4.getMoney()));
						}
						
						if (dto.getId().equals(id)) {
							JOptionPane.showMessageDialog(null, "월급(300)을 받았습니다 !!");
						}
					}
					
					// 해당 플레이어가 다이얼로그 타입을 보냈으면 해당 타일 정보 받아옴.
					if (dto.getType().equals(Protocol.DIALOGREQUEST)) {
						if (dto.getId().equals(id)) {
							TILE = dto.getTileInfo();

							nowPrice = TILE.getPriceAll();
							nowBuild = TILE.getIsPurchased();

							if (TILE.getTileType() == 1) {
								if (dto.getTileInfo().getLandOwner().equals("")
										|| dto.getTileInfo().getLandOwner().equals(id)) {
									if (dto.getTileInfo().getIsPurchased().equals(allPurchasedCity)) {
										return;
									} else {
										new DialogCity(id);

										// isPurchased 가 allPurchasedCity 와 다를 경우 서버로 타일 변경 값 전송
										new Thread(new Runnable() {
											@Override
											public void run() {
												while (true) {
													try {
														Thread.sleep(500);
														if (isDialogCity == true) {
															RequestDto tempDto = new RequestDto();
															newBuild = TILE.getIsPurchased();
															int[] tempBuild = new int[4];

															tempDto.setType(Protocol.DIALOGUPDATE);
															tempDto.setTileInfo(TILE);
															writer.println(gson.toJson(tempDto));

															tempDto.setType(Protocol.PLAYERPURCHASED);
															tempDto.setId(id);
															tempDto.setNewprice(TILE.getPriceAll() - nowPrice);
															writer.println(gson.toJson(tempDto));

															tempDto.setType(Protocol.PLAYERBUILD);
															tempDto.setTileOwnerId(TILE.getLandOwner());
															for (int i = 1; i < nowBuild.length; i++) {
																tempBuild[i] = nowBuild[i] - newBuild[i];
															}
															tempDto.setNewBuild(tempBuild);
															tempDto.setNowPlayerTile(nowPlayerTile);
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
									new DialogFine(id);

									new Thread(new Runnable() {
										@Override
										public void run() {
											while (true) {
												try {
													Thread.sleep(500);
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
							} else if (TILE.getTileType() == 2) {
								if (dto.getTileInfo().getLandOwner().equals("")
										|| dto.getTileInfo().getLandOwner().equals(id)) {
									if (dto.getTileInfo().getIsPurchased().equals(allPurchasedIsland)) {
										return;
									} else {
										nowPrice = TILE.getPriceAll();
										new DialogIsland(id);

										// isPurchased 가 allPurchasedIsland와 다를 경우 타일 변경값 서버에 전송
										new Thread(new Runnable() {

											@Override
											public void run() {
												while (true) {
													try {
														Thread.sleep(500);
														if (isDialogIsland == true) {
															RequestDto tempDto = new RequestDto();

															tempDto.setType(Protocol.DIALOGUPDATE);
															tempDto.setTileInfo(TILE);
															writer.println(gson.toJson(tempDto));

															tempDto.setType(Protocol.PLAYERPURCHASED);
															tempDto.setId(id);
															tempDto.setNewprice(TILE.getPriceAll() - nowPrice);
															writer.println(gson.toJson(tempDto));

															tempDto.setType(Protocol.PLAYERISLAND);
															tempDto.setTileOwnerId(TILE.getLandOwner());
															if (TILE.getIsPurchased()[0] == 1) {
																tempDto.setNowPlayerTile(nowPlayerTile);
																writer.println(gson.toJson(tempDto));
															}

															isDialogIsland = false;

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
									new DialogFine(id);

									new Thread(new Runnable() {

										@Override
										public void run() {
											while (true) {
												try {
													Thread.sleep(500);
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
							} else if (TILE.getTileType() == 3) {
								
							} else if (TILE.getTileType() == 4) {
								isResting += 1;
							} else if (TILE.getTileType() == 5) {
								int tempCount = 0;
								for (int i = 0; i < tileList.size(); i++) {
									if ((tileList.get(i).getLandOwner() != null) && (tileList.get(i).getLandOwner().equals(id))) {
										tempCount++;
										break;
									}
								}
								
								if (tempCount == 0) {
									JOptionPane.showMessageDialog(null, "보유한 땅이 없습니다 ㅠㅠ");
								} else if (tempCount > 0) {
									new DialogOlympic(id, tileList);
									new Thread(new Runnable() {
										@Override
										public void run() {
											Tile tempTile = new Tile("선언용", 31, 31, 0, 0);
											int tempTileNum = 30;
											while (true) {
												try {
													Thread.sleep(500);
													if (isOlympic == true) {
														for (int i = 0; i < tileList.size(); i++) {
															if (tileList.get(i).getTileNum() == olympicTileNum) {
																tileList.get(i).setOlympicCount(tileList.get(i).getOlympicCount()+1);
																tempTileNum = i;
																tempTile = tileList.get(i);
																break;
															}
														}
														RequestDto tempDto = new RequestDto();
														
														tempDto.setType(Protocol.DIALOGUPDATE);
														tempDto.setTileNum(tempTileNum);
														tempDto.setTileInfo(tempTile);
														
														tempDto.setType(Protocol.OLYMPIC);
														tempDto.setTileNum(tempTileNum);
														writer.println(gson.toJson(tempDto));
														
														isOlympic = false;
														
														break;
													}
												} catch (InterruptedException e) {
													e.printStackTrace();
												}
											}
										}
									}).start();
								}
								
							} else if (TILE.getTileType() == 6) {
								
							}
							
							
							if (isDouble == 1) {
								btnDiceRoll.setVisible(true);
								isTurn = true;
							} else if ((isDouble == 0) || (isDouble == 2)) {
								isTurn = false;
								btnDiceRoll.setVisible(false);
							}
							if (isTurn == false) {
								btnEndTurn.setVisible(true);
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

					if (dto.getType().equals(Protocol.PLAYERBUILD)) {
						String tempId = dto.getTileOwnerId();
						int[] tempArray = dto.getNewBuild();
						int tempX = dto.getBuildX();
						int tempY = dto.getBuildY();
						int tempTileNum = dto.getNowPlayerTile();
						new Thread(new Runnable() {
							@Override
							public void run() {
								buildUp(tempId, tempArray, tempX, tempY, tempTileNum);
							}
						}).start();
					}

					if (dto.getType().equals(Protocol.PLAYERISLAND)) {
						String tempId = dto.getTileOwnerId();
						int tempTileNum = dto.getNowPlayerTile();
						new Thread(new Runnable() {
							@Override
							public void run() {
								buildUpIsland(tempId, tempTileNum);
							}
						}).start();
					}

					if (dto.getType().equals(Protocol.PLAYERFINE)) {

						// 타일 주인 플레이어에게 통행료 만큼 돈을 넣음
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

						// 걸린 플레이어에게 통행료 만큼 돈을 차감
						// 벌금으로 인해 플레이어의 현금이 0이하가 되면 플레이어 아웃
						if (dto.getId().equals(player1.getId())) {
							player1.setMoney(player1.getMoney() - dto.getTileFine());
							if (player1.getMoney() < 0) {
								player1Money.setText("GAME OVER");
								player1Money.setFont(new Font("CookieRun BLACK", Font.BOLD, 10));
								if (dto.getId().equals(id)) {
									isDouble = 0;
									isPlaying = false;

									RequestDto tempDto = new RequestDto();
									tempDto.setType(Protocol.PLAYERDIE);
									tempDto.setId(id);
									writer.println(gson.toJson(tempDto));

									break;
								}
							} else
								player1Money.setText(Integer.toString(player1.getMoney()));
						} else if (dto.getId().equals(player2.getId())) {
							player2.setMoney(player2.getMoney() - dto.getTileFine());
							if (player2.getMoney() < 0) {
								player2Money.setText("GAME OVER");
								player2Money.setFont(new Font("CookieRun BLACK", Font.BOLD, 10));
								if (dto.getId().equals(id)) {
									isDouble = 0;
									isPlaying = false;

									new Thread(new Runnable() {
										@Override
										public void run() {
											while (true) {
												try {
													Thread.sleep(1000);
													RequestDto tempDto = new RequestDto();
													tempDto.setType(Protocol.PLAYERDIE);
													tempDto.setId(id);
													writer.println(gson.toJson(tempDto));

													break;
												} catch (InterruptedException e) {
													e.printStackTrace();
												}
											}
										}
									}).start();
								}
							} else
								player2Money.setText(Integer.toString(player2.getMoney()));
						} else if (dto.getId().equals(player3.getId())) {
							player3.setMoney(player3.getMoney() - dto.getTileFine());
							if (player3.getMoney() < 0) {
								player3Money.setText("GAME OVER");
								player3Money.setFont(new Font("CookieRun BLACK", Font.BOLD, 10));
								if (dto.getId().equals(id)) {
									isDouble = 0;
									isPlaying = false;

									new Thread(new Runnable() {
										@Override
										public void run() {
											while (true) {
												try {
													Thread.sleep(1000);
													RequestDto tempDto = new RequestDto();
													tempDto.setType(Protocol.PLAYERDIE);
													tempDto.setId(id);
													writer.println(gson.toJson(tempDto));

													break;
												} catch (InterruptedException e) {
													e.printStackTrace();
												}
											}
										}
									}).start();
								}
							} else
								player3Money.setText(Integer.toString(player3.getMoney()));
						} else if (dto.getId().equals(player4.getId())) {
							player4.setMoney(player4.getMoney() - dto.getTileFine());
							if (player4.getMoney() < 0) {
								player4Money.setText("GAME OVER");
								player4Money.setFont(new Font("CookieRun BLACK", Font.BOLD, 10));
								if (dto.getId().equals(id)) {
									isDouble = 0;
									isPlaying = false;

									new Thread(new Runnable() {
										@Override
										public void run() {
											while (true) {
												try {
													Thread.sleep(1000);
													RequestDto tempDto = new RequestDto();
													tempDto.setType(Protocol.PLAYERDIE);
													tempDto.setId(id);
													writer.println(gson.toJson(tempDto));

													break;
												} catch (InterruptedException e) {
													e.printStackTrace();
												}
											}
										}
									}).start();
								}
							} else
								player4Money.setText(Integer.toString(player4.getMoney()));
						}
					}

					if (dto.getType().equals(Protocol.DIALOGUPDATE)) {
						Tile tempTile = dto.getTileInfo();
						int tempTileNum = dto.getTileNum();
						tileList.set(tempTileNum, tempTile);
					}
					if (dto.getType().equals(Protocol.NEXTTURN)) {
						RequestDto tempDto = new RequestDto();
						if (isPlaying == false) {
							btnDiceRoll.setVisible(false);
							btnEndTurn.setVisible(false);
							
							tempDto.setType(Protocol.ENDTURN);
							tempDto.setGubun(Protocol.ENDTURN);
							tempDto.setId(id);
							writer.println(gson.toJson(tempDto));
							
							tempDto.setType(Protocol.NEXTTURN);
							tempDto.setId(id);
							writer.println(gson.toJson(tempDto));
						}
						else if (dto.getTurnId().equals(id)) {
							btnDiceRoll.setVisible(true);
						}
						if (dto.getGubun().equals(Protocol.TURNSEQUENCE)) {
							totalTurn++;
						}
					}
					
					if (dto.getType().equals(Protocol.OLYMPIC)) {
						int doubleCount = (int)(Math.pow(2, tileList.get(dto.getTileNum()).getOlympicCount()));
						tileList.get(dto.getTileNum()).setFine((tileList.get(dto.getTileNum()).getFine() * doubleCount));
						showOlympic(dto.getTileNum(), doubleCount);
					}

					if (dto.getType().equals(Protocol.ENDGAME)) {
						btnDiceRoll.setVisible(false);
						btnEndTurn.setVisible(false);
						btnStart.setVisible(false);
						laDice1.setVisible(false);
						laDice2.setVisible(false);
						
						if (dto.getGubun().equals(Protocol.WIN)) {
							JOptionPane.showMessageDialog(null, "축하합니다. 승리하셨습니다 !");
							boardCenter.setIcon(new ImageIcon("images/bg_win.jpg"));
						}
						
						if (dto.getGubun().equals(Protocol.LOSE)) {
							JOptionPane.showMessageDialog(null, "아쉽군요. 패배하셨습니다 ;-(");
							boardCenter.setIcon(new ImageIcon("images/bg_lose.jpg"));
						}
					}
					
					// 채팅 시스템 구현
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
			System.out.println(TAG + id + "연결 성공");
		} catch (Exception e) {
			System.out.println(TAG + id + "연결 실패");

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

	private void buildUp(String id, int[] intArray, int x, int y, int tileNum) {
		Color playerColor = new Color(0, 0, 0);
		int isHouse = intArray[1];
		int isBuilding = intArray[2];
		int isHotel = intArray[3];
		JLabel houseLabel = new JLabel();
		houseLabel.setIcon(new ImageIcon("images/img_house.png"));
		JLabel buildingLabel = new JLabel();
		buildingLabel.setIcon(new ImageIcon("images/img_building.png"));
		JLabel hotelLabel = new JLabel();
		hotelLabel.setIcon(new ImageIcon("images/img_hotel.png"));
		int width = 30;
		int height = 30;

		if (player1.getId().equals(id)) {
			playerColor = new Color(254, 236, 203);
		} else if (player2.getId().equals(id)) {
			playerColor = new Color(153, 153, 255);
		} else if (player3.getId().equals(id)) {
			playerColor = new Color(153, 255, 255);
		} else if (player4.getId().equals(id)) {
			playerColor = new Color(102, 255, 51);
		}

		System.out.println("buildUp 실행");
		System.out.println(intArray[1]);
		System.out.println("isHouse : " + isHouse);
		System.out.println(intArray[2]);
		System.out.println("isBuilding : " + isBuilding);
		System.out.println(intArray[3]);
		System.out.println("isHotel : " + isHotel);
		System.out.println("tileNum : " + tileNum);
		System.out.println("color : " + playerColor);

		if (tileNum == 1) {
			board1CityName.setOpaque(true);
			board1CityName.setBackground(playerColor);
			if (isHouse == -1) {
				houseLabel.setBounds(3, 3, width, height);
				board1BldImage.add(houseLabel);
			}
			if (isBuilding == -1) {
				buildingLabel.setBounds(35, 3, width, height);
				board1BldImage.add(buildingLabel);
			}
			if (isHotel == -1) {
				hotelLabel.setBounds(67, 3, width, height);
				board1BldImage.add(hotelLabel);
			}
		} else if (tileNum == 3) {
			board3CityName.setOpaque(true);
			board3CityName.setBackground(playerColor);
			if (isHouse == -1) {
				houseLabel.setBounds(3, 3, width, height);
				board3BldImage.add(houseLabel);
			}
			if (isBuilding == -1) {
				buildingLabel.setBounds(35, 3, width, height);
				board3BldImage.add(buildingLabel);
			}
			if (isHotel == -1) {
				hotelLabel.setBounds(67, 3, width, height);
				board3BldImage.add(hotelLabel);
			}
		} else if (tileNum == 4) {
			board3CityName.setOpaque(true);
			board3CityName.setBackground(playerColor);
			if (isHouse == -1) {
				houseLabel.setBounds(3, 3, width, height);
				board4BldImage.add(houseLabel);
			}
			if (isBuilding == -1) {
				buildingLabel.setBounds(35, 3, width, height);
				board4BldImage.add(buildingLabel);
			}
			if (isHotel == -1) {
				hotelLabel.setBounds(67, 3, width, height);
				board4BldImage.add(hotelLabel);
			}
		} else if (tileNum == 5) {
			board5CityName.setOpaque(true);
			board5CityName.setBackground(playerColor);
			if (isHouse == -1) {
				houseLabel.setBounds(3, 3, width, height);
				board5BldImage.add(houseLabel);
			}
			if (isBuilding == -1) {
				buildingLabel.setBounds(35, 3, width, height);
				board5BldImage.add(buildingLabel);
			}
			if (isHotel == -1) {
				hotelLabel.setBounds(67, 3, width, height);
				board5BldImage.add(hotelLabel);
			}
		} else if (tileNum == 7) {
			board7CityName.setOpaque(true);
			board7CityName.setBackground(playerColor);
			if (isHouse == -1) {
				houseLabel.setBounds(3, 3, width, height);
				board7BldImage.add(houseLabel);
			}
			if (isBuilding == -1) {
				buildingLabel.setBounds(3, 35, width, height);
				board7BldImage.add(buildingLabel);
			}
			if (isHotel == -1) {
				hotelLabel.setBounds(3, 67, width, height);
				board7BldImage.add(hotelLabel);
			}
		} else if (tileNum == 8) {
			board8CityName.setOpaque(true);
			board8CityName.setBackground(playerColor);
			if (isHouse == -1) {
				houseLabel.setBounds(3, 3, width, height);
				board8BldImage.add(houseLabel);
			}
			if (isBuilding == -1) {
				buildingLabel.setBounds(3, 35, width, height);
				board8BldImage.add(buildingLabel);
			}
			if (isHotel == -1) {
				hotelLabel.setBounds(3, 67, width, height);
				board8BldImage.add(hotelLabel);
			}
		} else if (tileNum == 9) {
			board9CityName.setOpaque(true);
			board9CityName.setBackground(playerColor);
			if (isHouse == -1) {
				houseLabel.setBounds(3, 3, width, height);
				board9BldImage.add(houseLabel);
			}
			if (isBuilding == -1) {
				buildingLabel.setBounds(3, 35, width, height);
				board9BldImage.add(buildingLabel);
			}
			if (isHotel == -1) {
				hotelLabel.setBounds(3, 67, width, height);
				board9BldImage.add(hotelLabel);
			}
		} else if (tileNum == 11) {
			board11CityName.setOpaque(true);
			board11CityName.setBackground(playerColor);
			if (isHouse == -1) {
				houseLabel.setBounds(3, 3, width, height);
				board11BldImage.add(houseLabel);
			}
			if (isBuilding == -1) {
				buildingLabel.setBounds(3, 35, width, height);
				board11BldImage.add(buildingLabel);
			}
			if (isHotel == -1) {
				hotelLabel.setBounds(3, 67, width, height);
				board11BldImage.add(hotelLabel);
			}
		} else if (tileNum == 13) {
			board13CityName.setOpaque(true);
			board13CityName.setBackground(playerColor);
			if (isHouse == -1) {
				houseLabel.setBounds(3, 3, width, height);
				board13BldImage.add(houseLabel);
			}
			if (isBuilding == -1) {
				buildingLabel.setBounds(35, 3, width, height);
				board13BldImage.add(buildingLabel);
			}
			if (isHotel == -1) {
				hotelLabel.setBounds(67, 3, width, height);
				board13BldImage.add(hotelLabel);
			}
		} else if (tileNum == 14) {
			board14CityName.setOpaque(true);
			board14CityName.setBackground(playerColor);
			if (isHouse == -1) {
				houseLabel.setBounds(3, 3, width, height);
				board14BldImage.add(houseLabel);
			}
			if (isBuilding == -1) {
				buildingLabel.setBounds(35, 3, width, height);
				board14BldImage.add(buildingLabel);
			}
			if (isHotel == -1) {
				hotelLabel.setBounds(67, 3, width, height);
				board14BldImage.add(hotelLabel);
			}
		} else if (tileNum == 15) {
			board15CityName.setOpaque(true);
			board15CityName.setBackground(playerColor);
			if (isHouse == -1) {
				houseLabel.setBounds(3, 3, width, height);
				board15BldImage.add(houseLabel);
			}
			if (isBuilding == -1) {
				buildingLabel.setBounds(35, 3, width, height);
				board15BldImage.add(buildingLabel);
			}
			if (isHotel == -1) {
				hotelLabel.setBounds(67, 3, width, height);
				board15BldImage.add(hotelLabel);
			}
		} else if (tileNum == 17) {
			board17CityName.setOpaque(true);
			board17CityName.setBackground(playerColor);
			if (isHouse == -1) {
				houseLabel.setBounds(3, 3, width, height);
				board17BldImage.add(houseLabel);
			}
			if (isBuilding == -1) {
				buildingLabel.setBounds(35, 3, width, height);
				board17BldImage.add(buildingLabel);
			}
			if (isHotel == -1) {
				hotelLabel.setBounds(67, 3, width, height);
				board17BldImage.add(hotelLabel);
			}
		} else if (tileNum == 20) {
			board20CityName.setOpaque(true);
			board20CityName.setBackground(playerColor);
			if (isHouse == -1) {
				houseLabel.setBounds(3, 3, width, height);
				board20BldImage.add(houseLabel);
			}
			if (isBuilding == -1) {
				buildingLabel.setBounds(3, 35, width, height);
				board20BldImage.add(buildingLabel);
			}
			if (isHotel == -1) {
				hotelLabel.setBounds(3, 67, width, height);
				board20BldImage.add(hotelLabel);
			}
		} else if (tileNum == 21) {
			board21CityName.setOpaque(true);
			board21CityName.setBackground(playerColor);
			if (isHouse == -1) {
				houseLabel.setBounds(3, 3, width, height);
				board21BldImage.add(houseLabel);
			}
			if (isBuilding == -1) {
				buildingLabel.setBounds(3, 35, width, height);
				board21BldImage.add(buildingLabel);
			}
			if (isHotel == -1) {
				hotelLabel.setBounds(3, 67, width, height);
				board21BldImage.add(hotelLabel);
			}
		} else if (tileNum == 22) {
			board22CityName.setOpaque(true);
			board22CityName.setBackground(playerColor);
			if (isHouse == -1) {
				houseLabel.setBounds(3, 3, width, height);
				board22BldImage.add(houseLabel);
			}
			if (isBuilding == -1) {
				buildingLabel.setBounds(3, 35, width, height);
				board22BldImage.add(buildingLabel);
			}
			if (isHotel == -1) {
				hotelLabel.setBounds(3, 67, width, height);
				board22BldImage.add(hotelLabel);
			}
		} else if (tileNum == 23) {
			board23CityName.setOpaque(true);
			board23CityName.setBackground(playerColor);
			if (isHouse == -1) {
				houseLabel.setBounds(3, 3, width, height);
				board23BldImage.add(houseLabel);
			}
			if (isBuilding == -1) {
				buildingLabel.setBounds(3, 35, width, height);
				board23BldImage.add(buildingLabel);
			}
			if (isHotel == -1) {
				hotelLabel.setBounds(3, 67, width, height);
				board23BldImage.add(hotelLabel);
			}
		}
		houseLabel.repaint();
		buildingLabel.repaint();
		hotelLabel.repaint();
	}

	private void buildUpIsland(String id, int tileNum) {
		Color playerColor = new Color(0, 0, 0);

		if (player1.getId().equals(id)) {
			playerColor = new Color(254, 236, 203);
		} else if (player2.getId().equals(id)) {
			playerColor = new Color(153, 153, 255);
		} else if (player3.getId().equals(id)) {
			playerColor = new Color(153, 255, 255);
		} else if (player4.getId().equals(id)) {
			playerColor = new Color(102, 255, 51);
		}

		if (tileNum == 4) {
			board4CityName.setBackground(playerColor);
			board4CityName.setOpaque(true);
			board4CityName.repaint();
		}

		if (tileNum == 7) {
			board7CityName.setBackground(playerColor);
			board7CityName.setOpaque(true);
			board7CityName.repaint();
		}

		if (tileNum == 15) {
			board15CityName.setBackground(playerColor);
			board15CityName.setOpaque(true);
			board15CityName.repaint();
		}

		if (tileNum == 23) {
			board23CityName.setBackground(playerColor);
			board23CityName.setOpaque(true);
			board23CityName.repaint();
		}
	}
	
	// 올림픽 배율 띄우기
	private void showOlympic(int tileNum, int doubleCount) {
		String text = "X" + doubleCount;
		
		switch (tileNum) {
		case 1: board1Centerla.setText(text);
			break;
		case 3: board3Centerla.setText(text);
			break;
		case 4: board4Centerla.setText(text);
			break;
		case 5: board5Centerla.setText(text);
			break;
		case 7: board7Centerla.setText(text);
			break;
		case 8: board8Centerla.setText(text);
			break;
		case 9: board9Centerla.setText(text);
			break;
		case 11: board11Centerla.setText(text);
			break;
		case 13: board13Centerla.setText(text);
			break;
		case 14: board14Centerla.setText(text);
			break;
		case 15: board15Centerla.setText(text);
			break;
		case 17: board17Centerla.setText(text);
			break;
		case 20: board20Centerla.setText(text);
			break;
		case 21: board21Centerla.setText(text);
			break;
		case 22: board22Centerla.setText(text);
			break;
		case 23: board23Centerla.setText(text);
			break;
		}
	}

	// 양 옆 세로라벨
	private class RotatedLabel extends JLabel {
		char[] tmpTextList;

		public RotatedLabel() {

		}

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
	
	// 마우스 커서 변경 (클릭 뗄 시)
	private void noClickCursor() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image cursorimage = tk.getImage("images/img_mouse_noclick.png");
		Point point = new Point(10,10);
		Cursor cursor = tk.createCustomCursor(cursorimage, point, "haha");
		setCursor(cursor); 
	}
	// 마우스 커서 변경 (클릭시)
	private void onClickCursor() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image cursorimage = tk.getImage("images/img_mouse_click.png");
		Point point = new Point(10,10);
		Cursor cursor = tk.createCustomCursor(cursorimage, point, "haha");
		setCursor(cursor);

	}
}
