package tile;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IslandTile extends Tile{
	
	private int fine;
	private int isPurchased;
	private int priceLand;
	private String owner;
	
}
