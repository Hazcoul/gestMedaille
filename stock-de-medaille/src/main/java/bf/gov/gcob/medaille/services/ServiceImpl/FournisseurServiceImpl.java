package bf.gov.gcob.medaille.services.ServiceImpl;

import bf.gov.gcob.medaille.mapper.FournisseurMapper;
import bf.gov.gcob.medaille.model.dto.FournisseurDTO;
import bf.gov.gcob.medaille.model.entities.Entree;
import bf.gov.gcob.medaille.model.entities.Fournisseur;
import bf.gov.gcob.medaille.repository.EntreeRepository;
import bf.gov.gcob.medaille.repository.FournisseurRepository;
import bf.gov.gcob.medaille.services.FournisseurService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Slf4j
@Service
@AllArgsConstructor
public class FournisseurServiceImpl implements FournisseurService {

    private final FournisseurRepository fournisseurRepository;
    private final EntreeRepository entreeRepository;
    private final FournisseurMapper fournisseurMapper;

    @Override
    public FournisseurDTO create(FournisseurDTO fournisseurDto) {
        Fournisseur fournisseur = fournisseurMapper.buildFournisseur(fournisseurDto);
        fournisseur = fournisseurRepository.save(fournisseur);
        fournisseurDto = fournisseurMapper.buildFournisseurDto(fournisseur);
        return fournisseurDto;
    }

    @Override
    public List<FournisseurDTO> find() {

        List<FournisseurDTO> fournisseurs = fournisseurRepository.findAll()
                .stream()
                .map(fournisseurMapper::buildFournisseurDto)
                .collect(Collectors.toList());
        return fournisseurs;
    }

    @Override
    public FournisseurDTO findById(Long idFournisseur) {
        /* Fournisseur fournisseur=fournisseurRepository.findById(idFournisseur);
        FournisseurDTO fournisseurDTO=fournisseurMapper.buildFournisseurDto(fournisseur);
        return fournisseurDTO;*/
        return null;
    }

    @Override
    public FournisseurDTO update(FournisseurDTO fournisseurDto) {
        Fournisseur fournisseur = fournisseurRepository.findById(fournisseurDto.getIdFournisseur()).orElse(null);
        if (fournisseur == null) {
            throw new RuntimeException("Fournisseur Id " + fournisseurDto.getIdFournisseur() + " inexistant");
        }
        fournisseur = fournisseurMapper.buildFournisseur(fournisseurDto);
        fournisseur = fournisseurRepository.save(fournisseur);
        FournisseurDTO fournisseurNDto = fournisseurMapper.buildFournisseurDto(fournisseur);
        return fournisseurNDto;
    }

    @Override
    public void delete(Long idFournisseur) {
        log.info("Suppression du fournisseur {} ", idFournisseur);
        List<Entree> entrees = entreeRepository.findByFournisseurIdFournisseur(idFournisseur);
        if (entrees != null || !CollectionUtils.isEmpty(entrees)) {
            throw new RuntimeException("Veuillez supprimer les entrees... de cet fournisseur avant de poursuivre.");
        } else {
            fournisseurRepository.deleteById(idFournisseur);
        }
    }
}
