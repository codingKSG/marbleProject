package object;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

public abstract  class Tile {
	String tileName; //�ش� Ÿ���� �̸�
	int tileNum; // �ش� Ÿ���� ��ȣ
	int tileX;
	int tileY;
}
