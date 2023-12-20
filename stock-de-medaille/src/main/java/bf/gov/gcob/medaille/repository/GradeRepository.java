package bf.gov.gcob.medaille.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bf.gov.gcob.medaille.model.entities.Grade;

public interface GradeRepository extends JpaRepository<Grade, Long> {

}
