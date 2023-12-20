package bf.gov.gcob.medaille.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bf.gov.gcob.medaille.model.entities.Detenteur;

public interface DetenteurRepository extends JpaRepository<Detenteur, Long> {

}
