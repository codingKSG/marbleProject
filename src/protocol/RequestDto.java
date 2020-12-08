package protocol;

import lombok.Data;

@Data
public class RequestDto {
	private String gubun;
	private String nowPlayer;
	
	// 채팅에 필요한 프로토콜
	private String id;
	private String text;
	
	// 아래는 게임 진행에 필요한 프로토콜
	private String type; 
	private int dice1;
	private int dice2;
	
	// 이동전 현재 위치값
	private int nowPlayerTile;
	private int nowPlayerX;
	private int nowPlayerY;
	
	// 이동시 변경 위치값
	private int newPlayerTile;
	private int newPlayerX;
	private int newPlayerY;
}
