package bf.gov.gcob.medaille.model.entities;

import bf.gov.gcob.medaille.model.AbstractBaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="ligne_entrees")
public class LigneEntree extends AbstractBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ligneentree", nullable = false, unique = true)
    private Long idLigneEntree;
    @Column(name = "quantite_entree", nullable = false)
    private Integer quantiteLigne;
    @Column(name = "prix_unitaire", nullable = true)
    private Double prixUnitaire;
    private Double montantLigne;
    private boolean isCloseEntree;

    @ManyToOne
    @JoinColumn(name = "entree_id")
    private Entree entrees;

    @ManyToOne
    @JoinColumn(name = "medaille_id")
    private Medaille medailles;
}
