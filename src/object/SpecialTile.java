package object;

public class SpecialTile extends Tile implements SpecialInterface{

	public SpecialTile(String tileName, int tileNum, int tileX, int tileY) {
		super(tileName, tileNum, tileX, tileY);
	}

	@Override
	public void event() {
		
	}// 스페셜 타일의 이벤트

	
}
