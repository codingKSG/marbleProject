package object;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

public abstract  class Tile {
	String tileName; //해당 타일의 이름
	int tileNum; // 해당 타일의 번호
	int tileX;
	int tileY;
}
