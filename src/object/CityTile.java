package object;

import lombok.Getter;

@Getter
public class CityTile extends IsLandTile implements CityInterface{
	int priceHouse; //����
	int priceBuilding; //������
	int priceHotel; //ȣ�ڰ�
	int priceAll; //��ü���ź�� priceLand + priceHouse + priceBuilding + priceHotel
	
	public void buildup(int[] isPurchased) {
		
	}//�ǹ��� �������� �� ������ �ִϸ��̼�
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
	}//Ÿ���� ������ ȣ��
}