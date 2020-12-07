package object;

import lombok.Getter;

@Getter
public class CityTile extends isLandTile implements CityInterface{
	int priceAll; //��ü���ź�� priceLand + priceHouse + priceBuilding + priceHotel
	int priceHouse; //����
	int priceBuilding; //������
	int priceHotel; //ȣ�ڰ�
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
		
	}//���� �������� �� ������ �ִϸ��̼�
	@Override
	public void showTileInfo(CityTile cityTile) {	

	}//Ÿ���� ������ ȣ��
}
