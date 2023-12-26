package bf.gov.gcob.medaille.model.dto;

import bf.gov.gcob.medaille.model.AbstractBaseDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MagasinDTO extends AbstractBaseDTO {

	private Long        idMagasin;
	@NotNull
    private String      nomMagasin;
	@NotNull
    private Integer     capacite;
    private String      description;
    private DepotDTO depot;
}
