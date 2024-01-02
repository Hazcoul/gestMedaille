/**
 * 
 */
package bf.gov.gcob.medaille.services;

import java.util.List;

import bf.gov.gcob.medaille.model.dto.EntreeDTO;
import bf.gov.gcob.medaille.model.dto.FilterEntreeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 
 */
public interface EntreeService {
	
	EntreeDTO save(EntreeDTO entreeDTO);
	List<EntreeDTO> findAll();
	List<EntreeDTO> findByAn(int annee);
	EntreeDTO findOne(Long id);
	void delete(Long id);

	Page<EntreeDTO> findAllByCriteria(FilterEntreeDto filterEntreeDto, Pageable pageable);
}
