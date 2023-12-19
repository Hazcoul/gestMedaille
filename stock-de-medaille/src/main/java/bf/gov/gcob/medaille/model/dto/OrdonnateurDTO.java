package bf.gov.gcob.medaille.model.dto;

import bf.gov.gcob.medaille.model.AbstractBaseDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdonnateurDTO extends AbstractBaseDTO {

	private Long 	idOrdonnateur;
    @NotNull
    private String 	matricule;
    private String 	civilite;
    @NotNull
    private String 	nom;
    @NotNull
    private String 	prenom;
    @NotNull
    private String 	fonction;
    @NotNull
    private String 	telephone;
    private String 	email;
}
