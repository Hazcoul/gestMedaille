package bf.gov.gcob.medaille.repository;

import bf.gov.gcob.medaille.model.entities.PieceJointe;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PieceJointeRepository extends JpaRepository<PieceJointe, Long> {

    List<PieceJointe> findByEntreeIdEntree(Long idEntree);
}
