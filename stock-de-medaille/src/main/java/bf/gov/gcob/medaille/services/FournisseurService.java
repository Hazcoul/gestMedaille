package bf.gov.gcob.medaille.services;

import bf.gov.gcob.medaille.model.dto.FournisseurDTO;
import bf.gov.gcob.medaille.model.entities.Fournisseur;

import java.util.List;

public interface FournisseurService {
    FournisseurDTO create(FournisseurDTO fournisseurDto);
    List<FournisseurDTO> find();
    FournisseurDTO update(FournisseurDTO fournisseurDto);
    void delete(Long idFournisseur);
}
