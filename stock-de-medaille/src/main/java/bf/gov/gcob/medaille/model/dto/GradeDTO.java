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
public class GradeDTO extends AbstractBaseDTO {

    private Long idGrade;
    private String typeGrade;
    private int code;//1 pour Chevalier, 2:Officier, 3:Commandeur, 4:Grand-officier et 5:Grand-croix
    private String libelle;
    private String description;
}
