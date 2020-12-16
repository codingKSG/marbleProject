package object;

public class IsLandTile extends Tile {
	
	public IsLandTile(String tileName, int tileNum, int tileType, int tileX, int tileY, String landOwner, int fine,
			int[] isPurchased, int priceLand, int olympicCount) {
		super(tileName, tileNum, tileType, tileX, tileY, landOwner, fine, isPurchased, priceLand, olympicCount);
	}
}
