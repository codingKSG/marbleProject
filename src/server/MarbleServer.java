package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import com.google.gson.Gson;

import protocol.Protocol;

public class MarbleServer {
	
	private MarbleServer marbleServer = this;
	private static final String TAG = "MarbleServer : ";
	private ServerSocket serverSocket;
	private Socket socket;
	private Vector<PlayerThread> vc;
	
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
		
		public PlayerThread(Socket socket) {
			this.socket = socket;
		}
		
		void start() {
			ChatThread ct = new ChatThread(socket);
			ct.start();
		}
	}
	
	// ���� ä���� ���� ������
	class ChatThread extends Thread {
		private Socket socket;
		private BufferedReader reader;
		private PrintWriter writer;
		private String id;
		
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
		}
	}
	
	
	public static void main(String[] args) {
		new MarbleServer();
	}
}
