package object;

import java.util.Vector;

public class Player {
	String name; // �̸�(=�α���â�����Է���id)
	int money; // ���� ����
	int asset; // ���� ���ڻ� // �ǹ� + ����
	Vector<IsLandTile> playerCity; // ������ �ǹ�
	int playerX; // x��ǥ
	int playerY; // y��ǥ
	int countMove; // �� ĭ ������ ī��Ʈ
	int countTurn; // �� ���� ������ ī��Ʈ // �̵�ĭ��/��ĭ��
	int nowPlayerTile; // ���� �÷��̾ ��ġ�� Ÿ�Ϲ�ȣ
	int island; // ���ε� ���� �� ��
	boolean isTurn; // ���� �÷��̾��� ������
	boolean isPlaying; // �÷��̾� ���� ����

	void playerMove() {} // ������� �����̴� �ִϸ��̼�ȿ�� , ������ stepOn()����
	void playerStepOn(){} // ������ҿ��� �ൿ����
	void playerFine(){} // �����ְ� �ٸ� �� ����
	void playerPurchase(){} // ��, �ǹ� ����
	void playerSale(){} // ��, �ǹ� ó��
	void playerLose(){} // isLive=false ���� �� �� ������ �����
	void playerRoll(){} // �ֻ��� �� ����
	void showDice(){} // �ֻ��� �����ֱ�
	void endTurn(){} // �� ����(isTurn False�� main�� sequenceFlow()ȣ��)

}
