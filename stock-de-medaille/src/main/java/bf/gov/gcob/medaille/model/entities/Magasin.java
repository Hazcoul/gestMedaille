package bf.gov.gcob.medaille.model.entities;

import bf.gov.gcob.medaille.model.AbstractBaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="magasins")
public class Magasin extends AbstractBaseEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_magasin", nullable = false, unique = true)
    private Long        idMagasin;
    private String      nomMagasin;
    private Integer     capacite;
    private String      description;

    @ManyToOne
    @JoinColumn(name="depot_id")
    private Depot depots;
}
