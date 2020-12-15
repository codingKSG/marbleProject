package protocol;

import java.util.Vector;

import lombok.Data;
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

	// 타일 정보 값
	private Tile tileInfo;
	
	// 타일 타입 값
	private int tileType;
	
	// 플레이어 구입 금액
	private int newprice;
	
	// 타일 통행료 금액
	private int tileFine;
	
	// 타일 주인의 아이디
	private String tileOwnerId;

	// 새로 지어지는 건물리스트
	private int[] newBuild;
	
	// 새로 지을 건물의 좌표
	private int buildX;
	private int buildY;
	
	// 서버로부터 받아오는 타일리스트
	private Vector<Tile> tileList;
	
	// 서버 및 클라이언트 타일 업데이트 시 타일리스트 인덱스 확인용
	private int tileNum;
	
	// 플레이어 턴을 알려줌.(아이디를 통해)
	private String turnId;
	
	// 올림픽 밟은 플레이어의 타일 리스트
	private Vector<Tile> playerTileList;
}
