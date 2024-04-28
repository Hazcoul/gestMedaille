/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.gov.gcob.medaille.model.reportdto;

import java.io.InputStream;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BmConsommationDTO {

    private InputStream logo;
    private String referenceBordereau;
    private String dateSortie;
    private String magasin;
    private String detenteur;
    private String beneficiaire;
    private String magasinier;
    private String cpm;
    private String ordonnateur;
    private String fMagasinier;
    private String fCpm;
    private String fOrdonnateur;

    List<bf.gov.gcob.medaille.model.reportdto.LigneSortieDTO> lignesSorties;
}
