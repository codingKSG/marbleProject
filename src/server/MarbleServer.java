package server;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import com.google.gson.Gson;

import object.CityTile;
import object.IsLandTile;
import object.SpecialTile;
import object.Tile;
import protocol.Protocol;
import protocol.RequestDto;

public class MarbleServer {
	
	private MarbleServer marbleServer = this;
	private static final String TAG = "MarbleServer : ";
	
	private ServerSocket serverSocket;
	private Socket socket;
	private Vector<PlayerThread> playerList; // 플레이어 객체가 담긴 벡터
	private String player1 = "";
	private String player2 = "";
	private String player3 = "";
	private String player4 = ""; // 플레이어 ID가 담긴 변수
	

	private String nowPlayer; // 현재 차례인 플레이어
	private int countTurn; // 현재 진행 턴 수
	private int dice1; // 주사위 값을 보여주기 위한 것
	private int dice2; // 주사위 값을 보여주기 위한 것
	private int countPlayer; // 현재 생존 플레이어 수
	private boolean isPlaying = false;
	private int[] arrayinit = {0,0,0,0};

	void initSequence() {} // 시작 전 순서 정하기
	void sequenceFlow() {} // 턴 넘기기(다음 턴 플레이어의 isTurn을 true로 변경
	
	
	public MarbleServer() {
		// 입장한 유저가 Vector에 담김
		playerList = new Vector<>();
		Tile T0 = new SpecialTile("시작", 0, 0, 240, 240);
		CityTile T1 = new CityTile("홍콩", 1, 1, 132, 240, null, 0, arrayinit, 20, 30, 50, 70, 0);
		CityTile T2 = new CityTile("싱가폴", 2, 1, 26, 240, null, 0, arrayinit, 30, 50, 70, 80, 0);
		IsLandTile T3 = new IsLandTile("제주도", 3, 2, 26, 131, null, 0, arrayinit, 50);
		SpecialTile T4 = new SpecialTile("무인도", 4, 3, 26, 26);
		IsLandTile T5 = new IsLandTile("독도", 5, 2, 132, 26, null, 0, arrayinit, 70);
		CityTile T6 = new CityTile("뉴욕", 6, 1, 240, 26, null, 0, arrayinit, 50, 60, 80, 100, 0);
		SpecialTile T7 = new SpecialTile("올림픽", 7, 3, 240, 131);
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
				playerList.add(pt);
				newPlayer.start();
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
				
				if (playerList.size() == 2) {
					RequestDto hostDto = new RequestDto();
					hostDto.setType(Protocol.GAMEHOST);
					playerList.get(0).writer.println(gson.toJson(hostDto));
					System.out.println(TAG + "GAMEHOST지정");
				}
				
				while ((text = reader.readLine()) != null) {
					dto = gson.fromJson(text, RequestDto.class);
					System.out.println(TAG + id + " : " + dto);
					router(dto);
				}
			} catch (IOException e) {
				System.out.println(TAG + id + "연결 종료");
				// 연결 종료한 플레이어를 리스트에서 삭제
				for (int i = 0; i < playerList.size(); i++) {
					if (playerList.get(i).id.equals(id)) {
						playerList.remove(i);
					}
				}
			}
		}
		
		private void router(RequestDto dto) {
			Gson gson = new Gson();
			String output = "";
			RequestDto tempDto = new RequestDto();
			// ID 설정 + 클라이언트 내 플레이어 객체에 ID값 넣기
			if (dto.getType().equals(Protocol.IDSET)) {
				playerThread.id = dto.getId();
				
//			이미 존재하는 ID면 ID를 변경하게 함.
//				if (playerList.size() != 0) {
//					for (int i = 0; i < playerList.size(); i++) {
//						if (playerList.get(playerList.size()-1).id.equals(dto.getId())) {
//							tempDto.setType(Protocol.IDCHECK);
//							playerList.get(i).writer.println(gson.toJson(tempDto));
//						}
//					}
//				}
			}
			
			// 4명 이상 이미 플레이중이면 더이상 새로운 플레이어가 참가할 수 없게 함.
			if (dto.getType().equals(Protocol.PLAYERNUMCHECK) && (playerList.size() != 0)) {
				if ((isPlaying == true) || playerList.size() > 4) {
					tempDto.setType(Protocol.PLAYERNUMCHECK);
					tempDto.setPlayerNum(4);
					for (int i = 0; i < playerList.size(); i++) {
						if (playerList.get(i).id.equals(dto.getId())) {
							playerList.get(i).writer.println(gson.toJson(tempDto));
						}
					}
				}
			}
			
			if (dto.getType().equals(Protocol.GAMESTART)) {
				isPlaying = true;
				tempDto.setType(Protocol.PLAYERSET);
				if (playerList.size() == 4) {
					tempDto.setPlayer1(playerList.get(0).id);
					tempDto.setPlayer2(playerList.get(1).id);
					tempDto.setPlayer3(playerList.get(2).id);
					tempDto.setPlayer4(playerList.get(3).id);
				} else if (playerList.size() == 3) {
					tempDto.setPlayer1(playerList.get(0).id);
					tempDto.setPlayer2(playerList.get(1).id);
					tempDto.setPlayer3(playerList.get(2).id);
				} else if (playerList.size() == 2) {
					tempDto.setPlayer1(playerList.get(0).id);
					tempDto.setPlayer2(playerList.get(1).id);
				} else if (playerList.size() == 1) {
					tempDto.setPlayer1(playerList.get(0).id);
				}
				
				for (int i = 0; i < playerList.size(); i++) {
					playerList.get(i).writer.println(gson.toJson(tempDto));
				}
				
				String notice = "[공지] 게임을 시작합니다.\n";
				tempDto.setType(Protocol.CHAT);
				tempDto.setText(notice);
				for (int i = 0; i < playerList.size(); i++) {
					playerList.get(i).writer.println(gson.toJson(tempDto));
				}
			}
			
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
				tempDto.setNewPlayerX(dto.getNewPlayerX());
				tempDto.setNewPlayerY(dto.getNewPlayerY());
				tempDto.setNewPlayerTile(dto.getNewPlayerTile());
				output = gson.toJson(tempDto);
				for (int i = 0; i < playerList.size(); i++) {
					playerList.get(i).writer.println(output);
					System.out.println(TAG + "MOVE 받아서 보냄");
				}
			}
			
			if (dto.getType().equals(Protocol.CHAT)) {
				String chatText = dto.getId() + " : " + dto.getText() + "\n";
				tempDto.setType(Protocol.CHAT);
				if (dto.getText().equals("")) {
					chatText = "[공지] 내용을 입력해주세요.\n";
					tempDto.setText(chatText);
					for (int i = 0; i < playerList.size(); i++) {
						if (playerList.get(i).id.equals(dto.getId())) {
							playerList.get(i).writer.println(gson.toJson(tempDto));
						}
					}
				} else {
					tempDto.setText(chatText);
					for (int i = 0; i < playerList.size(); i++) {
						playerList.get(i).writer.println(gson.toJson(tempDto));
					}
				}
			}
		} // end of router
		
	} // end of thread
}
