package bf.gov.gcob.medaille.model.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class FournisseurDto{
    private Long         idFournisseur;
    private String       sigle;
    private String       libelle;
    private String       numeroIfu;
    private String       telephoneFix;
    private String       telephoneMobile;
    private String       email;
    private String       adresse;
    private String       nomCompletPersonneRessource;
    private String       telephonePersonneRessource;

}
