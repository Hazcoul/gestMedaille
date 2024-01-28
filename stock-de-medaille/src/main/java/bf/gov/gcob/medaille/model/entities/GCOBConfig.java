package bf.gov.gcob.medaille.model.entities;

import bf.gov.gcob.medaille.model.AbstractBaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "gcob_configs")
public class GCOBConfig extends AbstractBaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_gcob_config", nullable = false, unique = true)
    private Long        idGCOBConfig;
    @Column(name = "code_institution", nullable = false, unique = true)
    private String      codeInstitution;
    @Column(name = "code_budgetaire", nullable = false, unique = true)
    private String      codeBudgetaire;
    private Boolean status = Boolean.TRUE;
    
    public GCOBConfig(String codeInst, String codeBudg, String author) {
    	this.codeInstitution = codeInst;
    	this.codeBudgetaire = codeBudg;
    	this.setCreatedBy(author);
    }
}
