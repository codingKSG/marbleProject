package protocol;

import lombok.Data;

@Data
public class GameDto {
	private int dice1;
	private int dice2;
	private String nowPlayer;
}
