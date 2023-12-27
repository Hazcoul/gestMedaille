package bf.gov.gcob.medaille.model.dto;

import bf.gov.gcob.medaille.model.AbstractBaseDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FournisseurDTO extends AbstractBaseDTO {

	private Long         idFournisseur;
    private String       sigle;
    @NotNull
    private String       libelle;
    @NotNull
    private String       telephoneFix;
    private String       telephoneMobile;
    private String       email;
    private String       adresse;
    @NotNull
    private String       numeroIfu;
    private String       nomCompletPersonneRessource;
    private String       telephonePersonneRessource;
}
