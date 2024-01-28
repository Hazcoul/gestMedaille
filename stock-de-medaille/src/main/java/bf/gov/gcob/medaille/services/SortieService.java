/**
 *
 */
package bf.gov.gcob.medaille.services;

import java.util.List;

import org.springframework.core.io.Resource;

import bf.gov.gcob.medaille.model.dto.FilterSortieDto;
import bf.gov.gcob.medaille.model.dto.LigneImpressionSortiePeriodeDTO;
import bf.gov.gcob.medaille.model.dto.SortieDTO;
import bf.gov.gcob.medaille.model.entities.Utilisateur;

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

    SortieDTO validerSortie(Long idSortie, Utilisateur user);
    
    SortieDTO rejeter(Long idSortie, String comment);
}
