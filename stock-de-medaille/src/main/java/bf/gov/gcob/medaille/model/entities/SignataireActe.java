/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.gov.gcob.medaille.model.entities;

import bf.gov.gcob.medaille.model.AbstractBaseEntity;
import bf.gov.gcob.medaille.model.enums.EFonctionSignataire;
import bf.gov.gcob.medaille.model.enums.ENatureActe;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * permet de parametrer les signataires des actes/doc generés par l'appli
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "signataire_acte")
public class SignataireActe extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_beneficiaire", nullable = false, unique = true)
    private Long idSignataire;

    @Enumerated(EnumType.ORDINAL)
    private ENatureActe natureActe;

    @Enumerated(EnumType.ORDINAL)
    private EFonctionSignataire fonctionSignataire;

    private String nomComplet;

    private String titreHonorifique;

    private Boolean actif;

    private Date debutMandat;

    private Date finMandat;

}
