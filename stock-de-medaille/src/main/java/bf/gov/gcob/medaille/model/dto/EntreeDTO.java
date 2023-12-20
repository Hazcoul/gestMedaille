package bf.gov.gcob.medaille.model.dto;

import java.util.Date;

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
    @Schema(accessMode = AccessMode.READ_ONLY, oneOf = {Long.class, FournisseurDTO.class}, description = "Prendre une valeur de type Long en entrée (id). Donne en sortie le DTO")
    private Object fournisseur;
    @Schema(accessMode = AccessMode.READ_ONLY, oneOf = {Long.class, MagasinDTO.class}, description = "Prendre une valeur de type Long en entrée (id). Donne en sortie le DTO")
    private Object magasin;
}
