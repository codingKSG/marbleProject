package object;

public class CityTile extends Tile implements CityInterface {
	

	public CityTile(String tileName, int tileNum, int tileType, int tileX, int tileY, String landOwner, int fine,
			int[] isPurchased, int priceLand, int priceHouse, int priceBuilding, int priceHotel, int priceAll) {
		super(tileName, tileNum, tileType, tileX, tileY, landOwner, fine, isPurchased, priceLand, priceHouse, priceBuilding,
				priceHotel, priceAll);
	}

	public void buildup(int[] isPurchased) {

	}

	// �ǹ��� �������� �� ������ �ִϸ��̼�
	@Override
	public void showTileInfo(CityTile cityTile) {

	}

	
}