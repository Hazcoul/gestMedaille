package bf.gov.gcob.medaille.repository;

import bf.gov.gcob.medaille.model.entities.Medaille;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedailleRepository extends JpaRepository<Medaille, Long> {

    List<Medaille> findByHorsUsage(boolean request);
}
