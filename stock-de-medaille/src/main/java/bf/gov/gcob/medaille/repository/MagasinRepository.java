package bf.gov.gcob.medaille.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bf.gov.gcob.medaille.model.entities.Magasin;

public interface MagasinRepository extends JpaRepository<Magasin, Long> {

}
