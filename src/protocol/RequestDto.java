package protocol;

import lombok.Data;
import object.CityTile;
import object.Tile;

@Data
public class RequestDto {
	private String gubun;
	private String nowPlayer;
	
	// 현재 플레이어 인원
	private int playerNum;
	
	// 플레이어별 캐릭터 생성 이미지
	private String playerImgSource;
	
	// 클라이언트 내 플레이어 객체에 아이디를 넣기 위함(서버 -> 클라이언트)
	private String player1;
	private String player2;
	private String player3;
	private String player4;
	
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
	
	private Tile tileInfo;
	private int tileType;
}
