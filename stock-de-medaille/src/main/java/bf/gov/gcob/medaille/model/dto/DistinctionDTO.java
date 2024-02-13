package bf.gov.gcob.medaille.model.dto;

import java.util.Date;

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
public class DistinctionDTO extends AbstractBaseDTO {

	private Long					idDistinction;
	@NotNull
    private String					code;
    private String					abreviation;
    @NotNull
    private String					libelle;
    @NotNull
    private String					categoryDistinction;
    private String					referenceDecret;
    private Date 					dateDecretCreation;
    private String					description;
}
