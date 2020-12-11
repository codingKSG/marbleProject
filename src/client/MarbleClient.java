package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import java.awt.Font;
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
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.google.gson.Gson;

import object.Tile;
import protocol.JFrameSet;
import protocol.Protocol;
import protocol.RequestDto;

public class MarbleClient extends JFrame implements JFrameSet {

	private MarbleClient marbleClient = this;
	private final static String TAG = "MarbleClient : ";
	static Tile TILE;
	static boolean isDialog = false;

	private ClientPlayerThread cpt;
	private Socket socket;
	private String id;
	private int dice1;
	private int dice2;
	private int nowPlayerTile = 0;
	private int playerX = 240;
	private int playerY = 240;
	private String playerImageSource;
	private boolean isTurn = false; // 현재 플레이어의 턴인지
	boolean isPlaying = true; // 플레이어 생존 여부

	private JLayeredPane board0, board1, board2, board3, board4, board5, board6, board7, board8, board9, board10,
			board11, board12, board13, board14, board15, board16, board17, board18, board19, board20, board21, board22,
			board23;
	private Container c;
	// 주사위굴리기 버튼, 시작버튼
	private JButton btnDiceRoll, btnStart;
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

	// 주사위 값 이미지 띄우는 라벨
	private JLabel laDice1, laDice2;

	Random dice = new Random();

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
		// 발판 new
		board0 = new JLayeredPane();
		board1 = new JLayeredPane();
		board2 = new JLayeredPane();
		board3 = new JLayeredPane();
		board4 = new JLayeredPane();
		board5 = new JLayeredPane();
		board6 = new JLayeredPane();
		board7 = new JLayeredPane();
		board8 = new JLayeredPane();
		board9 = new JLayeredPane();
		board10 = new JLayeredPane();
		board11 = new JLayeredPane();
		board12 = new JLayeredPane();
		board13 = new JLayeredPane();
		board14 = new JLayeredPane();
		board15 = new JLayeredPane();
		board16 = new JLayeredPane();
		board17 = new JLayeredPane();
		board18 = new JLayeredPane();
		board19 = new JLayeredPane();
		board20 = new JLayeredPane();
		board21 = new JLayeredPane();
		board22 = new JLayeredPane();
		board23 = new JLayeredPane();
		// 중간 보드
		boardCenter = new JLabel();
		// 주사위 눈 이미지
		laDice1 = new JLabel("");
		laDice2 = new JLabel("");
		// 주사위 버튼, 시작 버튼
		btnDiceRoll = new JButton("주사위 굴리기");
		btnStart = new JButton("게임 시작");
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
		// 플레이어1 이미지, 아이디, 보유 돈
		player1Img.setBounds(10, 10, 80, 80);
		player1Img.setBorder(new LineBorder(new Color(163, 112, 255)));
		player1Img.setVisible(false);
		player1Id.setBounds(100, 10, 80, 20);
		player1Id.setFont(new Font("CookieRun", Font.BOLD, 17));
		player1Money.setBounds(100, 75, 80, 20);
		player1Money.setFont(new Font("CookieRun", Font.BOLD, 14));
		// 플레이어2 이미지, 아이디, 보유 돈
		player2Img.setBounds(10, 10, 80, 80);
		player2Img.setBorder(new LineBorder(new Color(163, 163, 255)));
		player2Img.setVisible(false);
		player2Id.setBounds(100, 10, 80, 20);
		player2Id.setFont(new Font("CookieRun", Font.BOLD, 17));
		player2Money.setBounds(100, 75, 80, 20);
		player2Money.setFont(new Font("CookieRun", Font.BOLD, 14));
		// 플레이어3 이미지, 아이디, 보유 돈
		player3Img.setBounds(10, 10, 80, 80);
		player3Img.setBorder(new LineBorder(new Color(173, 255, 255)));
		player3Img.setVisible(false);
		player3Id.setBounds(100, 10, 80, 20);
		player3Id.setFont(new Font("CookieRun", Font.BOLD, 17));
		player3Money.setBounds(100, 75, 80, 20);
		player3Money.setFont(new Font("CookieRun", Font.BOLD, 14));
		// 플레이어4 이미지, 아이디, 보유 돈
		player4Img.setBounds(10, 10, 80, 80);
		player4Img.setBorder(new LineBorder(new Color(112, 255, 61)));
		player4Img.setVisible(false);
		player4Id.setBounds(100, 10, 80, 20);
		player4Id.setFont(new Font("CookieRun", Font.BOLD, 17));
		player4Money.setBounds(100, 75, 80, 20);
		player4Money.setFont(new Font("CookieRun", Font.BOLD, 14));
		// 오른쪽 채팅창
		playerChatPanel.setBounds(800, 400, 200, 400);
		scChatList.setSize(180, 370);
		playerChatField.setSize(200, 30);
		// 중간 보드
		boardCenter.setIcon(new ImageIcon("images/bg_board.png"));
		boardCenter.setBounds(150, 150, 500, 500);
		// 주사위 이미지
		laDice1.setBounds(155, 80, 90, 90);
		laDice2.setBounds(265, 80, 90, 90);
		// 주사위 굴리기 버튼
		btnDiceRoll.setBounds(200, 300, 100, 50);
		btnDiceRoll.setVisible(false);
		// 시작버튼
		btnStart.setBounds(200, 300, 100, 50);
		btnStart.setVisible(false);
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
		// 주사위 굴리기, 시작 버튼 배치
		add(btnDiceRoll);
		add(btnStart);
		// 중간 보드
		add(boardCenter);
		// 주사위 굴리기, 시작 버튼 배치
		boardCenter.add(btnDiceRoll);
		boardCenter.add(btnStart);
		// 주사위 값 배치 => 이미지 변경 예정
		boardCenter.add(laDice1);
		boardCenter.add(laDice2);
		// 캐릭터 이미지 배치
		add(player1);
		add(player2);
		add(player3);
		add(player4);
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
			int newPlayerTile = (int) ((nowPlayerTile + dice1 + dice2) % 24);

			dto.setGubun(Protocol.GAME);
			dto.setType(Protocol.DICEROLL);
			dto.setId(id);
			dto.setDice1(dice1);
			dto.setDice2(dice2);

			output = gson.toJson(dto);
			writer.println(output);

			System.out.println(TAG + "playerRoll 실행");
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
//		               if (dto.getType().equals(Protocol.IDCHECK)) {
//		                  JOptionPane.showMessageDialog(null, "이미 존재하는 ID입니다.\n 다른 ID를 사용해주세요 !");
//		                  System.exit(0);
//		                  setVisible(false);
//		               }

//		                클라이언트 내 플레이어 객체에 ID값 넣기
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
					// 플레이중이거나 4명 초과하면 프로그램 종료.(참여불가)
					if (dto.getType().equals(Protocol.PLAYERNUMCHECK)) {
						if (dto.getPlayerNum() == 4) {
							JOptionPane.showMessageDialog(null, "현재 플레이중인 유저가 많거나 게임이 이미 플레이중입니다.\n 나중에 시도해주세요.");
							System.exit(0);
							setDaemon(false);
						}
					}
					// 주사위굴리기 구현
					if (dto.getType().equals(Protocol.DICEROLL)) {
						laDice1.setText(Integer.toString(dto.getDice1()));
						laDice2.setText(Integer.toString(dto.getDice2()));
						System.out.println(dto.getId() + "DICEROLL 받음");
					}
					// 움직이기 구현, 주사위 이미지 띄우기 구현
					if (dto.getType().equals(Protocol.MOVE)) {
						// 움직이는 이미지
						if (dto.getId().equals(player1.getId())) {
							player1.moveAnimation(dto.getNewPlayerX() + 30, dto.getNewPlayerY() + 30,
									dto.getNewPlayerTile());
							player1.setNowPlayerTile(dto.getNewPlayerTile());
							System.out.println(player1.getId() + "의 nowPlayerTile은 :" + player1.getNowPlayerTile());
						} else if (dto.getId().equals(player2.getId())) {
							player2.moveAnimation(dto.getNewPlayerX() + 60, dto.getNewPlayerY() + 30,
									dto.getNewPlayerTile());
							player2.setNowPlayerTile(dto.getNewPlayerTile());
							System.out.println(player2.getId() + "의 nowPlayerTile은 :" + player2.getNowPlayerTile());
							System.out.println(dto.getId() + "MOVE 받음");
						} else if (dto.getId().equals(player3.getId())) {
							player3.moveAnimation(dto.getNewPlayerX() + 30, dto.getNewPlayerY() + 60,
									dto.getNewPlayerTile());
							player3.setNowPlayerTile(dto.getNewPlayerTile());
							System.out.println(player3.getId() + "의 nowPlayerTile은 :" + player3.getNowPlayerTile());
							System.out.println(dto.getId() + "MOVE 받음");
						} else if (dto.getId().equals(player4.getId())) {
							player4.moveAnimation(dto.getNewPlayerX() + 60, dto.getNewPlayerY() + 60,
									dto.getNewPlayerTile());
							player4.setNowPlayerTile(dto.getNewPlayerTile());
							System.out.println(player4.getId() + "의 nowPlayerTile은 :" + player4.getNowPlayerTile());
							System.out.println(dto.getId() + "MOVE 받음");
						}
						// 받은 주사위값을 통해 클라이언트에 이미지로 띄우기
						laDice1.setIcon(diceShow(dto.getDice1()));
						laDice2.setIcon(diceShow(dto.getDice2()));
					}
					// 해당 플레이어가 다이얼로그 타입을 보냈으면 해당 타일 정보 받아옴.
					if (dto.getType().equals(Protocol.DIALOGREQUEST)) {
						if (dto.getId().equals(id)) {

							if (dto.getTileType() == 0) {

							} else if (dto.getTileType() == 1) {
								TILE = dto.getTileInfo();
								new DiallogCity(id);
								new Thread(new Runnable() {
									@Override
									public void run() {
										while (true) {
											try {
												Thread.sleep(1000);
												if (isDialog == true) {
													RequestDto tempDto = new RequestDto();
													tempDto.setType(Protocol.DIALOGUPDATE);
													tempDto.setTileInfo(TILE);
													writer.println(gson.toJson(tempDto));
													isDialog = false;
													System.out.println(isDialog);
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

					// 채팅 시스템 구현
					if (dto.getType().equals(Protocol.CHAT)) {
						playerChatList.append(dto.getText());
						scChatList.getVAdjustable().setValue(scChatList.getVAdjustable().getMaximum());
					}

				}
			} catch (IOException e) {
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

	public static void main(String[] args) {
		new MarbleClient("유저1");
	}
}
