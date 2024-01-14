/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.gov.gcob.medaille.model.reportdto;

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
public class LigneEntreeDTO {

    private String no;

    private String code;

    private String typeBien; //Fongible pour les medailles

    private String designation;

    private String quantite;

    private String prixUnitaire;

    private String montant;

    private String observations;

}
