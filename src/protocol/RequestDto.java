package protocol;

import lombok.Data;
import object.CityTile;

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
}
