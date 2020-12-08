package object;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

public abstract  class Tile {
	int tileType; // 0-��� 1-��Ƽ 2-�� 3-�����
	String tileName; //�ش� Ÿ���� �̸�
	int tileNum; // �ش� Ÿ���� ��ȣ
	int tileX;
	int tileY;
}
