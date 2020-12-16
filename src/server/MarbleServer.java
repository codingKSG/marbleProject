package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
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
	private Vector<Tile> tileList; // 타일 객체가 담긴 벡터
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
	private int[] arrayinit = { 0, 0, 0, 0 };

	void initSequence() {
	} // 시작 전 순서 정하기

	void sequenceFlow() {
	} // 턴 넘기기(다음 턴 플레이어의 isTurn을 true로 변경

	public MarbleServer() {

		initSetting();

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
				// 2명 이상 접속하면 첫번째 플레이어에게 시작버튼 활성화
				if (playerList.size() == 2) {
					RequestDto hostDto = new RequestDto();
					hostDto.setType(Protocol.GAMEHOST);
					playerList.get(0).writer.println(gson.toJson(hostDto));
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
				// 이미 존재하는 ID면 ID를 변경하게 함.
				if (playerList.size() > 1) {
					for (int i = 0; i < playerList.size() - 1; i++) {
						if (playerList.get(i).id.equals(dto.getId())) {
							tempDto.setType(Protocol.IDCHECK);
							playerList.get(playerList.size() - 1).writer.println(gson.toJson(tempDto));
							playerList.remove(playerList.size() - 1);
						} else
							playerThread.id = dto.getId();
					}
				} else
					playerThread.id = dto.getId();
			}

//         if (dto.getType().equals(Protocol.PLAYERNUMCHECK)) {
//            tempDto.setGubun(Protocol.CHAT);
//            tempDto.setType(Protocol.CHAT);
//            tempDto.setId(dto.getId());
//            tempDto.setText("[공지] " + dto.getId() + "님이 입장하셨습니다.\n");
//            for (int i = 0; i < playerList.size(); i++) {
//               System.out.println(playerList.get(i).writer);
//               playerList.get(i).writer.println(gson.toJson(tempDto));
//            }
//         }

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

				tempDto.setType(Protocol.TURN);
				tempDto.setTurnId(playerList.get(0).id);

				for (int i = 0; i < playerList.size(); i++) {
					playerList.get(i).writer.println(gson.toJson(tempDto));
				}
			}

			if (dto.getType().equals(Protocol.TILELISTPULL)) {
				tempDto.setType(Protocol.TILELISTPULL);
				tempDto.setTileList(tileList);
				for (int i = 0; i < playerList.size(); i++) {
					playerList.get(i).writer.println(gson.toJson(tempDto));
				}
			}

			if (dto.getType().equals(Protocol.ENDTURN)) {
				tempDto.setGubun(Protocol.CHAT);
				tempDto.setType(Protocol.CHAT);
				tempDto.setText("[공지] " + dto.getId() + "님이 턴을 종료합니다.\n");
				for (int i = 0; i < playerList.size(); i++) {
					playerList.get(i).writer.println(gson.toJson(tempDto));
				}
			}

			if (dto.getType().equals(Protocol.NEXTTURN)) {
				String tempId = "";
				int tempIndex = 5;
				tempDto.setType(Protocol.NEXTTURN);
				for (int i = 0; i < playerList.size(); i++) {
					if (playerList.get(playerList.size() - 1).id.equals(dto.getId())) {
						tempId = playerList.get(0).id;
						tempIndex = 0;
						break;
					}
					if (playerList.get(i).id.equals(dto.getId())) {
						tempId = playerList.get(i + 1).id;
						tempIndex = i + 1;
						break;
					}
				}
				tempDto.setTurnId(tempId);
				playerList.get(tempIndex).writer.println(gson.toJson(tempDto));

				tempDto.setGubun(Protocol.CHAT);
				tempDto.setType(Protocol.CHAT);
				tempDto.setText("[공지] " + tempId + "님의 턴입니다.\n");
				for (int i = 0; i < playerList.size(); i++) {
					playerList.get(i).writer.println(gson.toJson(tempDto));
				}

			}

			if (dto.getType().equals(Protocol.DICEROLL)) {
				tempDto.setGubun(Protocol.GAME);
				tempDto.setType(Protocol.DICEROLL);
				tempDto.setId(dto.getId());
				tempDto.setDice1(1);
				tempDto.setDice2(1);
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
				tempDto.setDice1(dto.getDice1());
				tempDto.setDice2(dto.getDice2());
				tempDto.setNewPlayerTile(dto.getNewPlayerTile());
				output = gson.toJson(tempDto);
				for (int i = 0; i < playerList.size(); i++) {
					playerList.get(i).writer.println(output);
					System.out.println(TAG + "MOVE 받아서 보냄");
				}
			}

			if (dto.getType().equals(Protocol.DIALOGREQUEST)) {
				Tile tempTile = null;
				tempDto.setId(dto.getId());
				tempDto.setType(Protocol.DIALOGREQUEST);
				for (int i = 0; i < tileList.size(); i++) {
					if (tileList.get(i).getTileNum() == dto.getNowPlayerTile()) {
						tempTile = tileList.get(i);
						tempDto.setTileInfo(tempTile);
						tempDto.setTileType(tempTile.getTileType());
						break;
					}
				}
				for (int i = 0; i < playerList.size(); i++) {
					if (playerList.get(i).id.equals(dto.getId())) {
						playerList.get(i).writer.println(gson.toJson(tempDto));
					}
				}
			}
			// 클라이언트 >> 서버로 받은 값 업데이트
			if (dto.getType().equals(Protocol.DIALOGUPDATE)) {
				Tile tempTile = null;
				for (int i = 0; i < tileList.size(); i++) {
					if (tileList.get(i).getTileNum() == dto.getTileInfo().getTileNum()) {
						tempTile = dto.getTileInfo();
						tileList.set(i, tempTile);
						break;
					}
				}
			}
			// 구매 다이얼로그 정보 받아서 모든 클라이언트에게 돌려줌.
			if (dto.getType().equals(Protocol.PLAYERPURCHASED)) {
				tempDto.setType(Protocol.PLAYERPURCHASED);
				tempDto.setId(dto.getId());
				tempDto.setNewprice(dto.getNewprice());
				for (int i = 0; i < playerList.size(); i++) {
					playerList.get(i).writer.println(gson.toJson(tempDto));
				}
			}

			// 새로 지을 건물 정보 받아서 모든 클라이언트에게 돌려줌.
			if (dto.getType().equals(Protocol.PLAYERBUILD)) {
				tempDto.setType(Protocol.PLAYERBUILD);
				tempDto.setTileOwnerId(dto.getTileOwnerId());
				tempDto.setNowPlayerTile(dto.getNowPlayerTile());
				tempDto.setNewBuild(dto.getNewBuild());
				tempDto.setBuildX(tileList.get(dto.getNowPlayerTile()).getTileX());
				tempDto.setBuildY(tileList.get(dto.getNowPlayerTile()).getTileY());
				for (int i = 0; i < playerList.size(); i++) {
					playerList.get(i).writer.println(gson.toJson(tempDto));
				}
			}

			if (dto.getType().equals(Protocol.PLAYERISLAND)) {
				tempDto.setType(Protocol.PLAYERISLAND);
				tempDto.setTileOwnerId(dto.getTileOwnerId());
				tempDto.setNowPlayerTile(dto.getNowPlayerTile());
				for (int i = 0; i < playerList.size(); i++) {
					playerList.get(i).writer.println(gson.toJson(tempDto));
				}
			}

			// 벌금 다이얼로그 정보 받아서 모든 클라이언트에게 돌려줌.
			if (dto.getType().equals(Protocol.PLAYERFINE)) {
				tempDto.setType(Protocol.PLAYERFINE);
				tempDto.setId(dto.getId());
				tempDto.setTileFine(dto.getTileFine());
				tempDto.setTileOwnerId(dto.getTileOwnerId());
				for (int i = 0; i < playerList.size(); i++) {
					playerList.get(i).writer.println(gson.toJson(tempDto));
				}
			}

			// 채팅 기능
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
			// 황금열쇄 칸에 도착 시
			if (dto.getType().equals(Protocol.PLAYERSPECIAL)) {
				tempDto.setType(Protocol.PLAYERSPECIAL);
				tempDto.setId(dto.getId());
				tempDto.setSpecialType(dto.getSpecialType());
				tempDto.setTileFine(dto.getTileFine());
				tempDto.setNewPlayerTile(dto.getNewPlayerTile());
				for (int i = 0; i < playerList.size(); i++) {
					playerList.get(i).writer.println(gson.toJson(tempDto));
				}
				// 이동을 해야되는 이벤트일 경우
				if (dto.getSpecialType() == 2 || dto.getSpecialType() == 3) {
					tempDto.setGubun(Protocol.GAME);
					tempDto.setType(Protocol.MOVE);
					tempDto.setId(dto.getId());
					tempDto.setNewPlayerTile(dto.getNewPlayerTile());
					output = gson.toJson(tempDto);
					for (int i = 0; i < playerList.size(); i++) {
						playerList.get(i).writer.println(output);
						System.out.println(TAG + "MOVE 받아서 보냄");
					}
				}
			} // PLAYERSPECIAL END
		} // end of router
	} // end of thread

	private void initSetting() {
		playerList = new Vector<>(); // 입장한 유저가 Vector에 담김
		tileList = new Vector<>();
		Tile T0 = new SpecialTile("시작", 0, 0, 650, 650);
		Tile T1 = new CityTile("홍콩", 1, 1, 550, 650, "", 0, arrayinit, 20, 24, 30, 36, 0);
		Tile T2 = new SpecialTile("스페셜", 2, 3, 450, 650);
		Tile T3 = new CityTile("도쿄", 3, 1, 350, 650, "", 0, arrayinit, 24, 28, 34, 40, 0);
		Tile T4 = new IsLandTile("제주도", 4, 2, 250, 650, "", 0, arrayinit, 45);
		Tile T5 = new CityTile("카이로", 5, 1, 150, 650, "", 0, arrayinit, 27, 35, 41, 48, 0);
		Tile T6 = new SpecialTile("무인도", 6, 3, 0, 650);
		Tile T7 = new IsLandTile("하와이", 7, 2, 0, 550, "", 0, arrayinit, 65);
		Tile T8 = new CityTile("시드니", 8, 1, 0, 450, "", 0, arrayinit, 30, 38, 45, 52, 0);
		Tile T9 = new CityTile("상파울로", 9, 1, 0, 350, "", 0, arrayinit, 32, 40, 47, 55, 0);
		Tile T10 = new SpecialTile("스페셜", 10, 3, 0, 250);
		Tile T11 = new CityTile("퀘벡", 11, 1, 0, 150, "", 0, arrayinit, 35, 43, 51, 59, 0);
		Tile T12 = new SpecialTile("올림픽", 12, 3, 0, 0);
		Tile T13 = new CityTile("모스크바", 13, 1, 150, 0, "", 0, arrayinit, 37, 46, 54, 63, 0);
		Tile T14 = new CityTile("베를린", 14, 1, 250, 0, "", 0, arrayinit, 40, 50, 59, 68, 0);
		Tile T15 = new IsLandTile("독도", 15, 2, 350, 0, "", 0, arrayinit, 80);
		Tile T16 = new SpecialTile("스페셜", 16, 3, 450, 0);
		Tile T17 = new CityTile("로마", 17, 1, 550, 0, "", 0, arrayinit, 43, 54, 65, 74, 0);
		Tile T18 = new SpecialTile("세계여행", 18, 3, 650, 0);
		Tile T19 = new SpecialTile("스페셜", 19, 3, 650, 150);
		Tile T20 = new CityTile("런던", 20, 1, 650, 250, "", 0, arrayinit, 45, 58, 70, 79, 0);
		Tile T21 = new CityTile("파리", 21, 1, 650, 350, "", 0, arrayinit, 47, 62, 74, 84, 0);
		Tile T22 = new CityTile("뉴옥", 22, 1, 650, 450, "", 0, arrayinit, 50, 65, 79, 89, 0);
		Tile T23 = new IsLandTile("서울", 23, 2, 650, 550, "", 0, arrayinit, 100);

		tileList.add(T0);
		tileList.add(T1);
		tileList.add(T2);
		tileList.add(T3);
		tileList.add(T4);
		tileList.add(T5);
		tileList.add(T6);
		tileList.add(T7);
		tileList.add(T8);
		tileList.add(T9);
		tileList.add(T10);
		tileList.add(T11);
		tileList.add(T12);
		tileList.add(T13);
		tileList.add(T14);
		tileList.add(T15);
		tileList.add(T16);
		tileList.add(T17);
		tileList.add(T18);
		tileList.add(T19);
		tileList.add(T20);
		tileList.add(T21);
		tileList.add(T22);
		tileList.add(T23);

	}

}