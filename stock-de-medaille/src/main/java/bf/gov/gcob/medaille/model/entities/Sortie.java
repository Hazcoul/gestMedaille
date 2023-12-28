package bf.gov.gcob.medaille.model.entities;

import bf.gov.gcob.medaille.model.AbstractBaseEntity;
import bf.gov.gcob.medaille.model.enums.EMotifSortie;
import bf.gov.gcob.medaille.model.enums.EMvtStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sorties")
public class Sortie extends AbstractBaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sortie", nullable = false, unique = true)
    private Long idSortie;
    private Date dateSortie;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "motif_sortie", nullable = false, unique = false)
    private EMotifSortie motifSortie;
    @Column(name = "valider_le")
    private Date validerLe;
    @Column(name = "valider_par", unique = false)
    private String validerPar;
    @Column(name = "observation")
    private String description;
    @ManyToOne
    @JoinColumn(name = "ordonnateur_id")
    private Ordonnateur ordonnateur;
    @ManyToOne
    @JoinColumn(name = "beneficiaire_id")
    private Beneficiaire beneficiaire;
    @ManyToOne
    @JoinColumn(name = "detenteur_id")
    private Detenteur detenteur;
    @ManyToOne
    @JoinColumn(name = "magasin_id")
    private Magasin magasin;

    @OneToMany(mappedBy = "sortie")
    private Set<LigneSortie> ligneSorties;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private EMvtStatus status;
    @Column(name = "numero_sortie", nullable = false, length = 20)
    private String numeroSortie;
}
