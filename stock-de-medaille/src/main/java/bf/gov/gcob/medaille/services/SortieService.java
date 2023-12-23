/**
 * 
 */
package bf.gov.gcob.medaille.services;

import java.util.List;
import java.util.Optional;

import bf.gov.gcob.medaille.model.dto.SortieDTO;

/**
 * 
 */
public interface SortieService {

	SortieDTO save(SortieDTO sortieDTO);
	List<SortieDTO> findAll();
	Optional<SortieDTO> findOne(Long id);
	void delete(Long id);
}
