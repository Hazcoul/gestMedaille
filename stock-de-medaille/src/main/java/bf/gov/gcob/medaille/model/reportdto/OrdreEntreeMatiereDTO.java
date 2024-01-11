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
 * ce report dto permet de construire le jeu de donnees pour exporter un ordre
 * d'entree de matiere
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdreEntreeMatiereDTO {

    private InputStream logo;

    private String exercice;

    private String titre;

    private String modeAcquisition;

    private String destination;

    private String fournisseur;

    private String typePiece;

    private String referencePiece;

    private List<LigneEntreeDTO> lignesEntrees;

}
