package bf.gov.gcob.medaille.model.dto;

import bf.gov.gcob.medaille.model.AbstractBaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LigneImpressionSortieDTO extends AbstractBaseDTO {
    private Integer quantite;
    private String  Medaille;
    private String  detenteur;
    private String  motif;
    private String  structureBeneficiaire;
    private String  numeroSortie;
    private LocalDate dateSortie;
}
