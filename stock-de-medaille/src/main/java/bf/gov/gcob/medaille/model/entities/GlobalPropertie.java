package bf.gov.gcob.medaille.model.entities;

import bf.gov.gcob.medaille.model.AbstractBaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "global_properties", uniqueConstraints = @UniqueConstraint(columnNames = {"type_mvt", "exercice_budgetaire"}, name = "UNIQUE_TYPE_MVT_EXERCICE_BUDGETAIRE"))
public class GlobalPropertie extends AbstractBaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_global_propertie", nullable = false, unique = true)
    private Long        idGlobalPropertie;
    @Column(name = "exercice_budgetaire", nullable = false, unique = true)
    private Integer      exerciceBudgetaire;
    @Column(name = "type_mvt", nullable = false)
    private Character typeMvt;
    @Column(name = "entree_count", nullable = false)
    private Integer entreeCount = 0;
    @Column(name = "sortie_count", nullable = false)
    private Integer sortieCount = 0;
}
