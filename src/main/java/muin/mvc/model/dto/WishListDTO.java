package muin.mvc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WishListDTO {
	private Long wishNo;              //찜리스트 번호
	private Long movieNo;
	private Long memberId;
}
