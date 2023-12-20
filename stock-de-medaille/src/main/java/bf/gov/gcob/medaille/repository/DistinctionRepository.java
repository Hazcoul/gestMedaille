package bf.gov.gcob.medaille.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bf.gov.gcob.medaille.model.entities.Distinction;

public interface DistinctionRepository extends JpaRepository<Distinction, Long> {

}
