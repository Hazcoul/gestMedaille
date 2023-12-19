package bf.gov.gcob.medaille.model.entities;

import bf.gov.gcob.medaille.model.AbstractBaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="entrees")
public class Entree extends AbstractBaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entree", nullable = false, unique = true)
    private Long		idEntree;
    @Column(name = "date_entree", nullable = false, length = 19)
    private Date dateEntree;
    @Column(name = "numero_cmd", nullable = false, unique = true)
    private String        numeroCmd;
    @Column(name = "valider_le", nullable = true)
    private Date          validerLe;
    @Column(name = "valider_par", nullable = true, unique=false)
    private String        validerPar;
    @Column(name = "observation", nullable = true, unique=false)
    private String        observation;
    @Column(name = "date_reception", nullable = true, length = 19)
    private Date          dateReception;
    @Column(name = "exercice_budgetaire", nullable = true, length = 4)
    private Integer       exerciceBudgetaire;
    //cmd, retour, autre
    private String        acquisition;
    //valider ou pas
    //private EStatusEntree status;

    @ManyToOne
    @JoinColumn(name="fournisseur_id")
    private Fournisseur fournisseur;

    @ManyToOne
    @JoinColumn(name="magasin_id")
    private Magasin magasin;

	@OneToMany(mappedBy = "entree")
    List<LigneEntree> ligneentrees;
}
