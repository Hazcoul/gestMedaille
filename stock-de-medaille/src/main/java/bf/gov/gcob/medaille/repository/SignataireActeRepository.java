/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.gov.gcob.medaille.repository;

import bf.gov.gcob.medaille.model.entities.SignataireActe;
import bf.gov.gcob.medaille.model.enums.EFonctionSignataire;
import bf.gov.gcob.medaille.model.enums.ENatureActe;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface SignataireActeRepository extends JpaRepository<SignataireActe, Long> {

    Optional<SignataireActe> findByNatureActeAndFonctionSignataireAndActifTrue(ENatureActe nature, EFonctionSignataire fonction);

    Optional<SignataireActe> findByIdSignataireAndActifTrue(Long idSignataire);
}
