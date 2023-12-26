package bf.gov.gcob.medaille.model.dto;

import bf.gov.gcob.medaille.model.AbstractBaseDTO;
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
    private EntreeDTO   entree;
    private SortieDTO   sortie;
}
