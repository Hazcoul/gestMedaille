package bf.gov.gcob.medaille.repository;

import bf.gov.gcob.medaille.model.entities.Magasin;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MagasinRepository extends JpaRepository<Magasin, Long> {

    List<Magasin> findByDepotIdDepot(Long idDepot);
}
