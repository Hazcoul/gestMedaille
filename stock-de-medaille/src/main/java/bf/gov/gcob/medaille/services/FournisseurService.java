package bf.gov.gcob.medaille.services;

import bf.gov.gcob.medaille.model.dto.FournisseurDto;
import bf.gov.gcob.medaille.model.entities.Fournisseur;

import java.util.List;

public interface FournisseurService {
    FournisseurDto create(FournisseurDto fournisseurDto);
    List<FournisseurDto> find();
    FournisseurDto update(FournisseurDto fournisseurDto);
    void delete(Long idFournisseur);
}
