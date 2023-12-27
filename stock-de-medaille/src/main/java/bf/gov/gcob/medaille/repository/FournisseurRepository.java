package bf.gov.gcob.medaille.repository;

import bf.gov.gcob.medaille.model.entities.Entree;
import bf.gov.gcob.medaille.model.entities.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {

}
