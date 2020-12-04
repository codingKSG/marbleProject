package protocol;

import lombok.Data;

@Data
public class GameDto extends RequestDto {
	private final String valType = "GAME";
	private int dice1;
	private int dice2;
	private String nowPlayer;
}
