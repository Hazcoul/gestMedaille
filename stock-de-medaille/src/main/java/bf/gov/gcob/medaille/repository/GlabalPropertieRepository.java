package bf.gov.gcob.medaille.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bf.gov.gcob.medaille.model.entities.GlobalPropertie;

@Repository
public interface GlabalPropertieRepository extends JpaRepository<GlobalPropertie, Long> {

	Optional<GlobalPropertie> findByTypeMvtAndExerciceBudgetaire(Character type, Integer exercice);
}
