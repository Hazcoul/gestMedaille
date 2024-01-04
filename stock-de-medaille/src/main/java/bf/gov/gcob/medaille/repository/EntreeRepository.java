package bf.gov.gcob.medaille.repository;

import bf.gov.gcob.medaille.model.entities.Entree;
import java.util.List;

import bf.gov.gcob.medaille.model.entities.LigneEntree;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntreeRepository extends JpaRepository<Entree, Long> {

    Entree findFirstByOrderByIdEntreeDesc();

    /*
	Liste des entrées de médaille par an
     */
    @Query("select e from Entree e where YEAR(e.dateEntree)= :annee")
    List<Entree> findEntreeByYear(int annee);

    @Query("select e from Entree e where (:fournisseur is null  or e.fournisseur.idFournisseur=:fournisseur)  and (:annee is null or YEAR(e.dateEntree)= :annee) ")
    Page<Entree> findByCriteria(@Param("annee") Integer annee, @Param("fournisseur") Long fournisseur, Pageable pageable);

    @Query("select e from LigneEntree e where e.entree.idEntree=:idEntree")
    List<LigneEntree> findAllLigneByEntree(@Param("idEntree") Long idEntree);
}
