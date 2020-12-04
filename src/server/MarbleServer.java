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
		
		public PlayerThread(Socket socket) {
			this.socket = socket;
		}
		
		void start() {
			ChatThread ct = new ChatThread(socket);
			ct.start();
		}
	}
	
	// 유저 채팅을 위한 스레드
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
