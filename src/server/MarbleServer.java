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
import protocol.RequestDto;

public class MarbleServer {
	
	private MarbleServer marbleServer = this;
	private static final String TAG = "MarbleServer : ";
	
	private ServerSocket serverSocket;
	private Socket socket;
	private Vector<PlayerThread> playerList; // �÷��̾� ��ü�� ��� ����
	private String player1 = "";
	private String player2 = "";
	private String player3 = "";
	private String player4 = ""; // �÷��̾� ID�� ��� ����
	
	private String nowPlayer ; // ���� ������ �÷��̾�
	private int countTurn ; // ���� ���� �� ��
	private int dice1 ; // �ֻ��� ���� �����ֱ� ���� ��
	private int dice2 ; // �ֻ��� ���� �����ֱ� ���� ��
	private int countPlayer ; // ���� ���� �÷��̾� ��

	void initSequence() {} // ���� �� ���� ���ϱ�
	void sequenceFlow() {} // �� �ѱ��(���� �� �÷��̾��� isTurn�� true�� ����
	
	public MarbleServer() {
		// ������ ������ Vector�� ���
		playerList = new Vector<>();
		
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
				newPlayer.start();
				playerList.add(pt);
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
				while ((text = reader.readLine()) != null) {
					dto = gson.fromJson(text, RequestDto.class);
					System.out.println(TAG + id + " : " + dto);
					router(dto);
				}
			} catch (IOException e) {
				System.out.println(TAG + "���� ����");
			}
		}
		
		private void router(RequestDto dto) {
			Gson gson = new Gson();
			String output = "";
			RequestDto tempDto = new RequestDto();
			// ID ���� + Ŭ���̾�Ʈ �� �÷��̾� ��ü�� ID�� �ֱ�
			if (dto.getType().equals(Protocol.IDSET)) {
				playerThread.id = dto.getId();
				
				
//			�̹� �����ϴ� ID�� ID�� �����ϰ� ��.
//				if (playerList.size() != 0) {
//					for (int i = 0; i < playerList.size(); i++) {
//						if (playerList.get(playerList.size()-1).id.equals(dto.getId())) {
//							tempDto.setType(Protocol.IDCHECK);
//							playerList.get(i).writer.println(gson.toJson(tempDto));
//						}
//					}
//				}
			}
			
//			if (dto.getType().equals(Protocol.PLAYERSET)) {
//				tempDto.setType(Protocol.PLAYERSET);
//				if ("".equals(player1)) {
//					player1 = dto.getId();
//					System.out.println("PLAYERSET Player1 : " + player1);
//					tempDto.setPlayer1(player1);
//				} else if ("".equals(player2)) {
//					player2 = dto.getId();
//					tempDto.setPlayer2(player2);
//				} else if ("".equals(player3)) {
//					player3 = dto.getId();
//					tempDto.setPlayer3(player3);
//				} else if ("".equals(player4)) {
//					player4 = dto.getId();
//					tempDto.setPlayer4(player4);
//				}
//				for (int i = 0; i < playerList.size(); i++) {
//					playerList.get(i).writer.println(gson.toJson(tempDto));
//				}
//			}
			
			// 4�� �̻� �̹� �÷������̸� ���̻� ���ο� �÷��̾ ������ �� ���� ��.
			if (dto.getType().equals(Protocol.PLAYERNUMCHECK) && (playerList.size() != 0)) {
				if (playerList.size() > 4) {
					tempDto.setType(Protocol.PLAYERNUMCHECK);
					tempDto.setPlayerNum(4);
					for (int i = 0; i < playerList.size(); i++) {
						if (playerList.get(i).id.equals(dto.getId())) {
							playerList.get(i).writer.println(gson.toJson(tempDto));
						}
					}
				}
			}
			
			// ĳ���� ���� ��û �޾Ƽ� ó��
			if (dto.getType().equals(Protocol.MAKEPLAYER)) {
				int makePlayerNum = 10;
				for (int i = 0; i < playerList.size(); i++) {
					if (playerList.get(i).id.equals(dto.getId())) {
						makePlayerNum = i;
					}
				}
				tempDto.setGubun(Protocol.GAME);
				tempDto.setType(Protocol.MAKEPLAYER);
				System.out.println("getID:" + dto.getId());
				System.out.println("player1:" + player1);
				System.out.println("player2:" + player2);
				System.out.println("player3:" + player3);
				System.out.println("player4:" + player4);
				if (player1.equals(dto.getId())) {
					tempDto.setNowPlayer(player1);
					tempDto.setPlayerNum(makePlayerNum + 1);
				} else if (player2.equals(dto.getId())) {
					tempDto.setNowPlayer(player2);
					tempDto.setPlayerNum(makePlayerNum + 1);
				} else if (player3.equals(dto.getId())) {
					tempDto.setNowPlayer(player3);
					tempDto.setPlayerNum(makePlayerNum + 1);
				} else if (player4.equals(dto.getId())) {
					tempDto.setNowPlayer(player4);
					tempDto.setPlayerNum(makePlayerNum + 1);
				}
				System.out.println(TAG + tempDto.getNowPlayer());
				tempDto.setNowPlayerX(initPlayerX(makePlayerNum));
				tempDto.setNowPlayerY(initPlayerY(makePlayerNum));
				tempDto.setPlayerImgSource(initPlayerImg(makePlayerNum));
				String makePlayer = gson.toJson(tempDto);
				for (int i = 0; i < playerList.size(); i++) {
					playerList.get(i).writer.println(makePlayer);
				}
				System.out.println(TAG + tempDto);
			}
			
			if (dto.getType().equals(Protocol.DICEROLL)) {
				tempDto.setGubun(Protocol.GAME);
				tempDto.setType(Protocol.DICEROLL);
				tempDto.setId(dto.getId());
				tempDto.setDice1(dto.getDice1());
				tempDto.setDice2(dto.getDice2());
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
				tempDto.setNewPlayerX(dto.getNewPlayerX());
				tempDto.setNewPlayerY(dto.getNewPlayerY());
				output = gson.toJson(tempDto);
				for (int i = 0; i < playerList.size(); i++) {
					playerList.get(i).writer.println(output);
				}
			}
		} // end of router
		
	} // end of thread
	
	int initPlayerX(int PlayerNum) {
		int result = 0;
		switch (PlayerNum) {
			case 0: result = 210;
				break;
			case 1: result = 210;
				break;
			case 2: result = 240;
				break;
			case 3: result = 240;
				break;
		}
		return result;
	}
	
	int initPlayerY(int PlayerNum) {
		int result = 0;
		switch (PlayerNum) {
			case 0: result = 210;
				break;
			case 1: result = 240;
				break;
			case 2: result = 210;
				break;
			case 3: result = 240;
				break;
		}
		return result;
	}
	
	String initPlayerImg(int PlayerNum) {
		String result = "";
		switch (PlayerNum) {
			case 0: result = "images/img_player01.png";
				break;
			case 1: result = "images/img_player02.png";
				break;
			case 2: result = "images/img_player03.png";
				break;
			case 3: result = "images/img_player04.png";
				break;
		}
		return result;
	}
	
}
