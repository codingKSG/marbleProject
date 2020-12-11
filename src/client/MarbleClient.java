package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

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

import object.CityTile;
import protocol.JFrameSet;
import protocol.Protocol;
import protocol.RequestDto;


public class MarbleClient extends JFrame implements JFrameSet{

	private MarbleClient marbleClient = this;
	private final static String TAG = "MarbleClient : ";
	
	static CityTile cityTile;

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

	private JLayeredPane board0, board1, board2, board3, board4, board5, board6, board7, board8, board9, board10, board11, board12, board13, board14, board15, board16, board17, board18, board19, board20, board21, board22, board23;
	private Container c;
	// �ֻ��������� ��ư, ���۹�ư
	private JButton btnDiceRoll, btnStart;
	// ������ �÷��̾�â
	private JPanel player1Info, player2Info, player3Info, player4Info, playerChatPanel;
	// ������ ä��â
	private ScrollPane scChatList;
	private JTextArea playerChatList;
	private JTextField playerChatField;
	// �÷��̾�(����) �̹��� ��ü - �÷������� �ƴϸ� �Ⱥ���
	private Player player1, player2, player3, player4;
	private int playerNum;
	// �ֻ��� �� ���� ��
	private JLabel laDice;

	Random dice = new Random();
	
	int[] arrayinit = {0, 0, 0, 0};

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
		// ���� new
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
		// �ֻ��� �� �� => �̹����� ���� ����
		laDice = new JLabel("");
		// �ֻ��� ��ư, ���� ��ư
		btnDiceRoll = new JButton("�ֻ��� ������");
		btnStart = new JButton("���� ����");
		// ���� ����Ʈ
		c = getContentPane();
		// �÷��̾� ��ü �̹���
		player1 = new Player(210, 210, "images/img_player01.png");
		player2 = new Player(210, 240, "images/img_player02.png");
		player3 = new Player(240, 210, "images/img_player03.png");
		player4 = new Player(240, 240, "images/img_player04.png");
		// ������ �÷��̾� ����â
		player1Info = new JPanel();
		player2Info = new JPanel();
		player3Info = new JPanel();
		player4Info = new JPanel();
		// ������ ä��â
		playerChatPanel = new JPanel();
		scChatList = new ScrollPane();
		playerChatList = new JTextArea();
		playerChatField = new JTextField(20);
		
		
		MarbleClient.cityTile = new CityTile("ȫ��", 1, 1, 550, 650, null, 0, arrayinit, 20, 24, 30, 36, 0);

	}

	@Override
	public void setting() {

		setTitle("Marble Client" + " : " + id);
		setSize(1000,840);
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
		
		// ������ �÷��̾�â
		player1Info.setBorder(new LineBorder(new Color(0, 0, 0)));
		player2Info.setBorder(new LineBorder(new Color(0, 0, 0)));
		player3Info.setBorder(new LineBorder(new Color(0, 0, 0)));
		player4Info.setBorder(new LineBorder(new Color(0, 0, 0)));
		// ������ ä��â
		playerChatPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		playerChatPanel.setLayout(new BorderLayout());
		playerChatList.setEditable(false);

		c.setLayout(null);
		// ������ �÷��̾�â
		player1Info.setBounds(800, 0, 200, 100);
		player1Info.setBackground(new Color(153, 102, 255));
		
		player2Info.setBounds(800, 100, 200, 100);
		player2Info.setBackground(new Color(153, 153, 255));
		
		player3Info.setBounds(800, 200, 200, 100);
		player3Info.setBackground(new Color(153, 255, 255));
		
		player4Info.setBounds(800, 300, 200, 100);
		player4Info.setBackground(new Color(102, 255, 51));
		// ������ ä��â
		playerChatPanel.setBounds(800, 400, 200, 400);
		scChatList.setSize(180, 370);
		playerChatField.setSize(200, 30);
		
		laDice.setBounds(124, 143, 57, 15);
		// �ֻ��� ������ ��ư
		btnDiceRoll.setBounds(100, 110, 100, 23);
		btnDiceRoll.setVisible(false);
		// ���۹�ư
		btnStart.setBounds(100, 110, 100, 23);
		System.out.println("START��ư ������");
		btnStart.setVisible(false);
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
		
		player1.setVisible(false);
		player2.setVisible(false);
		player3.setVisible(false);
		player4.setVisible(false);
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
		// �ֻ��� �� ��ġ => �̹��� ���� ����
		add(laDice);
		// ĳ���� �̹��� ��ġ
		add(player1);
		add(player2);
		add(player3);
		add(player4);
		// ������ �÷��̾�â
		add(player1Info);
		add(player2Info);
		add(player3Info);
		add(player4Info);
		// ������ ä��â
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
			int tempDice1 = dice.nextInt(6)+1;
			int tempDice2 = dice.nextInt(6)+1;
			dice1 = tempDice1;
			dice2 = tempDice2;
			int newPlayerTile = (int)((nowPlayerTile + dice1 + dice2) % 8);
			
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
			nowPlayerTile = newPlayerTile;
			
			if (newPlayerTile == 0) {
				dto.setNewPlayerX(240);
				dto.setNewPlayerY(240);
			} else if (newPlayerTile == 1) {
				dto.setNewPlayerX(132);
				dto.setNewPlayerY(240);
			} else if (newPlayerTile == 2) {
				dto.setNewPlayerX(26);
				dto.setNewPlayerY(240);
			} else if (newPlayerTile == 3) {
				dto.setNewPlayerX(26);
				dto.setNewPlayerY(131);
			} else if (newPlayerTile == 4) {
				dto.setNewPlayerX(26);
				dto.setNewPlayerY(26);
			} else if (newPlayerTile == 5) {
				dto.setNewPlayerX(132);
				dto.setNewPlayerY(26);
			} else if (newPlayerTile == 6) {
				dto.setNewPlayerX(240);
				dto.setNewPlayerY(26);
			} else if (newPlayerTile == 7) {
				dto.setNewPlayerX(240);
				dto.setNewPlayerY(131);
			}
			output = gson.toJson(dto);
			writer.println(output);
			System.out.println(TAG + "MOVE �����");
			
			if(nowPlayerTile == 3 || nowPlayerTile == 5) {
				System.out.println("�� Ÿ���Դϴ�.");
				new DiallogIsland(id);
			}else if(nowPlayerTile == 1 || nowPlayerTile == 2 || nowPlayerTile == 6) {
				System.out.println("��Ƽ Ÿ���Դϴ�.");
				new DiallogCity(id, cityTile);
				
			}else if(nowPlayerTile == 4 || nowPlayerTile == 7) {
				System.out.println("����� Ÿ���Դϴ�.");
				new DiallogSpecial(id);
			}else if(nowPlayerTile == 0){
				System.out.println("��� Ÿ���Դϴ�.");
				new DiallogStart(id);
			}
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
//					if (dto.getType().equals(Protocol.IDCHECK)) {
//						JOptionPane.showMessageDialog(null, "�̹� �����ϴ� ID�Դϴ�.\n �ٸ� ID�� ������ּ��� !");
//						System.exit(0);
//						setVisible(false);
//					}
					
//					 Ŭ���̾�Ʈ �� �÷��̾� ��ü�� ID�� �ֱ�
					if (dto.getType().equals(Protocol.PLAYERSET)) {
						if (dto.getPlayer1() != null) {
							player1.setId(dto.getPlayer1());
							player1.setVisible(true);
						}
						if (dto.getPlayer2() != null) {
							player2.setId(dto.getPlayer2());
							player2.setVisible(true);
						}
						if (dto.getPlayer3() != null) {
							player3.setId(dto.getPlayer3());
							player3.setVisible(true);
						}
						if (dto.getPlayer4() != null) {
							player4.setId(dto.getPlayer4());
							player4.setVisible(true);
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
						laDice.setText(dto.getId() + ": " + dto.getDice1() + "," + dto.getDice2());
						System.out.println(dto.getId() + "DICEROLL ����");
					}
					// �����̱� ����
					if (dto.getType().equals(Protocol.MOVE)) {
						System.out.println(dto.getId() + "MOVE ����");
						if (dto.getId().equals(player1.getId())) {
							player1.moveAnimation(dto.getNewPlayerX(), dto.getNewPlayerY(), dto.getNewPlayerTile());
							player1.setNowPlayerTile(dto.getNewPlayerTile());
							System.out.println(player1.getId() + "�� nowPlayerTile�� :" + player1.getNowPlayerTile());
						} else if (dto.getId().equals(player2.getId())) {
							player2.moveAnimation(dto.getNewPlayerX(), dto.getNewPlayerY(), dto.getNewPlayerTile());
							player2.setNowPlayerTile(dto.getNewPlayerTile());
							System.out.println(player2.getId() + "�� nowPlayerTile�� :" + player2.getNowPlayerTile());
							System.out.println(dto.getId() + "MOVE ����");
						} else if (dto.getId().equals(player3.getId())) {
							player3.moveAnimation(dto.getNewPlayerX(), dto.getNewPlayerY(), dto.getNewPlayerTile());
							player3.setNowPlayerTile(dto.getNewPlayerTile());
							System.out.println(player3.getId() + "�� nowPlayerTile�� :" + player3.getNowPlayerTile());
							System.out.println(dto.getId() + "MOVE ����");
						} else if (dto.getId().equals(player4.getId())) {
							player4.moveAnimation(dto.getNewPlayerX(), dto.getNewPlayerY(), dto.getNewPlayerTile());
							player4.setNowPlayerTile(dto.getNewPlayerTile());
							System.out.println(player4.getId() + "�� nowPlayerTile�� :" + player4.getNowPlayerTile());
							System.out.println(dto.getId() + "MOVE ����");
						}
					}
					
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
	
	public static void main(String[] args) {
		new MarbleClient("����1");
	}
}
