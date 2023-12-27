/**
 * 
 */
package bf.gov.gcob.medaille.services;

import java.util.List;
import java.util.Optional;

import bf.gov.gcob.medaille.model.dto.EntreeDTO;
import bf.gov.gcob.medaille.model.dto.SortieDTO;
import bf.gov.gcob.medaille.model.enums.EMotifSortie;

/**
 * 
 */
public interface SortieService {

	SortieDTO save(SortieDTO sortieDTO);
	List<SortieDTO> findAll();
	List<SortieDTO> findByDecoByAn(int annee);
	Optional<SortieDTO> findOne(Long id);
	void delete(Long id);
}
