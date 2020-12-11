package object;

import lombok.Getter;

@Getter
public class IsLandTile extends Tile implements IslandInterface {

	String landOwner; // ������ �÷��̾�
	int fine; // ���� priceAll * 1.2
	int isPurchased[];// ��/��/����/ȣ�� �����
	int priceLand; // ����

	@Override
	public void showTileInfo(IsLandTile isLandTile) {
	}

	public IsLandTile(String tileName, int tileNum, int tileType, int tileX, int tileY, String landOwner, int fine,
			int[] isPurchased, int priceLand) {
		super(tileName, tileNum, tileType, tileX, tileY);
		this.landOwner = landOwner;
		this.fine = fine;
		this.isPurchased = isPurchased;
		this.priceLand = priceLand;
	}// Ÿ���� ������ ȣ��
}
