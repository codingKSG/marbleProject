package protocol;

public interface Protocol {
	String HOST = "localhost";
	int PORT = 10001;
	
	String CHAT = "CHAT";
	String GAME = "GAME";
	
	// ���� �÷��̾� �� üũ
	String PLAYERNUMCHECK = "PLAYERNUMCHECK";
	
	// �ʱ� ID ����
	String IDSET = "IDSET";
	
	// �̹� �����ϴ� ID���� üũ
	String IDCHECK = "IDCHECK";
	
	// ĳ���� �̹��� ���� ��û
	String MAKEPLAYER = "MAKEPLAYER";
	
	// �ֻ����� �����ٴ� ��ȣ
	String DICEROLL = "DICEROLL";
	
	// ĳ���� �����̴� ��ȣ
	String MOVE = "MOVE";
}
