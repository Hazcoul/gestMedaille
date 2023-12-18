package bf.gov.gcob.medaille.model.entities;

import bf.gov.gcob.medaille.model.AbstractBaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="distinctions")
public class Distinction extends AbstractBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_distinction", nullable = false, unique = true)
    private Long		idDistinction;
    @Column(name = "code", nullable = false, unique = true, length = 1)
    private String		code;
    private String		abreviation;
    private String		libelle;
    @Column(name = "categorie_distinction", nullable = false)//ça sera une enumeration (Ordres Nationaux, Ordres Specifiques, Médailles)
    private String		categoryDistinction;
    private String		referenceDecret;
    @Column(name = "date_creation", nullable = true)
    private Date dateDecretCreation;
    private String		description;
}
