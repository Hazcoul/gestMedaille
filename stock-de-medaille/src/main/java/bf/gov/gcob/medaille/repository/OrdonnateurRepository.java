package bf.gov.gcob.medaille.repository;

import bf.gov.gcob.medaille.model.entities.Ordonnateur;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdonnateurRepository extends JpaRepository<Ordonnateur, Long> {

    Optional<Ordonnateur> findByActuelTrue();
}
