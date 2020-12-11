package protocol;

public interface Protocol {
	String HOST = "localhost";
	int PORT = 10001;
	
	String CHAT = "CHAT";
	String GAME = "GAME";
	
	// ���� ���� ������ �÷��̾� = ����
	String GAMEHOST = "GAMEHOST";
	
	// ���� ��ŸƮ �˸�
	String GAMESTART = "GAMESTART";
	
	// �������
	String REJECTENTER = "REJECTENTER";
	
	// ���� �÷��̾� �� üũ
	String PLAYERNUMCHECK = "PLAYERNUMCHECK";
	
	// �ʱ� ID ����
	String IDSET = "IDSET";
	
	// �ʱ� PLAYER��ȣ ����
	String PLAYERSET = "PLAYERSET";
	
	// �̹� �����ϴ� ID���� üũ
	String IDCHECK = "IDCHECK";
	
	// ĳ���� �̹��� ���� ��û
	String MAKEPLAYER = "MAKEPLAYER";
	
	// �ֻ����� �����ٴ� ��ȣ
	String DICEROLL = "DICEROLL";
	
	// ĳ���� �����̴� ��ȣ
	String MOVE = "MOVE";
}
