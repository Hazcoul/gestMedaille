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
public class LigneSortieDTO extends AbstractBaseDTO {

	private Long		idLigneSortie;
    @NotNull
    private Integer		quantiteLigne;
    private boolean		isCloseSortie;
    private SortieDTO sortie;
    private MedailleDTO medaille;
}
