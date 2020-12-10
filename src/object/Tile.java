package object;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

public abstract class Tile {
	String tileName; //해당 타일의 이름
	int tileNum; // 해당 타일의 번호
	int tileType; // 0-출발 1-시티 2-섬 3-스페셜
	int tileX;
	int tileY;
}
