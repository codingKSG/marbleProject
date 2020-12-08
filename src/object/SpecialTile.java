package object;

public class SpecialTile extends Tile implements SpecialInterface{

	public SpecialTile(int tileType, String tileName, int tileNum, int tileX, int tileY) {
		super(tileType, tileName, tileNum, tileX, tileY);
	}

	@Override
	public void event() {
		
	}// 스페셜 타일의 이벤트

	
}
