package bf.gov.gcob.medaille.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bf.gov.gcob.medaille.model.entities.Sortie;

public interface SortieRepository extends JpaRepository<Sortie, Long> {

}
