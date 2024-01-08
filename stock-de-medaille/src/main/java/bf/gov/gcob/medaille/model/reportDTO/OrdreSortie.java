package bf.gov.gcob.medaille.model.reportDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.InputStream;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrdreSortie {
    private String titre;
    private InputStream logo;
    private List<LigneOS> lignesSorties;
    private String date;
    private String magasin;
    private String detenteur;
    private String beneficiaire;
    private String magasinier;
    private String cpm;
    private String ordonnateur;
}
