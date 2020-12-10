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
	
	private String nowPlayer; // ���� ������ �÷��̾�
	private int countTurn; // ���� ���� �� ��
	private int dice1; // �ֻ��� ���� �����ֱ� ���� ��
	private int dice2; // �ֻ��� ���� �����ֱ� ���� ��
	private int countPlayer; // ���� ���� �÷��̾� ��
	private boolean isPlaying = false;

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
				tempDto.setNewPlayerTile(dto.getNewPlayerTile());
				output = gson.toJson(tempDto);
				for (int i = 0; i < playerList.size(); i++) {
					playerList.get(i).writer.println(output);
					System.out.println(TAG + "MOVE �޾Ƽ� ����");
				}
			}
		} // end of router
		
	} // end of thread
}
