package bf.gov.gcob.medaille.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bf.gov.gcob.medaille.model.entities.Beneficiaire;

@Repository
public interface BeneficiaireRepository extends JpaRepository<Beneficiaire, Long> {

}
