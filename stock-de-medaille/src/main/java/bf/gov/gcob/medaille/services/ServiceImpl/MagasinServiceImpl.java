package bf.gov.gcob.medaille.services.ServiceImpl;

import bf.gov.gcob.medaille.mapper.MagasinMapper;
import bf.gov.gcob.medaille.model.dto.MagasinDTO;
import bf.gov.gcob.medaille.model.entities.Magasin;
import bf.gov.gcob.medaille.repository.MagasinRepository;
import bf.gov.gcob.medaille.services.MagasinService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class MagasinServiceImpl implements MagasinService {
    private final MagasinMapper magasinMapper;
    private final MagasinRepository magasinRepository;
    @Override
    public MagasinDTO create(MagasinDTO magasinDTO) {
        Magasin magasin=magasinMapper.toEntity(magasinDTO);
        magasin=magasinRepository.save(magasin);
            magasinDTO=magasinMapper.toDTO(magasin);
            return magasinDTO;
    }

    @Override
    public List<MagasinDTO> findAll() {
        List<MagasinDTO> magasinDTOS=magasinRepository.findAll().stream().map(magasinMapper::toDTO).collect(Collectors.toList());
        return magasinDTOS;
    }

    @Override
    public MagasinDTO update(MagasinDTO magasinDTO) {
       Magasin magasin=magasinRepository.findById(magasinDTO.getIdMagasin()).orElse(null);
        if(magasin==null){
            throw new RuntimeException("L'identifiant de ce magasin est : "+magasinDTO.getIdMagasin()+" n'existe pas");
        }
        magasin=magasinMapper.toEntity(magasinDTO);
        magasin=magasinRepository.save(magasin);
        MagasinDTO magasinModifer=magasinMapper.toDTO(magasin);
        return magasinModifer;
    }

    @Override
    public void delete(Long idMagasin) {
        magasinRepository.deleteById(idMagasin);
    }
}
