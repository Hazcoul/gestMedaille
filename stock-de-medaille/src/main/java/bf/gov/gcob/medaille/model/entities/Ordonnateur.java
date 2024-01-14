package bf.gov.gcob.medaille.model.entities;

import bf.gov.gcob.medaille.model.AbstractBaseEntity;
import bf.gov.gcob.medaille.model.enums.ECivilite;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ordonnateurs")
public class Ordonnateur extends AbstractBaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ordonnateur", nullable = false, unique = true)
    private Long idOrdonnateur;
    @Column(name = "matricule", nullable = false, unique = true)
    private String matricule;
    //enumeration {Monsieur, Madame}
    @Enumerated(EnumType.ORDINAL)
    private ECivilite civilite;
    @Column(name = "nom", nullable = false)
    private String nom;
    @Column(name = "prenom", nullable = false)
    private String prenom;
    @Column(name = "fonction", nullable = false)
    private String fonction;
    @Column(name = "telephone", nullable = false, unique = true)
    private String telephone;
    @Column(name = "email", unique = true)
    private String email;
    private String gradeMilitaire;
    private String titreHonorifique;
    private boolean actuel = false;//pour savoir qui est ordonnateur actif et qui ne l'est plus. on ne peut pas avoir plusieurs ordonnateurs actuel à la fois
    @Column(updatable = false)
    private Date debutMandat; //a partir de quant le nouvel ordonnateur exerce son autorité
    private Date finMandat;
}
