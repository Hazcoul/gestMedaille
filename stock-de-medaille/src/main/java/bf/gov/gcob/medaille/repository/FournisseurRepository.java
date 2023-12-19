package bf.gov.gcob.medaille.repository;

import bf.gov.gcob.medaille.model.entities.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {
}
