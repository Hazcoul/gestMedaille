package bf.gov.gcob.medaille.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bf.gov.gcob.medaille.model.entities.LigneSortie;

public interface LigneSortieRepository extends JpaRepository<LigneSortie, Long> {

}
