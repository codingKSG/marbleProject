package protocol;

import java.util.Vector;

import lombok.Data;
import object.Tile;

@Data
public class RequestDto {
	private String gubun;
	private String nowPlayer;
	
	// ���� �÷��̾� �ο�
	private int playerNum;
	
	// �÷��̾ ĳ���� ���� �̹���
	private String playerImgSource;
	
	// Ŭ���̾�Ʈ �� �÷��̾� ��ü�� ���̵� �ֱ� ����(���� -> Ŭ���̾�Ʈ)
	private String player1;
	private String player2;
	private String player3;
	private String player4;
	
	// ä�ÿ� �ʿ��� ��������
	private String id;
	private String text;
	
	// �Ʒ��� ���� ���࿡ �ʿ��� ��������
	private String type; 
	private int dice1;
	private int dice2;
	
	// �̵��� ���� ��ġ��
	private int nowPlayerTile;
	private int nowPlayerX;
	private int nowPlayerY;
	
	// �̵��� ���� ��ġ��
	private int newPlayerTile;
	private int newPlayerX;
	private int newPlayerY;

	// Ÿ�� ���� ��
	private Tile tileInfo;
	
	// Ÿ�� Ÿ�� ��
	private int tileType;
	
	// �÷��̾� ���� �ݾ�
	private int newprice;
	
	// Ÿ�� ����� �ݾ�
	private int tileFine;
	
	// Ÿ�� ������ ���̵�
	private String tileOwnerId;

	// ���� �������� �ǹ�����Ʈ
	private int[] newBuild;
	
	// ���� ���� �ǹ��� ��ǥ
	private int buildX;
	private int buildY;
	
	// �����κ��� �޾ƿ��� Ÿ�ϸ���Ʈ
	private Vector<Tile> tileList;
	
	// ���� �� Ŭ���̾�Ʈ Ÿ�� ������Ʈ �� Ÿ�ϸ���Ʈ �ε��� Ȯ�ο�
	private int tileNum;
	
	// �÷��̾� ���� �˷���.(���̵� ����)
	private String turnId;
	
	// �ø��� ���� �÷��̾��� Ÿ�� ����Ʈ
	private Vector<Tile> playerTileList;
}
