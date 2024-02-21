/**
 *
 */
package bf.gov.gcob.medaille.services;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.codec.multipart.FilePart;

import bf.gov.gcob.medaille.model.dto.FilterSortieDto;
import bf.gov.gcob.medaille.model.dto.LigneImpressionSortiePeriodeDTO;
import bf.gov.gcob.medaille.model.dto.PieceJointeDTO;
import bf.gov.gcob.medaille.model.dto.SortieDTO;
import bf.gov.gcob.medaille.model.entities.Utilisateur;

/**
 *
 */
public interface SortieService {

    SortieDTO save(SortieDTO sortieDTO, List<PieceJointeDTO> pjDTOs, List<FilePart> pFiles);

    List<SortieDTO> findAll();

    List<SortieDTO> findByDecoByAn(int annee);

    SortieDTO findOne(Long id);

    void delete(Long id);
    
    void deleteLine(Long id, Long idLine);

    Resource getLigneSortieBySortie(Long id);

    List<SortieDTO> findAllByCriteria(FilterSortieDto filterSortieDto);

    List<LigneImpressionSortiePeriodeDTO> findAllSortiesByPeriode(FilterSortieDto filterSortieDto);

    Resource getLigneSortieByPeriode(FilterSortieDto filterSortieDto);

    SortieDTO validerSortie(Long idSortie, Utilisateur user);
    
    SortieDTO rejeter(Long idSortie, String comment);
}
