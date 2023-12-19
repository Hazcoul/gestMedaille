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
public class EntreeDTO extends AbstractBaseDTO {

	private Long          idEntree;
	@NotNull
	private Date          dateEntree;
	@NotNull
    private String        numeroCmd;
    private Date          validerLe;
    private String        validerPar;
    private String        observation;
    private Date          dateReception;
    private Integer       exerciceBudgetaire;
    private String        acquisition;
}
