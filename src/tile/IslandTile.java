package tile;

import lombok.Data;

@Data
public class IslandTile extends Tile{
	
	private int fine;
	private int isPurchased;
	private int priceLand;
	private String owner;
	
	public IslandTile(String name, int tileNum, int x, int y, int fine, int isPurchased, int priceLand, String owner) {
		this.fine = fine;
		this.isPurchased = isPurchased;
		this.priceLand = priceLand;
		this.owner = owner;
		
		this.name = name;
		this.tileNum = tileNum;
		this.x = x;
		this.y = y;
	}
	
	
}
