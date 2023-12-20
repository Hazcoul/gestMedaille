package bf.gov.gcob.medaille.model.dto;

import java.util.Date;
import java.util.List;

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
public class SortieDTO extends AbstractBaseDTO {

	private Long		idSortie;
    private Date dateSortie;
    @NotNull
    private String		motifSortie;
    private Date          validerLe;
    private String        validerPar;
    private String		description;
    @Schema(accessMode = AccessMode.READ_ONLY, oneOf = {Long.class, OrdonnateurDTO.class}, description = "Prendre une valeur de type Long en entrée (id). Donne en sortie le DTO")
    private Object ordonnateur;
    @Schema(accessMode = AccessMode.READ_ONLY, oneOf = {Long.class, BeneficiaireDTO.class}, description = "Prendre une valeur de type Long en entrée (id). Donne en sortie le DTO")
    private Object beneficiaire;
    @Schema(accessMode = AccessMode.READ_ONLY, oneOf = {Long.class, DetenteurDTO.class}, description = "Prendre une valeur de type Long en entrée (id). Donne en sortie le DTO")
    private Object detenteur;
    @Schema(accessMode = AccessMode.READ_ONLY, oneOf = {Long.class, MagasinDTO.class}, description = "Prendre une valeur de type Long en entrée (id). Donne en sortie le DTO")
    private Object magasin;
    List<LigneSortieDTO> ligneSorties;
}
