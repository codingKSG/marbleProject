package protocol;

import lombok.Data;

@Data
public class RequestDto {
	private String gubun;
	private String nowPlayer;
	
	// ä�ÿ� �ʿ��� ��������
	private String id;
	private String text;
	
	// �Ʒ��� ���� ���࿡ �ʿ��� ��������
	private String type; 
	private int dice1;
	private int dice2;
	
	// �̵��� ���� ��ġ��
	private int nowBoardNum;
	private int nowX;
	private int nowY;
	
	// �̵��� ���� ��ġ��
	private int newBoardNum;
	private int newX;
	private int newY;
}
