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
public class MedailleDTO extends AbstractBaseDTO {

    private Long idMedaille;
    private String nomComplet;
    private Integer stock;
    private String lienImage;
    private String description;
    private byte[] image;
    private DistinctionDTO distinction;
    private GradeDTO grade;
}
