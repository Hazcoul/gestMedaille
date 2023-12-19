package bf.gov.gcob.medaille.model.entities;

import bf.gov.gcob.medaille.model.AbstractBaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

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
    private Long		idSortie;
    private Date dateSortie;
    @Column(name = "motif_sortie", nullable = false, unique=false)
    private String		motifSortie;
    @Column(name = "valider_le", nullable = true)
    private Date          validerLe;
    @Column(name = "valider_par", nullable = true, unique=false)
    private String        validerPar;
    @Column(name = "observation", nullable = true)
    private String		description;

    @ManyToOne
    @JoinColumn(name="ordonnateur_id")
    private Ordonnateur ordonnateur;
    @ManyToOne
    @JoinColumn(name="beneficiaire_id")
    private Beneficiaire beneficiaire;
    @ManyToOne
    @JoinColumn(name="detenteur_id")
    private Detenteur detenteur;
    @ManyToOne
    @JoinColumn(name="magasin_id")
    private Magasin magasin;

	@OneToMany(mappedBy = "sorties")
    List<LigneSortie> ligneSorties;
}
