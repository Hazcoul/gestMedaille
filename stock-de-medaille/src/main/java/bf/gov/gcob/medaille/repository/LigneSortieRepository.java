package bf.gov.gcob.medaille.repository;

import bf.gov.gcob.medaille.model.entities.LigneSortie;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneSortieRepository extends JpaRepository<LigneSortie, Long> {

    List<LigneSortie> findBySortieIdSortie(Long idSortie);

    List<LigneSortie> findByMedailleIdMedaille(Long idMedaille);
}
