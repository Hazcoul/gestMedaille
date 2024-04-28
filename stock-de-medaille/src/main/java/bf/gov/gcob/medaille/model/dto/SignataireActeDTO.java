/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.gov.gcob.medaille.model.dto;

import bf.gov.gcob.medaille.model.AbstractBaseDTO;
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
@NoArgsConstructor
@AllArgsConstructor
public class SignataireActeDTO extends AbstractBaseDTO {

    private Long idSignataire;

    private String natureActe;

    private String fonctionSignataire;

    private String nomComplet;
    private String departement;

    private String titreHonorifique;

    private Boolean actif;
}
