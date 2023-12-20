package bf.gov.gcob.medaille.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bf.gov.gcob.medaille.model.entities.Medaille;

public interface MedailleRepository extends JpaRepository<Medaille, Long> {

}
