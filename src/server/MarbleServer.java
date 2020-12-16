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
	private Vector<PlayerThread> playerList; // �÷��̾� ��ü�� ��� ����
	private Vector<Tile> tileList; // Ÿ�� ��ü�� ��� ����
	private String player1 = "";
	private String player2 = "";
	private String player3 = "";
	private String player4 = ""; // �÷��̾� ID�� ��� ����

	private String nowPlayer; // ���� ������ �÷��̾�
	private int countTurn; // ���� ���� �� ��
	private int dice1; // �ֻ��� ���� �����ֱ� ���� ��
	private int dice2; // �ֻ��� ���� �����ֱ� ���� ��
	private int countPlayer; // ���� ���� �÷��̾� ��
	private boolean isPlaying = false;
	private int[] arrayinit = { 0, 0, 0, 0 };

	void initSequence() {
	} // ���� �� ���� ���ϱ�

	void sequenceFlow() {
	} // �� �ѱ��(���� �� �÷��̾��� isTurn�� true�� ����

	public MarbleServer() {

		initSetting();

		try {
			serverSocket = new ServerSocket(Protocol.PORT);
			System.out.println(TAG + "�÷��̾� ���� �����....");
			// main ������� ���� ���� ���
			while (true) {
				socket = serverSocket.accept();
				System.out.println(TAG + "�÷��̾� ����");
				// ���� ������ ����
				PlayerThread pt = new PlayerThread(socket);
				Thread newPlayer = new Thread(pt);
				playerList.add(pt);
				newPlayer.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ���� ���� �� �����Ǵ� ������
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
				// 2�� �̻� �����ϸ� ù��° �÷��̾�� ���۹�ư Ȱ��ȭ
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
				System.out.println(TAG + id + "���� ����");
				// ���� ������ �÷��̾ ����Ʈ���� ����
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
			// ID ���� + Ŭ���̾�Ʈ �� �÷��̾� ��ü�� ID�� �ֱ�
			if (dto.getType().equals(Protocol.IDSET)) {
				// �̹� �����ϴ� ID�� ID�� �����ϰ� ��.
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
//            tempDto.setText("[����] " + dto.getId() + "���� �����ϼ̽��ϴ�.\n");
//            for (int i = 0; i < playerList.size(); i++) {
//               System.out.println(playerList.get(i).writer);
//               playerList.get(i).writer.println(gson.toJson(tempDto));
//            }
//         }

			// 4�� �̻� �̹� �÷������̸� ���̻� ���ο� �÷��̾ ������ �� ���� ��.
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

				String notice = "[����] ������ �����մϴ�.\n";
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
				tempDto.setText("[����] " + dto.getId() + "���� ���� �����մϴ�.\n");
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
				tempDto.setText("[����] " + tempId + "���� ���Դϴ�.\n");
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
				System.out.println(TAG + "DICEROLL ����");
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
					System.out.println(TAG + "MOVE �޾Ƽ� ����");
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
			// Ŭ���̾�Ʈ >> ������ ���� �� ������Ʈ
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
			// ���� ���̾�α� ���� �޾Ƽ� ��� Ŭ���̾�Ʈ���� ������.
			if (dto.getType().equals(Protocol.PLAYERPURCHASED)) {
				tempDto.setType(Protocol.PLAYERPURCHASED);
				tempDto.setId(dto.getId());
				tempDto.setNewprice(dto.getNewprice());
				for (int i = 0; i < playerList.size(); i++) {
					playerList.get(i).writer.println(gson.toJson(tempDto));
				}
			}

			// ���� ���� �ǹ� ���� �޾Ƽ� ��� Ŭ���̾�Ʈ���� ������.
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

			// ���� ���̾�α� ���� �޾Ƽ� ��� Ŭ���̾�Ʈ���� ������.
			if (dto.getType().equals(Protocol.PLAYERFINE)) {
				tempDto.setType(Protocol.PLAYERFINE);
				tempDto.setId(dto.getId());
				tempDto.setTileFine(dto.getTileFine());
				tempDto.setTileOwnerId(dto.getTileOwnerId());
				for (int i = 0; i < playerList.size(); i++) {
					playerList.get(i).writer.println(gson.toJson(tempDto));
				}
			}

			// ä�� ���
			if (dto.getType().equals(Protocol.CHAT)) {
				String chatText = dto.getId() + " : " + dto.getText() + "\n";
				tempDto.setType(Protocol.CHAT);
				if (dto.getText().equals("")) {
					chatText = "[����] ������ �Է����ּ���.\n";
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
			// Ȳ�ݿ��� ĭ�� ���� ��
			if (dto.getType().equals(Protocol.PLAYERSPECIAL)) {
				tempDto.setType(Protocol.PLAYERSPECIAL);
				tempDto.setId(dto.getId());
				tempDto.setSpecialType(dto.getSpecialType());
				tempDto.setTileFine(dto.getTileFine());
				tempDto.setNewPlayerTile(dto.getNewPlayerTile());
				for (int i = 0; i < playerList.size(); i++) {
					playerList.get(i).writer.println(gson.toJson(tempDto));
				}
				// �̵��� �ؾߵǴ� �̺�Ʈ�� ���
				if (dto.getSpecialType() == 2 || dto.getSpecialType() == 3) {
					tempDto.setGubun(Protocol.GAME);
					tempDto.setType(Protocol.MOVE);
					tempDto.setId(dto.getId());
					tempDto.setNewPlayerTile(dto.getNewPlayerTile());
					output = gson.toJson(tempDto);
					for (int i = 0; i < playerList.size(); i++) {
						playerList.get(i).writer.println(output);
						System.out.println(TAG + "MOVE �޾Ƽ� ����");
					}
				}
			} // PLAYERSPECIAL END
		} // end of router
	} // end of thread

	private void initSetting() {
		playerList = new Vector<>(); // ������ ������ Vector�� ���
		tileList = new Vector<>();
		Tile T0 = new SpecialTile("����", 0, 0, 650, 650);
		Tile T1 = new CityTile("ȫ��", 1, 1, 550, 650, "", 0, arrayinit, 20, 24, 30, 36, 0);
		Tile T2 = new SpecialTile("�����", 2, 3, 450, 650);
		Tile T3 = new CityTile("����", 3, 1, 350, 650, "", 0, arrayinit, 24, 28, 34, 40, 0);
		Tile T4 = new IsLandTile("���ֵ�", 4, 2, 250, 650, "", 0, arrayinit, 45);
		Tile T5 = new CityTile("ī�̷�", 5, 1, 150, 650, "", 0, arrayinit, 27, 35, 41, 48, 0);
		Tile T6 = new SpecialTile("���ε�", 6, 3, 0, 650);
		Tile T7 = new IsLandTile("�Ͽ���", 7, 2, 0, 550, "", 0, arrayinit, 65);
		Tile T8 = new CityTile("�õ��", 8, 1, 0, 450, "", 0, arrayinit, 30, 38, 45, 52, 0);
		Tile T9 = new CityTile("���Ŀ��", 9, 1, 0, 350, "", 0, arrayinit, 32, 40, 47, 55, 0);
		Tile T10 = new SpecialTile("�����", 10, 3, 0, 250);
		Tile T11 = new CityTile("����", 11, 1, 0, 150, "", 0, arrayinit, 35, 43, 51, 59, 0);
		Tile T12 = new SpecialTile("�ø���", 12, 3, 0, 0);
		Tile T13 = new CityTile("��ũ��", 13, 1, 150, 0, "", 0, arrayinit, 37, 46, 54, 63, 0);
		Tile T14 = new CityTile("������", 14, 1, 250, 0, "", 0, arrayinit, 40, 50, 59, 68, 0);
		Tile T15 = new IsLandTile("����", 15, 2, 350, 0, "", 0, arrayinit, 80);
		Tile T16 = new SpecialTile("�����", 16, 3, 450, 0);
		Tile T17 = new CityTile("�θ�", 17, 1, 550, 0, "", 0, arrayinit, 43, 54, 65, 74, 0);
		Tile T18 = new SpecialTile("���迩��", 18, 3, 650, 0);
		Tile T19 = new SpecialTile("�����", 19, 3, 650, 150);
		Tile T20 = new CityTile("����", 20, 1, 650, 250, "", 0, arrayinit, 45, 58, 70, 79, 0);
		Tile T21 = new CityTile("�ĸ�", 21, 1, 650, 350, "", 0, arrayinit, 47, 62, 74, 84, 0);
		Tile T22 = new CityTile("����", 22, 1, 650, 450, "", 0, arrayinit, 50, 65, 79, 89, 0);
		Tile T23 = new IsLandTile("����", 23, 2, 650, 550, "", 0, arrayinit, 100);

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