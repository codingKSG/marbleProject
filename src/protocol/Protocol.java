package protocol;

public interface Protocol {
	String HOST = "localhost";
	int PORT = 10001;
	
	String CHAT = "CHAT";
	String GAME = "GAME";
	
	// �ʱ� ID ����
	String IDSET = "IDSET";
	
	// �ֻ����� �����ٴ� ��ȣ
	String DICEROLL = "DICEROLL";
	
	// ĳ���� �����̴� ��ȣ
	String MOVE = "MOVE";
}
