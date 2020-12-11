package object;

public class CityTile extends Tile implements CityInterface {
	

	public CityTile(String tileName, int tileNum, int tileType, int tileX, int tileY, String landOwner, int fine,
			int[] isPurchased, int priceLand, int priceHouse, int priceBuilding, int priceHotel, int priceAll) {
		super(tileName, tileNum, tileType, tileX, tileY, landOwner, fine, isPurchased, priceLand, priceHouse, priceBuilding,
				priceHotel, priceAll);
	}

	public void buildup(int[] isPurchased) {

	}

	// 건물을 구매했을 시 나오는 애니메이션
	@Override
	public void showTileInfo(CityTile cityTile) {

	}

	
}