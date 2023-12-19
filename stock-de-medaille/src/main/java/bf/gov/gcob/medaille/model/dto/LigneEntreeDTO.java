package bf.gov.gcob.medaille.model.dto;

import bf.gov.gcob.medaille.model.AbstractBaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
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
    @Schema(accessMode = AccessMode.READ_ONLY, oneOf = {Long.class, EntreeDTO.class}, description = "Prendre une valeur de type Long en entrée (id). Donne en sortie le DTO")
    private Object entree;
    @Schema(accessMode = AccessMode.READ_ONLY, oneOf = {Long.class, MedailleDTO.class}, description = "Prendre une valeur de type Long en entrée (id). Donne en sortie le DTO")
    private Object medaille;
}
