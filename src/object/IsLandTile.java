package object;

import lombok.Getter;

@Getter
public class IsLandTile extends Tile implements IslandInterface {

	String landOwner; // 소유한 플레이어
	int fine; // 벌금 priceAll * 1.2
	int isPurchased[];// 땅/집/빌딩/호텔 샀는지
	int priceLand; // 땅값

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
	}// 타일의 정보를 호출
}
