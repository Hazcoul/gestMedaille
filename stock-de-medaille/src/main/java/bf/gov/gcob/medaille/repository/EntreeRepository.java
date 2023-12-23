package bf.gov.gcob.medaille.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bf.gov.gcob.medaille.model.entities.Entree;

public interface EntreeRepository extends JpaRepository<Entree, Long> {
	
	Entree findFirstByOrderByIdEntreeDesc();

}
