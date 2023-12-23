package bf.gov.gcob.medaille.services;

import bf.gov.gcob.medaille.model.dto.DepotDTO;

import java.util.List;

public interface DepotService {
    DepotDTO create(DepotDTO depotDTO);
    List<DepotDTO> findAll();
    DepotDTO update(DepotDTO depotDTO);
    void delete(Long idDepot);
}
