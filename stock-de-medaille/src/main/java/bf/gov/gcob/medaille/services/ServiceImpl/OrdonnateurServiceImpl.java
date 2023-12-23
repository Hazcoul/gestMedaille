package bf.gov.gcob.medaille.services.ServiceImpl;

import bf.gov.gcob.medaille.mapper.OrdonnateurMapper;
import bf.gov.gcob.medaille.model.dto.OrdonnateurDTO;
import bf.gov.gcob.medaille.model.entities.Ordonnateur;
import bf.gov.gcob.medaille.repository.OrdonnateurRepository;

import bf.gov.gcob.medaille.services.OrdonnateurService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor

public class OrdonnateurServiceImpl implements OrdonnateurService {
    private final OrdonnateurRepository ordonnateurRepository;
    private final OrdonnateurMapper ordonnateurMapper;
    @Override
    public OrdonnateurDTO create(OrdonnateurDTO ordonnateurDTO) {
        Ordonnateur ordonnateur=ordonnateurMapper.toEntity(ordonnateurDTO);
        ordonnateur=ordonnateurRepository.save(ordonnateur);
        ordonnateurDTO=ordonnateurMapper.toDTO(ordonnateur);
        return ordonnateurDTO;
    }

    @Override
    public List<OrdonnateurDTO> findAll() {
        List<OrdonnateurDTO> ordonnateurs=ordonnateurRepository.findAll().stream().map(ordonnateurMapper::toDTO).collect(Collectors.toList());
        return ordonnateurs;
    }

    @Override
    public OrdonnateurDTO update(OrdonnateurDTO ordonnateurDTO) {
        Ordonnateur ordonnateur=ordonnateurRepository.findById(ordonnateurDTO.getIdOrdonnateur()).orElse(null);
        if(ordonnateur==null){
            throw new RuntimeException("L'identifiant de ce ordonnateur est : "+ordonnateurDTO.getIdOrdonnateur()+" n'existe pas");
        }
        ordonnateur=ordonnateurMapper.toEntity(ordonnateurDTO);
        ordonnateur=ordonnateurRepository.save(ordonnateur);
        OrdonnateurDTO ordonnateurModifer=ordonnateurMapper.toDTO(ordonnateur);
        return ordonnateurModifer;
    }

    @Override
    public void delete(Long idOrdonnateur) {
        ordonnateurRepository.deleteById(idOrdonnateur);
    }
}