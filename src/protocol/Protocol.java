package protocol;

public interface Protocol {
	String HOST = "localhost";
	int PORT = 10001;
	
	String CHAT = "CHAT";
	String GAME = "GAME";
	
	// 주사위를 굴린다는 신호
	String DICEROLL = "DICEROLL";
	
	// 캐릭터 움직이는 신호
	String MOVE = "MOVE";
}
