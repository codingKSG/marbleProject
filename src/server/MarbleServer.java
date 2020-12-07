package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import com.google.gson.Gson;

import object.Player;
import protocol.ChatDto;
import protocol.GameDto;
import protocol.Protocol;

public class MarbleServer {
	
	private MarbleServer marbleServer = this;
	private static final String TAG = "MarbleServer : ";
	private ServerSocket serverSocket;
	private Socket socket;
	private Vector<PlayerThread> vc;
	
	String nowPlayer ; // 현재 차례인 플레이어
	int countTurn ; // 현재 진행 턴 수
	int dice1 ; // 주사위 값을 보여주기 위한 것
	int dice2 ; // 주사위 값을 보여주기 위한 것
	int countPlayer ; // 현재 생존 플레이어 수
	Vector<Player> playerList ; // 플레이어 객체가 담긴 벡터

	void initSequence() {} // 시작 전 순서 정하기
	void sequenceFlow() {} // 턴 넘기기(다음 턴 플레이어의 isTurn을 true로 변경

	
	public MarbleServer() {
		// 입장한 유저가 Vector에 담김
		vc = new Vector<>();
		
		try {
			serverSocket = new ServerSocket(Protocol.PORT);
			System.out.println(TAG + "플레이어 접속 대기중....");
			// main 스레드는 유저 접속 대기
			while (true) {
				socket = serverSocket.accept();
				System.out.println(TAG + "플레이어 접속");
				// 유저 스레드 생성
				PlayerThread pt = new PlayerThread(socket);
				vc.add(pt);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// 유저 입장 시 생성되는 스레드
	class PlayerThread {
		private Socket socket;
		private ChatThread ct;
		private GameThread gt;
		
		public PlayerThread(Socket socket) {
			this.socket = socket;
		}
		
		void start() {
			ct = new ChatThread(socket);
			ct.start();
			gt = new GameThread(socket);
			gt.start();
		}
	}
	
	// 유저 채팅을 위한 스레드
	class ChatThread extends Thread {
		private BufferedReader reader;
		private PrintWriter writer;
		private String id;
		private ChatDto chatDto;
		
		public ChatThread(Socket socket) {
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new PrintWriter(socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			String text = null;
			Gson gson = new Gson();
			chatDto = new ChatDto();
			try {
				while ((text = reader.readLine()) != null) {
					chatDto = gson.fromJson(text, ChatDto.class);
					
					String tempText = "["+chatDto.getId()+"] " + chatDto.getText();
					// user connect 시 최초 호출(서버에 id값 입력을 위함)
					if (chatDto.getGubun().equals("IdSettingCode")) {
						this.id = chatDto.getId();
					}
					if (!(tempText.contains("IdSettingCode"))) {
						for (int i = 0; i < vc.size(); i++) {
							vc.get(i).ct.writer.println(tempText);
						}
					}
				}
			} catch (IOException e) {
				System.out.println(TAG + "연결 종료");
			}
		}
	}

	// 게임 정보 전송을 위한 스레드
	class GameThread extends Thread {
		private BufferedReader reader;
		private PrintWriter writer;
		private String id;
		private GameDto gameDto;
		private int dice1;
		private int dice2;
		
		public GameThread(Socket socket) {
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new PrintWriter(socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			String text = null;
			Gson gson = new Gson();
			gameDto = new GameDto();
			try {
				while ((text = reader.readLine()) != null) {
					gameDto = gson.fromJson(text, GameDto.class);
					
					dice1 = gameDto.getDice1();
					dice2 = gameDto.getDice2();
					
					
				}
			} catch (IOException e) {
				System.out.println(TAG + "연결 종료");
			}
		}
	}
	
}
