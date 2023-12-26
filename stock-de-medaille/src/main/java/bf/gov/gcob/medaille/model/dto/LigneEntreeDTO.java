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
public class LigneEntreeDTO extends AbstractBaseDTO {

	private Long idLigneEntree;
    @NotNull
    private Integer quantiteLigne;
    private Double prixUnitaire;
    private Double montantLigne;
    private boolean isCloseEntree;
    private EntreeDTO entree;
    private MedailleDTO medaille;
}
