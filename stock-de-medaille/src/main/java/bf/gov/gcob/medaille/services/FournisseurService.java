package bf.gov.gcob.medaille.services;

import java.util.List;

import bf.gov.gcob.medaille.model.dto.FournisseurDTO;

public interface FournisseurService {
    FournisseurDTO create(FournisseurDTO fournisseurDto);
    List<FournisseurDTO> find();
    FournisseurDTO update(FournisseurDTO fournisseurDto);
    void delete(Long idFournisseur);
}
