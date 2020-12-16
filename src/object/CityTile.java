package object;

public class CityTile extends Tile {
	

	public CityTile(String tileName, int tileNum, int tileType, int tileX, int tileY, String landOwner, int fine,
			int[] isPurchased, int priceLand, int priceHouse, int priceBuilding, int priceHotel, int priceAll, int olympicCount) {
		super(tileName, tileNum, tileType, tileX, tileY, landOwner, fine, isPurchased, priceLand, priceHouse, priceBuilding,
				priceHotel, priceAll, olympicCount);
	}
	
}