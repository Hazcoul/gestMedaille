package bf.gov.gcob.medaille.repository;

import bf.gov.gcob.medaille.model.entities.Detenteur;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetenteurRepository extends JpaRepository<Detenteur, Long> {

    List<Detenteur> findByBeneficiaireIdBeneficiaire(Long idBeneficiaire);

}
