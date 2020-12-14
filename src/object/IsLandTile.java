package object;

public class IsLandTile extends Tile implements IslandInterface {
	
	public IsLandTile(String tileName, int tileNum, int tileType, int tileX, int tileY, String landOwner, int fine,
			int[] isPurchased, int priceLand) {
		super(tileName, tileNum, tileType, tileX, tileY, landOwner, fine, isPurchased, priceLand);
	}

	@Override
	public void showTileInfo(IsLandTile isLandTile) {
	}
}
