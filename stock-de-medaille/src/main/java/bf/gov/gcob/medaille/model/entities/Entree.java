package bf.gov.gcob.medaille.model.entities;

import bf.gov.gcob.medaille.model.AbstractBaseEntity;
import bf.gov.gcob.medaille.model.enums.EAcquisition;
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

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "entrees")
public class Entree extends AbstractBaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entree", nullable = false, unique = true)
    private Long idEntree;
    @Column(name = "date_entree", nullable = false, length = 19)
    private Date dateEntree;
    @Column(name = "numero_cmd", nullable = false, unique = true)
    private String numeroCmd;
    @Column(name = "valider_le", nullable = true)
    private Date validerLe;
    @Column(name = "valider_par", nullable = true, unique = false)
    private String validerPar;
    @Column(name = "observation", nullable = true, unique = false)
    private String observation;
    @Column(name = "date_reception", nullable = true, length = 19)
    private Date dateReception;
    @Column(name = "exercice_budgetaire", nullable = true, length = 4)
    private Integer exerciceBudgetaire;
    //cmd, retour, autre
    @Enumerated(EnumType.ORDINAL)
    private EAcquisition acquisition;
    //valider ou pas
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private EMvtStatus status;

    @ManyToOne
    @JoinColumn(name = "fournisseur_id")
    private Fournisseur fournisseur;

    @ManyToOne
    @JoinColumn(name = "magasin_id")
    private Magasin magasin;

    @OneToMany(mappedBy = "entree")
    private Set<LigneEntree> ligneentrees;
}
