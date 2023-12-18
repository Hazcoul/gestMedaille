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
@Table(name = "ligne_sorties")
public class LigneSortie extends AbstractBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lignesortie", nullable = false, unique = true)
    private Long		idLigneSortie;
    @Column(name = "quantite_sortie", nullable = false)
    private Integer		quantiteLigne;

    @Column(name = "cloture_sortie", nullable = true)
    private boolean		isCloseSortie;

    @ManyToOne
    @JoinColumn(name = "sortie_id")
    private Sortie sorties;

    @ManyToOne
    @JoinColumn(name="medaille_id")
    private Medaille medailles;
}
