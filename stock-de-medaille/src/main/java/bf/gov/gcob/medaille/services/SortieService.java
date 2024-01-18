/**
 *
 */
package bf.gov.gcob.medaille.services;

import bf.gov.gcob.medaille.model.dto.FilterSortieDto;
import bf.gov.gcob.medaille.model.dto.LigneImpressionSortiePeriodeDTO;
import bf.gov.gcob.medaille.model.dto.SortieDTO;
import java.util.List;
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

    List<SortieDTO> findAllByCriteria(FilterSortieDto filterSortieDto);

    List<LigneImpressionSortiePeriodeDTO> findAllSortiesByPeriode(FilterSortieDto filterSortieDto);

    Resource getLigneSortieByPeriode(FilterSortieDto filterSortieDto);

    SortieDTO validerSortie(Long idSortie);
}
