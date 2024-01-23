package bf.gov.gcob.medaille.repository;

import bf.gov.gcob.medaille.model.entities.Medaille;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedailleRepository extends JpaRepository<Medaille, Long> {

    List<Medaille> findByGradeIdGrade(Long idGrade);

    List<Medaille> findByDistinctionIdDistinction(Long idDistinction);
}
