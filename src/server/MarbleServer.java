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
	
	String nowPlayer ; // ���� ������ �÷��̾�
	int countTurn ; // ���� ���� �� ��
	int dice1 ; // �ֻ��� ���� �����ֱ� ���� ��
	int dice2 ; // �ֻ��� ���� �����ֱ� ���� ��
	int countPlayer ; // ���� ���� �÷��̾� ��
	Vector<Player> playerList ; // �÷��̾� ��ü�� ��� ����

	void initSequence() {} // ���� �� ���� ���ϱ�
	void sequenceFlow() {} // �� �ѱ��(���� �� �÷��̾��� isTurn�� true�� ����

	
	public MarbleServer() {
		// ������ ������ Vector�� ���
		vc = new Vector<>();
		
		try {
			serverSocket = new ServerSocket(Protocol.PORT);
			System.out.println(TAG + "�÷��̾� ���� �����....");
			// main ������� ���� ���� ���
			while (true) {
				socket = serverSocket.accept();
				System.out.println(TAG + "�÷��̾� ����");
				// ���� ������ ����
				PlayerThread pt = new PlayerThread(socket);
				vc.add(pt);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// ���� ���� �� �����Ǵ� ������
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
	
	// ���� ä���� ���� ������
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
					// user connect �� ���� ȣ��(������ id�� �Է��� ����)
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
				System.out.println(TAG + "���� ����");
			}
		}
	}

	// ���� ���� ������ ���� ������
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
				System.out.println(TAG + "���� ����");
			}
		}
	}
	
}
