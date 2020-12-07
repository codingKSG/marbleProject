package object;

import lombok.Getter;

@Getter
public class CityTile extends isLandTile implements CityInterface{
	int priceAll; //전체구매비용 priceLand + priceHouse + priceBuilding + priceHotel
	int priceHouse; //집값
	int priceBuilding; //빌딩값
	int priceHotel; //호텔값
	public CityTile(String tileName, int tileNum, int tileX, int tileY, int fine, int isPurchased, int priceLand,
			String landOwner, int priceAll, int priceHouse, int priceBuilding, int priceHotel) {
		super(tileName, tileNum, tileX, tileY, fine, isPurchased, priceLand, landOwner);
		this.priceAll = priceAll;
		this.priceHouse = priceHouse;
		this.priceBuilding = priceBuilding;
		this.priceHotel = priceHotel;
	}
	@Override
	public void buildup(int[] isPurchased) {
		
	}//땅을 구매했을 시 나오는 애니메이션
	@Override
	public void showTileInfo(CityTile cityTile) {	

	}//타일의 정보를 호출
}
