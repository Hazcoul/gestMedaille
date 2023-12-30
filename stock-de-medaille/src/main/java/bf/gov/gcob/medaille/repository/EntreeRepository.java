package bf.gov.gcob.medaille.repository;

import bf.gov.gcob.medaille.model.entities.Entree;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EntreeRepository extends JpaRepository<Entree, Long> {

    Entree findFirstByOrderByIdEntreeDesc();

    /*
	Liste des entrées de médaille par an
     */
    @Query("select e from Entree e where YEAR(e.dateEntree)= :annee")
    List<Entree> findEntreeByYear(int annee);
}
