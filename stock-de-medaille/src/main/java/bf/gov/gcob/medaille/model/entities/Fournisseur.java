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
@Table(name="fournisseurs")
public class Fournisseur extends AbstractBaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fournisseur", nullable = false, unique = true)
    private Long         idFournisseur;
    private String       sigle;
    @Column(name = "raison_sociale", nullable = false, unique = true)
    private String       libelle;
    @Column(name = "tel_fix", nullable = false, unique = true)
    private String       telephoneFix;
    @Column(name = "tel_mobile", nullable = true, unique = true)
    private String       telephoneMobile;
    private String       email;
    private String       adresse;
    @Column(name = "numero_ifu", nullable = false, unique = true)
    private String       numeroIfu;
    @Column(name = "nom_prenom", nullable = true)
    private String       nomCompletPersonneRessource;
    @Column(name = "tel_representant", nullable = true)
    private String       telephonePersonneRessource;
}
