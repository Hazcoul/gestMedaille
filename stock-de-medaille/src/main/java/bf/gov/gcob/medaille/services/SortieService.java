/**
 * 
 */
package bf.gov.gcob.medaille.services;

import java.util.List;

import bf.gov.gcob.medaille.model.dto.FilterSortieDto;
import bf.gov.gcob.medaille.model.dto.LigneImpressionSortiePeriodeDTO;
import bf.gov.gcob.medaille.model.dto.SortieDTO;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 
 */
public interface SortieService {

	SortieDTO save(SortieDTO sortieDTO);
	List<SortieDTO> findAll();
	List<SortieDTO> findByDecoByAn(int annee);
	SortieDTO findOne(Long id);
	void delete(Long id);

    Resource getLigneSortieBySortie(Long id);
    

	Page<SortieDTO> findAllByCriteria(FilterSortieDto filterSortieDto, Pageable pageable);
	Page<LigneImpressionSortiePeriodeDTO> findAllSortiesByPeriode(FilterSortieDto filterSortieDto, Pageable pageable);

	Resource getLigneSortieByPeriode(FilterSortieDto filterSortieDto);
}
