package protocol;

public interface Protocol {
	String HOST = "localhost";
	int PORT = 10001;
	
	String CHAT = "CHAT";
	String GAME = "GAME";
	
	// 서버 플레이어 수 체크
	String PLAYERNUMCHECK = "PLAYERNUMCHECK";
	
	// 초기 ID 셋팅
	String IDSET = "IDSET";
	
	// 이미 존재하는 ID인지 체크
	String IDCHECK = "IDCHECK";
	
	// 캐릭터 이미지 생성 요청
	String MAKEPLAYER = "MAKEPLAYER";
	
	// 주사위를 굴린다는 신호
	String DICEROLL = "DICEROLL";
	
	// 캐릭터 움직이는 신호
	String MOVE = "MOVE";
}
