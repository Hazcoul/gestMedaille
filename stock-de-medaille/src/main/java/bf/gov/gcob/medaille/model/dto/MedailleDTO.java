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
public class MedailleDTO extends AbstractBaseDTO {

    private Long idMedaille;
    private String nomComplet;
    private Integer stock;
    private String lienImage;
    private String description;
    private boolean horsUsage;
    @Schema(accessMode = AccessMode.READ_ONLY, oneOf = {Long.class, DistinctionDTO.class}, description = "Prendre une valeur de type Long en entrée (id). Donne en sortie le DTO")
    private Object distinction;
    @Schema(accessMode = AccessMode.READ_ONLY, oneOf = {Long.class, GradeDTO.class}, description = "Prendre une valeur de type Long en entrée (id). Donne en sortie le DTO")
    private Object grade;
}
