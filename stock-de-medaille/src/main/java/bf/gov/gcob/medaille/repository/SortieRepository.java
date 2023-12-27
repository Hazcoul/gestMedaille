package bf.gov.gcob.medaille.repository;
import bf.gov.gcob.medaille.model.enums.EMotifSortie;
import org.springframework.data.jpa.repository.JpaRepository;
import bf.gov.gcob.medaille.model.entities.Sortie;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SortieRepository extends JpaRepository<Sortie, Long> {
	
	Sortie findFirstByOrderByIdSortieDesc();
	
	@Query("select s from Sortie s where YEAR(s.dateSortie)= :annee and s.motifSortie= :motif")
	List<Sortie> findSortieByDecoByYear(int annee, EMotifSortie motif);
}
