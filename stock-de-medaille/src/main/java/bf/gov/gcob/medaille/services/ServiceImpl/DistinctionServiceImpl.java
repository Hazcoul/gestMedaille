package bf.gov.gcob.medaille.services.ServiceImpl;

import bf.gov.gcob.medaille.mapper.DistinctionMapper;
import bf.gov.gcob.medaille.model.dto.DistinctionDTO;
import bf.gov.gcob.medaille.model.entities.Distinction;
import bf.gov.gcob.medaille.model.entities.Medaille;
import bf.gov.gcob.medaille.repository.DistinctionRepository;
import bf.gov.gcob.medaille.repository.MedailleRepository;
import bf.gov.gcob.medaille.services.DistinctionService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class DistinctionServiceImpl implements DistinctionService {

    private final DistinctionMapper distinctionMapper;
    private final DistinctionRepository distinctionRepository;
    private final MedailleRepository medailleRepository;

    @Override
    public DistinctionDTO create(DistinctionDTO distinctionDTO) {
        Distinction distinction = distinctionMapper.toEntity(distinctionDTO);
        distinction = distinctionRepository.save(distinction);
        distinctionDTO = distinctionMapper.toDTO(distinction);
        return distinctionDTO;
    }

    @Override
    public List<DistinctionDTO> findAll() {
        log.info("Liste de toutes les distinctions");
        List<DistinctionDTO> distinctions = distinctionRepository.findAll().stream().map(distinctionMapper::toDTO).collect(Collectors.toList());
        return distinctions;
    }

    @Override
    public DistinctionDTO update(DistinctionDTO distinctionDTO) {
        Distinction distinction = distinctionRepository.findById(distinctionDTO.getIdDistinction()).orElse(null);
        if (distinction == null) {
            throw new RuntimeException("L'identifiant de ce ordonnateur est : " + distinctionDTO.getIdDistinction() + " n'existe pas");
        }
        distinction = distinctionMapper.toEntity(distinctionDTO);
        distinction = distinctionRepository.save(distinction);
        DistinctionDTO ordonnateurModifer = distinctionMapper.toDTO(distinction);
        return ordonnateurModifer;
    }

    @Override
    public void delete(Long idDistinction) {
        log.info("Suppression de la distinction : {}", idDistinction);
        List<Medaille> medailles = medailleRepository.findByDistinctionIdDistinction(idDistinction);
        if (medailles.size() != 0) {
            throw new RuntimeException("Veuillez supprimer les medailles... de cette distinction avant de poursuivre.");
        } else {
            distinctionRepository.deleteById(idDistinction);
        }
    }
}
