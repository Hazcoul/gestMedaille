package bf.gov.gcob.medaille.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bf.gov.gcob.medaille.model.entities.Beneficiaire;

public interface BeneficiaireRepository extends JpaRepository<Beneficiaire, Long> {

}
