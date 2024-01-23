package bf.gov.gcob.medaille.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ApiResponse<T> {
	private String code;
	private String msg;
	private T data;
	
	
}
