package protocol;

public interface Protocol {
	String HOST = "localhost";
	int PORT = 10001;
	
	String CHAT = "CHAT";
	String GAME = "GAME";
	
	// 제일 먼저 입장한 플레이어 = 방장
	String GAMEHOST = "GAMEHOST";
	
	// 게임 스타트 알림
	String GAMESTART = "GAMESTART";
	
	// 입장거절
	String REJECTENTER = "REJECTENTER";
	
	// 서버 플레이어 수 체크
	String PLAYERNUMCHECK = "PLAYERNUMCHECK";
	
	// 초기 ID 셋팅
	String IDSET = "IDSET";
	
	// 초기 PLAYER번호 지정
	String PLAYERSET = "PLAYERSET";
	
	// 이미 존재하는 ID인지 체크
	String IDCHECK = "IDCHECK";
	
	// 캐릭터 이미지 생성 요청
	String MAKEPLAYER = "MAKEPLAYER";
	
	// 주사위를 굴린다는 신호
	String DICEROLL = "DICEROLL";
	
	// 캐릭터 움직이는 신호
	String MOVE = "MOVE";
	
	// 다이얼로그 정보 요청
	String DIALOGREQUEST = "DIALOGREQUEST";
	
	// 다이얼로그 정보 클라이언트 >> 서버
	String DIALOGUPDATE = "DIALOGUPDATE";
	
	// 플레이어 구매 정보 전달
	String PLAYERPURCHASED = "PLAYERPURCHASED";
	
	// 플레이어 통행료 정보 전달
	String PLAYERFINE = "PLAYERFINE";
	
	// 플레이어 아웃 정보 전달
	String PLAYERDIE = "PLAYERDIE";

	// 플레이어 건물 생성
	String PLAYERBUILD = "PLAYERBUILD";
	
	// 플레이어 아일랜드 땅 구매
	String PLAYERISLAND = "PLAYERISLAND";
	
	// 서버로부터 타일리스트 pull
	String TILELISTPULL = "TILELISTPULL";
	
	// 플레이어 턴
	String TURN = "TURN";
	
	// 다음 턴
	String NEXTTURN = "NEXTTURN";
	
	// 턴 종료
	String ENDTURN = "ENDTURN";
}
