package protocol;

import lombok.Data;

@Data
public class ChatDto extends RequestDto {
	private final String valType = "CHAT";
	private String id;
	private String text;
}
