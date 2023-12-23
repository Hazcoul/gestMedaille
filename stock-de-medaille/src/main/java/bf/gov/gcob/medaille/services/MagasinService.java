package bf.gov.gcob.medaille.services;


import bf.gov.gcob.medaille.model.dto.MagasinDTO;

import java.util.List;

public interface MagasinService {
    MagasinDTO create(MagasinDTO magasinDTO);
    List<MagasinDTO> findAll();
    MagasinDTO update(MagasinDTO magasinDTO);
    void delete(Long idMagasin);
}
