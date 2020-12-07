package object;

import lombok.Builder;
import lombok.Getter;
@Getter
public class isLandTile extends Tile implements IslandInterface{

	int fine; //벌금 priceAll * 1.2
	int isPurchased;// 땅/집/빌딩/호텔 샀는지
	int priceLand; // 땅값
	String landOwner; //소유한 플레이어
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
	}//타일의 정보를 호출
}
