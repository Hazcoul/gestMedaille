package bf.gov.gcob.medaille.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bf.gov.gcob.medaille.model.entities.Entree;
import bf.gov.gcob.medaille.model.entities.LigneEntree;
import bf.gov.gcob.medaille.model.enums.EMvtStatus;

@Repository
public interface EntreeRepository extends JpaRepository<Entree, Long> {

    Entree findFirstByOrderByIdEntreeDesc();

    /*
	Liste des entrées de médaille par an
     */
    @Query("select e from Entree e where YEAR(e.dateEntree)= :annee")
    List<Entree> findEntreeByYear(int annee);

    @Query("select e from Entree e where (:fournisseur is null  or e.fournisseur.idFournisseur=:fournisseur)  and (:annee is null or YEAR(e.dateEntree)= :annee) ")
    List<Entree> findByCriteria(@Param("annee") Integer annee, @Param("fournisseur") Long fournisseur);

    @Query("select e from LigneEntree e where e.entree.idEntree=:idEntree")
    List<LigneEntree> findAllLigneByEntree(@Param("idEntree") Long idEntree);

    Optional<Entree> findByIdEntreeAndStatus(Long idEntree, EMvtStatus status);

    List<Entree> findByMagasinIdMagasin(Long idMagasin);

    List<Entree> findByFournisseurIdFournisseur(Long idFournisseur);
    
    Collection<Entree> findAllByOrderByLastModifiedDateDesc();
}
