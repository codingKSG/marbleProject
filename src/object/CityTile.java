package object;

import lombok.Getter;

@Getter
public class CityTile extends IsLandTile implements CityInterface{
	int priceHouse; //집값
	int priceBuilding; //빌딩값
	int priceHotel; //호텔값
	int priceAll; //전체구매비용 priceLand + priceHouse + priceBuilding + priceHotel
	
	public void buildup(int[] isPurchased) {
		
	}//건물을 구매했을 시 나오는 애니메이션
	@Override
	public void showTileInfo(CityTile cityTile) {	

	}
	public CityTile(String tileName, int tileNum, int tileType, int tileX, int tileY, String landOwner, int fine,
			int[] isPurchased, int priceLand, int priceHouse, int priceBuilding, int priceHotel, int priceAll) {
		super(tileName, tileNum, tileType, tileX, tileY, landOwner, fine, isPurchased, priceLand);
		this.priceHouse = priceHouse;
		this.priceBuilding = priceBuilding;
		this.priceHotel = priceHotel;
		this.priceAll = priceAll;
	}//타일의 정보를 호출
}