package bf.gov.gcob.medaille.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LigneImpressionSortiePeriodeDTO {
    private String  medaille;
    private Long quantite;
    private Date dateSortie;

}
