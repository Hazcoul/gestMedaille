package bf.gov.gcob.medaille.model.dto;

import bf.gov.gcob.medaille.model.AbstractBaseDTO;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdonnateurDTO extends AbstractBaseDTO {

    private Long idOrdonnateur;
    @NotNull
    private String matricule;
    private String civilite;
    @NotNull
    private String nom;
    @NotNull
    private String prenom;
    @NotNull
    private String fonction;
    @NotNull
    private String telephone;
    private String email;
    private String gradeMilitaire;
    private String titreHonorifique;
    private boolean actuel;//pour savoir qui est ordonnateur actif et qui ne l'est plus. on ne peut pas avoir plusieurs ordonnateurs actuel à la fois
    private Date debutMandat; //a partir de quant le nouvel ordonnateur exerce son autorité
    private Date finMandat;
}
