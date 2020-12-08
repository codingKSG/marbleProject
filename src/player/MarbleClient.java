package player;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
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
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.google.gson.Gson;

import protocol.JFrameSet;
import protocol.Protocol;
import protocol.RequestDto;

public class MarbleClient extends JFrame implements JFrameSet{
	
	private MarbleClient marbleClient = this;
	private final static String TAG = "MarbleClient : ";
	
	private ClientPlayerThread cpt;
	private Socket socket;
	private String id;
	private int dice1;
	private int dice2;
	private int nowBoardNum = 0;
	private int nowX = 240;
	private int nowY = 240;
	
	private JLayeredPane board0, board1, board2, board3, board4, board5, board6, board7;
	private Container c;
	private JButton btnDiceRoll;
	private Player player1;
	private JLabel laDice;
	
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
		
		board0 = new JLayeredPane();
		board1 = new JLayeredPane();
		board2 = new JLayeredPane();
		board3 = new JLayeredPane();
		board4 = new JLayeredPane();
		board5 = new JLayeredPane();
		board6 = new JLayeredPane();
		board7 = new JLayeredPane();
		laDice = new JLabel("");
		btnDiceRoll = new JButton("주사위 굴리기");
		player1 = new Player(nowX, nowY, id);
		c = getContentPane();
		
	}

	@Override
	public void setting() {
		
		setTitle("Marble Client" + " : " + id);
		setSize(330,330);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		board0.setBorder(new LineBorder(new Color(0, 0, 0)));
		board1.setBorder(new LineBorder(new Color(0, 0, 0)));
		board2.setBorder(new LineBorder(new Color(0, 0, 0)));
		board3.setBorder(new LineBorder(new Color(0, 0, 0)));
		board4.setBorder(new LineBorder(new Color(0, 0, 0)));
		board5.setBorder(new LineBorder(new Color(0, 0, 0)));
		board6.setBorder(new LineBorder(new Color(0, 0, 0)));
		board7.setBorder(new LineBorder(new Color(0, 0, 0)));
		player1.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		c.setLayout(null);
		player1.setLayout(null);
		
		player1.setForeground(Color.BLUE);
		player1.setBackground(Color.BLUE);
		player1.setBounds(nowX, nowY, 40, 40);
		laDice.setBounds(124, 143, 57, 15);

		btnDiceRoll.setBounds(100, 110, 100, 23);
		
		board0.setBounds(200, 200, 100, 100);
		getContentPane().add(board0);
		
		board1.setBounds(0, 0, 100, 100);
		getContentPane().add(board1);
		
		board2.setBounds(0, 100, 100, 100);
		getContentPane().add(board2);
		
		board3.setBounds(0, 200, 100, 100);
		getContentPane().add(board3);
		
		board4.setBounds(100, 200, 100, 100);
		getContentPane().add(board4);
		
		board5.setBounds(100, 0, 100, 100);
		getContentPane().add(board5);
		
		board6.setBounds(200, 0, 100, 100);
		getContentPane().add(board6);
		
		board7.setBounds(200, 100, 100, 100);
		getContentPane().add(board7);

	}

	@Override
	public void batch() {
		add(board0);
		add(board1);
		add(board2);
		add(board3);
		add(board4);
		add(board5);
		add(board6);
		add(board7);
		add(btnDiceRoll);
		add(player1);
		add(laDice);
	}

	@Override
	public void listener() {
		btnDiceRoll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cpt.playerRoll();
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
		
		public ClientPlayerThread(Socket socket, String id) {
			this.socket = socket;
			this.id = id;
			this.dto = new RequestDto();
			this.gson = new Gson();
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new PrintWriter(socket.getOutputStream(), true);
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
			
			dto.setGubun(Protocol.GAME);
			dto.setType(Protocol.DICEROLL);
			dto.setId(id);
			dto.setDice1(dice1);
			dto.setDice2(dice2);
			
			output = gson.toJson(dto);
			writer.println(output);
			
			System.out.println(TAG + "playerRoll 실행");
		}
		
		private void move(int x, int y, String id) {
			if (!(this.id.equals(id))) {
				return;
			}
			dto.setId(id);
			dto.setGubun(Protocol.GAME);
			dto.setType(Protocol.MOVE);
			int newBoardNum = (int)((dice1 + dice2) % 8);
			dto.setNewBoardNum(newBoardNum);
			
			if (newBoardNum == 0) {
				dto.setNewX(240);
				dto.setNewY(240);
			} else if (newBoardNum == 1) {
				dto.setNewX(132);
				dto.setNewY(240);
			} else if (newBoardNum == 2) {
				dto.setNewX(26);
				dto.setNewY(240);
			} else if (newBoardNum == 3) {
				dto.setNewX(26);
				dto.setNewY(131);
			} else if (newBoardNum == 4) {
				dto.setNewX(26);
				dto.setNewY(26);
			} else if (newBoardNum == 5) {
				dto.setNewX(132);
				dto.setNewY(26);
			} else if (newBoardNum == 6) {
				dto.setNewX(240);
				dto.setNewY(26);
			} else if (newBoardNum == 7) {
				dto.setNewX(240);
				dto.setNewY(131);
			}
			output = gson.toJson(dto);
			writer.println(output);
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
					if (dto.getType().equals(Protocol.DICEROLL)) {
						laDice.setText(dto.getId() + ": " + dto.getDice1() + "," + dto.getDice2());
					}
					
					if (dto.getType().equals(Protocol.MOVE)) {
						cpt.move(dto.getNewX(), dto.getNewY(), dto.getId());
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
	
	public static void main(String[] args) {
		new MarbleClient("유저1");
	}
}
