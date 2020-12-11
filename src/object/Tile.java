package object;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public abstract class Tile {
	String tileName; //�ش� Ÿ���� �̸�
	int tileNum; // �ش� Ÿ���� ��ȣ
	int tileType; // 0-��� 1-��Ƽ 2-�� 3-�����
	int tileX;
	int tileY;
}
