package object;

import lombok.Builder;
import lombok.Getter;
@Getter
public class isLandTile extends Tile implements IslandInterface{

	int fine; //���� priceAll * 1.2
	int isPurchased;// ��/��/����/ȣ�� �����
	int priceLand; // ����
	String landOwner; //������ �÷��̾�
	@Builder
	public isLandTile(String tileName, int tileNum, int tileX, int tileY, int fine, int isPurchased, int priceLand,
			String landOwner) {
		super(tileName, tileNum, tileX, tileY);
		this.fine = fine;
		this.isPurchased = isPurchased;
		this.priceLand = priceLand;
		this.landOwner = landOwner;
	}
	@Override
	public void showTileInfo(isLandTile isLandTile) {
	}//Ÿ���� ������ ȣ��
}
