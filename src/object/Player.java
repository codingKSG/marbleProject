package object;

import java.util.Vector;

public class Player {
	String name; // 이름(=로그인창에서입력한id)
	int money; // 보유 현금
	int asset; // 보유 총자산 // 건물 + 현금
	Vector<IsLandTile> playerCity; // 보유한 건물
	int playerX; // x좌표
	int playerY; // y좌표
	int countMove; // 몇 칸 갔는지 카운트
	int countTurn; // 몇 바퀴 갔는지 카운트 // 이동칸수/총칸수
	int nowPlayerTile; // 현재 플레이어가 위치한 타일번호
	int island; // 무인도 남은 턴 수
	boolean isTurn; // 현재 플레이어의 턴인지
	boolean isPlaying; // 플레이어 생존 여부

	void playerMove() {} // 스레드로 움직이는 애니메이션효과 , 끝나고 stepOn()실행
	void playerStepOn(){} // 도착장소에서 행동가능
	void playerFine(){} // 소유주가 다를 시 벌금
	void playerPurchase(){} // 땅, 건물 구매
	void playerSale(){} // 땅, 건물 처분
	void playerLose(){} // isLive=false 말고 뭘 더 넣을지 고민중
	void playerRoll(){} // 주사위 값 설정
	void showDice(){} // 주사위 보여주기
	void endTurn(){} // 턴 종료(isTurn False로 main의 sequenceFlow()호출)

}
