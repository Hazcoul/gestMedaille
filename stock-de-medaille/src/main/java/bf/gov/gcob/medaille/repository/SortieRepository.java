package bf.gov.gcob.medaille.repository;

import bf.gov.gcob.medaille.model.dto.LigneImpressionSortiePeriodeDTO;
import bf.gov.gcob.medaille.model.entities.LigneSortie;
import bf.gov.gcob.medaille.model.entities.Sortie;
import bf.gov.gcob.medaille.model.enums.EMotifSortie;
import bf.gov.gcob.medaille.model.enums.EMvtStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SortieRepository extends JpaRepository<Sortie, Long> {

    Sortie findFirstByOrderByIdSortieDesc();

    @Query("select s from Sortie s where YEAR(s.dateSortie)= :annee and s.motifSortie= :motif")
    List<Sortie> findSortieByDecoByYear(int annee, EMotifSortie motif);

    @Query("select s from Sortie s where (:ordonnateur is null  or s.ordonnateur.idOrdonnateur=:ordonnateur)  and (:annee is null or YEAR(s.dateSortie)= :annee) and (:detenteur is null  or s.detenteur.idDetenteur=:detenteur) and (:beneficiaire is null  or s.beneficiaire.idBeneficiaire=:beneficiaire) and (:motifSortie is null or s.motifSortie=:motifSortie)")
    Page<Sortie> findByCriteria(@Param("annee") Integer annee, @Param("motifSortie") EMotifSortie motifSortie, @Param("ordonnateur") Long ordonnateur, @Param("detenteur") Long detenteur, @Param("beneficiaire") Long beneficiaire, Pageable pageable);

    @Query("select ls from LigneSortie ls where ls.sortie.idSortie=:idSortie")
    List<LigneSortie> findAllLigneBySortie(@Param("idSortie") Long idSortie);

    @Query("SELECT new bf.gov.gcob.medaille.model.dto.LigneImpressionSortiePeriodeDTO(c.medaille.nomComplet,SUM(c.quantiteLigne),c.sortie.dateSortie) FROM LigneSortie AS c GROUP BY c.medaille.nomComplet,c.sortie.dateSortie,c.sortie.motifSortie having c.sortie.motifSortie=:motifSortie ")
    List<LigneImpressionSortiePeriodeDTO> countTotalMedailleByMedailleAndPeriode(@Param("motifSortie") EMotifSortie motifSortie);

    Optional<Sortie> findByIdSortieAndStatus(Long idSortie, EMvtStatus status);

    List<Sortie> findByMagasinIdMagasin(Long idMagasin);

    List<Sortie> findByBeneficiaireIdBeneficiaire(Long idBeneficiaire);

    List<Sortie> findByDetenteurIdDetenteur(Long idDetenteur);

    List<Sortie> findByOrdonnateurIdOrdonnateur(Long idOrdonnateur);
}
