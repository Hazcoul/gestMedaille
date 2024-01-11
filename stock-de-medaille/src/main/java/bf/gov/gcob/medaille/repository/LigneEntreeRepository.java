package bf.gov.gcob.medaille.repository;

import bf.gov.gcob.medaille.model.entities.LigneEntree;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneEntreeRepository extends JpaRepository<LigneEntree, Long> {

    List<LigneEntree> findByEntreeIdEntree(Long idEntree);
}
