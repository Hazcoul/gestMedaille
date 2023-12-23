package bf.gov.gcob.medaille.services;

import bf.gov.gcob.medaille.model.dto.OrdonnateurDTO;

import java.util.List;

public interface OrdonnateurService {
    OrdonnateurDTO create(OrdonnateurDTO ordonnateurDTO);
    List<OrdonnateurDTO> findAll();
    OrdonnateurDTO update(OrdonnateurDTO ordonnateurDTO);
    void delete(Long idOrdonnateur);
}
