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
	
	// ���̾�α� ���� ��û
	String DIALOGREQUEST = "DIALOGREQUEST";
	
	// ���̾�α� ���� Ŭ���̾�Ʈ >> ����
	String DIALOGUPDATE = "DIALOGUPDATE";
	
	// �÷��̾� ���� ���� ����
	String PLAYERPURCHASED = "PLAYERPURCHASED";
	
	// �÷��̾� ����� ���� ����
	String PLAYERFINE = "PLAYERFINE";
	
	// �÷��̾� �ƿ� ���� ����
	String PLAYERDIE = "PLAYERDIE";

	// �÷��̾� �ǹ� ����
	String PLAYERBUILD = "PLAYERBUILD";
	
	// �÷��̾� ���Ϸ��� �� ����
	String PLAYERISLAND = "PLAYERISLAND";
	
	// �����κ��� Ÿ�ϸ���Ʈ pull
	String TILELISTPULL = "TILELISTPULL";
	
	// �÷��̾� ��
	String TURN = "TURN";
	
	// ���� ��
	String NEXTTURN = "NEXTTURN";
	
	// �� ����
	String ENDTURN = "ENDTURN";
}
