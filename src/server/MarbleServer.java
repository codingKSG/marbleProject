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
import protocol.Protocol;
import protocol.RequestDto;

public class MarbleServer {
	
	private MarbleServer marbleServer = this;
	private static final String TAG = "MarbleServer : ";
	
	private ServerSocket serverSocket;
	private Socket socket;
	private Vector<PlayerThread> playerList; // 플레이어 객체가 담긴 벡터
	
	private String nowPlayer ; // 현재 차례인 플레이어
	private int countTurn ; // 현재 진행 턴 수
	private int dice1 ; // 주사위 값을 보여주기 위한 것
	private int dice2 ; // 주사위 값을 보여주기 위한 것
	private int countPlayer ; // 현재 생존 플레이어 수

	void initSequence() {} // 시작 전 순서 정하기
	void sequenceFlow() {} // 턴 넘기기(다음 턴 플레이어의 isTurn을 true로 변경
	
	public MarbleServer() {
		// 입장한 유저가 Vector에 담김
		playerList = new Vector<>();
		
		try {
			serverSocket = new ServerSocket(Protocol.PORT);
			System.out.println(TAG + "플레이어 접속 대기중....");
			// main 스레드는 유저 접속 대기
			while (true) {
				socket = serverSocket.accept();
				System.out.println(TAG + "플레이어 접속");
				// 유저 스레드 생성
				PlayerThread pt = new PlayerThread(socket);
				Thread newPlayer = new Thread(pt);
				newPlayer.start();
				playerList.add(pt);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// 유저 입장 시 생성되는 스레드
	class PlayerThread implements Runnable {
		
		PlayerThread playerThread = this;
		private Socket socket;
		private BufferedReader reader;
		private PrintWriter writer;
		private String id;
		
		public PlayerThread(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new PrintWriter(socket.getOutputStream(), true);
				
				String text = "";
				RequestDto dto = new RequestDto();
				Gson gson = new Gson();
				while ((text = reader.readLine()) != null) {
					dto = gson.fromJson(text, RequestDto.class);
					System.out.println(TAG + "클라이언트 : " + dto);
					router(dto);
				}
			} catch (IOException e) {
				System.out.println(TAG + "연결 종료");
			}
		}
		
		private void router(RequestDto dto) {
			Gson gson = new Gson();
			String output = "";
			RequestDto tempDto = new RequestDto();
			
			if (dto.getType().equals(Protocol.DICEROLL)) {
				tempDto.setGubun(Protocol.GAME);
				tempDto.setType(Protocol.DICEROLL);
				tempDto.setId(dto.getId());
				tempDto.setDice1(dto.getDice1());
				tempDto.setDice2(dto.getDice2());
				output = gson.toJson(tempDto);
				System.out.println(TAG + "DICEROLL 받음");
				for (int i = 0; i < playerList.size(); i++) {
					playerList.get(i).writer.println(output);
				}
			}
			
			if (dto.getType().equals(Protocol.MOVE)) {
				tempDto.setGubun(Protocol.GAME);
				tempDto.setType(Protocol.MOVE);
				tempDto.setId(dto.getId());
				tempDto.setNewX(dto.getNewX());
				tempDto.setNewY(dto.getNewY());
				output = gson.toJson(tempDto);
				for (int i = 0; i < playerList.size(); i++) {
					if (playerList.get(i).id.equals(tempDto.getId())) {
						playerList.get(i).writer.println(output);
					}
				}
			}
		}
		
	}
	
}
