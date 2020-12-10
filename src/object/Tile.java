package object;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

public abstract class Tile {
	String tileName; //�ش� Ÿ���� �̸�
	int tileNum; // �ش� Ÿ���� ��ȣ
	int tileType; // 0-��� 1-��Ƽ 2-�� 3-�����
	int tileX;
	int tileY;
}
