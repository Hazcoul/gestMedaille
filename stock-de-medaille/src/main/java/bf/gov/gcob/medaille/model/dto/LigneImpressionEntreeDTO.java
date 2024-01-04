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
public class LigneImpressionEntreeDTO extends AbstractBaseDTO {
    private Integer quantiteLigne;
    private Integer prixUnitaire;
    private Integer montantLigne;
    private String nomMagasin;
    private String libelleFournisseur;
    private String nomDepot;
    private String nomCompletMedaille;
    private String numeroCommande;
    private String acquisition;
}
