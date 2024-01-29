package bf.gov.gcob.medaille.services.ServiceImpl;

import bf.gov.gcob.medaille.mapper.MagasinMapper;
import bf.gov.gcob.medaille.model.dto.MagasinDTO;
import bf.gov.gcob.medaille.model.entities.Entree;
import bf.gov.gcob.medaille.model.entities.Magasin;
import bf.gov.gcob.medaille.model.entities.Sortie;
import bf.gov.gcob.medaille.repository.EntreeRepository;
import bf.gov.gcob.medaille.repository.MagasinRepository;
import bf.gov.gcob.medaille.repository.SortieRepository;
import bf.gov.gcob.medaille.services.MagasinService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class MagasinServiceImpl implements MagasinService {

    private final MagasinMapper magasinMapper;
    private final MagasinRepository magasinRepository;
    private final EntreeRepository entreeRepository;
    private final SortieRepository sortieRepository;

    @Override
    public MagasinDTO create(MagasinDTO magasinDTO) {
        Magasin magasin = magasinMapper.toEntity(magasinDTO);
        magasin = magasinRepository.save(magasin);
        magasinDTO = magasinMapper.toDTO(magasin);
        return magasinDTO;
    }

    @Override
    public List<MagasinDTO> findAll() {
        List<MagasinDTO> magasinDTOS = magasinRepository.findAll().stream().map(magasinMapper::toDTO).collect(Collectors.toList());
        return magasinDTOS;
    }

    @Override
    public MagasinDTO update(MagasinDTO magasinDTO) {
        Magasin magasin = magasinRepository.findById(magasinDTO.getIdMagasin()).orElse(null);
        if (magasin == null) {
            throw new RuntimeException("L'identifiant de ce magasin est : " + magasinDTO.getIdMagasin() + " n'existe pas");
        }
        magasin = magasinMapper.toEntity(magasinDTO);
        magasin = magasinRepository.save(magasin);
        MagasinDTO magasinModifer = magasinMapper.toDTO(magasin);
        return magasinModifer;
    }

    @Override
    public void delete(Long idMagasin) {
        log.info("Suppression du magasin : {}", idMagasin);
        List<Entree> entrees = entreeRepository.findByMagasinIdMagasin(idMagasin);
        List<Sortie> sorties = sortieRepository.findByMagasinIdMagasin(idMagasin);
        if (entrees.size() != 0 || sorties.size() != 0) {
            throw new RuntimeException("Veuillez supprimer les entrée/sortie... de cet magasin avant de poursuivre.");
        } else {
            magasinRepository.deleteById(idMagasin);
        }
    }
}
