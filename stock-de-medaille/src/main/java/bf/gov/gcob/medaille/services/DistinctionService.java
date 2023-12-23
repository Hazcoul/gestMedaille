package bf.gov.gcob.medaille.services;

import bf.gov.gcob.medaille.model.dto.DistinctionDTO;

import java.util.List;

public interface DistinctionService {
    DistinctionDTO create(DistinctionDTO distinctionDTO);
    List<DistinctionDTO> findAll();
    DistinctionDTO update(DistinctionDTO distinctionDTO);
    void delete(Long idDistinction);
}
