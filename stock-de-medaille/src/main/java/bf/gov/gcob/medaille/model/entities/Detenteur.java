package bf.gov.gcob.medaille.model.entities;

import bf.gov.gcob.medaille.model.AbstractBaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "detenteurs")
public class Detenteur extends AbstractBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_detenteur", nullable = false, unique=true)
    private Long 	idDetenteur;
    @Column(name="matricule", nullable=false, unique=true)
    private String 	matricule;
    //enumeration {Monsieur, Madame}
    private String 	civilite;
    @Column(name="nom", nullable=false)
    private String 	nom;
    @Column(name="prenom", nullable=false)
    private String 	prenom;
    @Column(name="fonction", nullable=false)
    private String 	fonction;
    @Column(name="telephone", nullable=false, unique=true)
    private String 	telephone;
    @Column(name="email", nullable=true, unique=true )
    private String 	email;
}
