package player;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.google.gson.Gson;

import protocol.GameDto;
import protocol.JFrameSet;
import protocol.Protocol;
import protocol.RequestDto;

public class MarbleClient extends JFrame implements JFrameSet{
	
	private Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;
	
	private MarbleClient marbleClient = this;
	private final static String TAG = "MarbleClient : ";
	private String id;
	private int dice1;
	private int dice2;
	
	private JPanel tile0 ,tile1, tile2, tile3, tile4, tile5, tile6, tile7, tileCenter;
	private Container c;
	
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
		connect();
		tile0 = new JPanel();
		tile1 = new JPanel();
		tile2 = new JPanel();
		tile3 = new JPanel();
		tile4 = new JPanel();
		tile5 = new JPanel();
		tile6 = new JPanel();
		tile7 = new JPanel();
		tileCenter = new JPanel();
		
		c = getContentPane();
		setLayout(new GridLayout(3,3,5,5));
	}

	@Override
	public void setting() {
		setTitle("Marble Client" + " : " + id);
		setSize(800,800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		tileCenter.setSize(500,500);
		
		c.setBackground(Color.BLACK);
		
	}

	@Override
	public void batch() {
		add(tile4);
		add(tile5);
		add(tile6);
		add(tile3);
		add(tileCenter);
		add(tile7);
		add(tile2);
		add(tile1);
		add(tile0);
		
	}

	@Override
	public void listener() {
		c.addMouseListener(new MouseAdapter() {
			
		});
	}
	
	
	class groundLocation {
		int location;
	}
	
	class player{
		int location = 0;
		
		void location(int dice) {
			this.location = location + dice;
			if(location >= 8) location -= 8;
		}
	}
	
	class ClientPlayerThread {
		
		private Socket socket;
		private ClientChatWriter ccw;
		private ClientChatReader ccr;
		private ClientGameWriter cgw;
		private ClientGameReader cgr;
		
		public ClientPlayerThread(Socket socket) {
			this.socket = socket;
		}
		
		private void start() {
			ccw = new ClientChatWriter(socket);
			ccw.start();
			System.out.println(TAG + "ClientChatThread 실행");
			cgw = new ClientGameThread(socket);
			cgw.start();
			System.out.println(TAG + "ClientGameThread 실행");
		}

		private void playerRoll() {
			int tempDice1 = dice.nextInt(6)+1;
			int tempDice2 = dice.nextInt(6)+1;
			dice1 = tempDice1;
			dice2 = tempDice2;
			
			cgt.gameDto.setGubun("DICEROLL");
			cgt.gameDto.setDice1(dice1);
			cgt.gameDto.setDice2(dice2);
			
			
		}
		
	}
	
	class ClientChatWriter extends Thread {
		private String id;
		private GameDto gameDto;
		private BufferedReader clientChatReader;
		private PrintWriter clientChatWriter;
		
		public ClientChatWriter(Socket socket) {
			try {
				clientChatReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				clientChatWriter = new PrintWriter(socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			String text = null;
			Gson gson = new Gson();
			gameDto = new GameDto();
			
		}
	}
	
	class ClientChatReader extends Thread {
		
		@Override
		public void run() {
			String text = null;
			RequestDto dto = new RequestDto();
			Gson gson = new Gson();
			try {
				while ((text = reader.readLine()) != null) {
						dto = gson.fromJson(text, dto.getClass()); 
						
						if (dto.getGubun().equals("GAME")) {
							GameDto dto1 = (GameDto)dto;
							dice1 = dto1.getDice1();
							dice2 = dto1.getDice2();
						}
					}
			} catch (IOException e) {
				System.out.println(TAG + "연결 종료");
			}
		}
	}
	
	class ClientGameWriter extends Thread {
		private String id;
		private GameDto gameDto;
		private BufferedReader gameReader;
		private PrintWriter gameWriter;
		private ClientGameReader cgr;
		
		public ClientGameWriter(Socket socket) {
			try {
				gameReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				gameWriter = new PrintWriter(socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			cgr = new ClientGameReader();
			cgr.start();
			
			String text = null;
			Gson gson = new Gson();
			gameDto = new GameDto();
			try {
				while ((text = gameReader.readLine()) != null) {
					gameDto = gson.fromJson(text, GameDto.class);
					
					dice1 = gameDto.getDice1();
					dice2 = gameDto.getDice2();
				}
			} catch (IOException e) {
				System.out.println(TAG + "연결 종료");
			}
		}
	}
	
	class ClientGameReader extends Thread {
		@Override
		public void run() {
			String text = null;
			RequestDto dto = new RequestDto();
			Gson gson = new Gson();
			try {
				while ((text = gameReader.readLine()) != null) {
						dto = gson.fromJson(text, dto.getClass()); 
						
						if (dto.getGubun().equals("GAME")) {
							GameDto dto1 = (GameDto)dto;
							dice1 = dto1.getDice1();
							dice2 = dto1.getDice2();
						}
					}
			} catch (IOException e) {
				System.out.println(TAG + "연결 종료");
			}
		}
	}
	
	private void connect() {
		String host = Protocol.HOST;
		int port = Protocol.PORT;
		try {
			socket = new Socket(host, port);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream());
			ClientPlayerThread cpt = new ClientPlayerThread(socket);
			cpt.start();
			System.out.println(TAG + id + " 연결 성공");
		} catch (Exception e) {
			System.out.println(TAG + id + "연결 실패");
		}
	}
	
	public static void main(String[] args) {
		new MarbleClient("유저1");
	}
}
