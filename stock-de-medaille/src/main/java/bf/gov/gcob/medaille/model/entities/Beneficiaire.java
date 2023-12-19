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
@Table(name = "beneficiaires")
public class Beneficiaire extends AbstractBaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_beneficiaire", nullable = false, unique = true)
    private Long         idBeneficiaire;
    private String       sigle;
    @Column(name = "raison_sociale", nullable = false, unique = true)
    private String       raisonSociale;
    @Column(name = "tel_fix", nullable = true, unique = true)
    private String       telephoneFix;
    @Column(name = "tel_mobile", nullable = true, unique = true)
    private String       telephoneMobile;
    private String       email;
    private String       adresse;
}
