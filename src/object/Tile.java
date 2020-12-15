package object;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Tile {
	String tileName; //해당 타일의 이름
	int tileNum; // 해당 타일의 번호
	int tileType; // 0-출발 1-시티 2-섬 3-스페셜 4-무인도 5-올림픽 6-세계여행
	int tileX;
	int tileY;
	String landOwner; // 소유한 플레이어
	int fine; // 벌금 priceAll * 1.2
	int isPurchased[];// 땅/집/빌딩/호텔 샀는지
	int priceLand; // 땅값
	int priceHouse; // 집값
	int priceBuilding; // 빌딩값
	int priceHotel; // 호텔값
	int priceAll; // 전체구매비용 priceLand + priceHouse + priceBuilding + priceHotel
	
	public Tile(String tileName, int tileNum, int tileType, int tileX, int tileY, String landOwner, int fine,
			int[] isPurchased, int priceLand) {
		super();
		this.tileName = tileName;
		this.tileNum = tileNum;
		this.tileType = tileType;
		this.tileX = tileX;
		this.tileY = tileY;
		this.landOwner = landOwner;
		this.fine = fine;
		this.isPurchased = isPurchased;
		this.priceLand = priceLand;
	}

	public Tile(String tileName, int tileNum, int tileType, int tileX, int tileY) {
		super();
		this.tileName = tileName;
		this.tileNum = tileNum;
		this.tileType = tileType;
		this.tileX = tileX;
		this.tileY = tileY;
	}
	
}
