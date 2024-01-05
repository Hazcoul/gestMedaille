package bf.gov.gcob.medaille.model.dto;

import bf.gov.gcob.medaille.model.AbstractBaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.kafka.common.protocol.types.Field;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilterSortieDto extends AbstractBaseDTO {

	private Integer annee;
    private Long detenteur;
    private Long ordonnateur;
    private Long beneficiaire;
    private String motifSortie;
}
