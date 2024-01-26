package bf.gov.gcob.medaille.model.dto;

import java.util.Date;
import java.util.List;

import bf.gov.gcob.medaille.model.AbstractBaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntreeDTO extends AbstractBaseDTO {

    private Long idEntree;
    //@NotNull
    private Date dateEntree;
    //@NotNull
    private String numeroCmd;
    private Date validerLe;
    private String validerPar;
    private String observation;
    private Date dateReception;
    private Integer exerciceBudgetaire;
    private Acquisition acquisition;
    private FournisseurDTO fournisseur;
    private MagasinDTO magasin;
    private List<LigneEntreeDTO> ligneEntrees;
    private MvtStatus status;

    public enum MvtStatus {
        CREATED, VALIDATED, REJECT, CLOSED;
    }

    public enum Acquisition {
        ACQUISITION, REVERSEMENT, RETOUR;
    }
}
