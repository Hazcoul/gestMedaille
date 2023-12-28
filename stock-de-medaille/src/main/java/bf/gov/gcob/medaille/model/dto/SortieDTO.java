package bf.gov.gcob.medaille.model.dto;

import java.util.Date;
import java.util.List;

import bf.gov.gcob.medaille.model.AbstractBaseDTO;
import bf.gov.gcob.medaille.model.dto.EntreeDTO.MvtStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SortieDTO extends AbstractBaseDTO {

	private Long		idSortie;
    private Date dateSortie;
    @NotNull
    private MotifSortie		motifSortie;
    private Date          validerLe;
    private String        validerPar;
    private String		description;
    private OrdonnateurDTO ordonnateur;
    private BeneficiaireDTO beneficiaire;
    private DetenteurDTO detenteur;
    private MagasinDTO magasin;
    private List<LigneSortieDTO> ligneSorties;
    private MvtStatus status;
    private String numeroSortie;
    
    public enum MotifSortie {
    	DECORATION,
        VENTE,
        DETERIORE;
    }
}
