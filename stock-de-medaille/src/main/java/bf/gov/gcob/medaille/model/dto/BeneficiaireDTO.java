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
public class BeneficiaireDTO extends AbstractBaseDTO {

	private Long         idBeneficiaire;
    private String       sigle;
    @NotNull(message = "La raison sociale est obligatoire")
    private String       raisonSociale;
    private String       telephoneFix;
    private String       telephoneMobile;
    private String       email;
    private String       adresse;
}
