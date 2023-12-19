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
public class MagasinDTO extends AbstractBaseDTO {

	private Long        idMagasin;
	@NotNull
    private String      nomMagasin;
	@NotNull
    private Integer     capacite;
    private String      description;
    @Schema(accessMode = AccessMode.READ_ONLY, oneOf = {Long.class, DepotDTO.class}, description = "Prendre une valeur de type Long en entrée (id). Donne en sortie le DTO")
    private Object depot;
}
