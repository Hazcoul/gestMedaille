/**
 * 
 */
package bf.gov.gcob.medaille.services;

import java.util.List;
import java.util.Optional;

import bf.gov.gcob.medaille.model.dto.EntreeDTO;
import bf.gov.gcob.medaille.model.dto.FournisseurDTO;

/**
 * 
 */
public interface EntreeService {
	
	EntreeDTO save(EntreeDTO entreeDTO);
	List<EntreeDTO> findAll();
	List<EntreeDTO> findByAn(int annee);
	Optional<EntreeDTO> findOne(Long id);
	void delete(Long id);

}
