package bf.gov.gcob.medaille.model.dto;

import bf.gov.gcob.medaille.model.AbstractBaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PieceJointeDTO extends AbstractBaseDTO {

	private Long        idPiece;
    private String      typePiece;
    private String      lienPiece;
    private String      referencePiece;
    private String      description;
    @Schema(accessMode = AccessMode.READ_ONLY, oneOf = {Long.class, EntreeDTO.class}, description = "Prendre une valeur de type Long en entrée (id). Donne en sortie le DTO")
    private Object entree;
    @Schema(accessMode = AccessMode.READ_ONLY, oneOf = {Long.class, SortieDTO.class}, description = "Prendre une valeur de type Long en entrée (id). Donne en sortie le DTO")
    private Object sortie;
}
