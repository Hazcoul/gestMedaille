package bf.gov.gcob.medaille.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bf.gov.gcob.medaille.model.entities.Ordonnateur;

public interface OrdonnateurRepository extends JpaRepository<Ordonnateur, Long> {

}
