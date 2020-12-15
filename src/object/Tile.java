package object;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Tile {
	String tileName; //�ش� Ÿ���� �̸�
	int tileNum; // �ش� Ÿ���� ��ȣ
	int tileType; // 0-��� 1-��Ƽ 2-�� 3-����� 4-���ε� 5-�ø��� 6-���迩��
	int tileX;
	int tileY;
	String landOwner; // ������ �÷��̾�
	int fine; // ���� priceAll * 1.2
	int isPurchased[];// ��/��/����/ȣ�� �����
	int priceLand; // ����
	int priceHouse; // ����
	int priceBuilding; // ������
	int priceHotel; // ȣ�ڰ�
	int priceAll; // ��ü���ź�� priceLand + priceHouse + priceBuilding + priceHotel
	
	public Tile(String tileName, int tileNum, int tileType, int tileX, int tileY, String landOwner, int fine,
			int[] isPurchased, int priceLand) {
		super();
		this.tileName = tileName;
		this.tileNum = tileNum;
		this.tileType = tileType;
		this.tileX = tileX;
		this.tileY = tileY;
		this.landOwner = landOwner;
		this.fine = fine;
		this.isPurchased = isPurchased;
		this.priceLand = priceLand;
	}

	public Tile(String tileName, int tileNum, int tileType, int tileX, int tileY) {
		super();
		this.tileName = tileName;
		this.tileNum = tileNum;
		this.tileType = tileType;
		this.tileX = tileX;
		this.tileY = tileY;
	}
	
}
