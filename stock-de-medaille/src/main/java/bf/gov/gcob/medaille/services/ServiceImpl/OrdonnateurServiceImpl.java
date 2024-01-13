package bf.gov.gcob.medaille.services.ServiceImpl;

import bf.gov.gcob.medaille.mapper.OrdonnateurMapper;
import bf.gov.gcob.medaille.model.dto.OrdonnateurDTO;
import bf.gov.gcob.medaille.model.entities.Ordonnateur;
import bf.gov.gcob.medaille.repository.OrdonnateurRepository;
import bf.gov.gcob.medaille.services.OrdonnateurService;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class OrdonnateurServiceImpl implements OrdonnateurService {
    
    private final OrdonnateurRepository ordonnateurRepository;
    private final OrdonnateurMapper ordonnateurMapper;
    
    @Override
    public OrdonnateurDTO create(OrdonnateurDTO ordonnateurDTO) {
        Ordonnateur response = ordonnateurMapper.toEntity(ordonnateurDTO);
        Ordonnateur check = ordonnateurRepository.findByActuelTrue().orElse(null);
        if (check == null) {//premier enregistrement
            response.setActuel(true);
            response.setDebutMandat(new Date());
            response.setFinMandat(null);
        } else {
            throw new RuntimeException("Vous avez un ordonnateur actuel en vigueur. Veuillez le désactiver avant de poursuivre.");
        }
        response = ordonnateurRepository.save(response);
        return ordonnateurMapper.toDTO(response);
    }
    
    @Override
    public List<OrdonnateurDTO> findAll() {
        List<OrdonnateurDTO> ordonnateurs = ordonnateurRepository.findAll().stream().map(ordonnateurMapper::toDTO).collect(Collectors.toList());
        return ordonnateurs;
    }
    
    @Override
    public OrdonnateurDTO update(OrdonnateurDTO ordonnateurDTO) {
        Ordonnateur response = ordonnateurRepository.findById(ordonnateurDTO.getIdOrdonnateur()).orElse(null);
        if (response == null) {
            throw new RuntimeException("L'identifiant de ce ordonnateur est : " + ordonnateurDTO.getIdOrdonnateur() + " n'existe pas");
        }
        if (!ordonnateurDTO.isActuel() && response.isActuel()) {//on tente une modif + desactivation
            ordonnateurDTO.setFinMandat(new Date());
        } else if (ordonnateurDTO.isActuel() && !response.isActuel()) {//tentative hazardeuse d'activer un ordonnateur
            throw new RuntimeException("Vous avez déjà un ordonnateur actuel en vigueur différent de celui-ci. Veuillez le désactiver avant de poursuivre.");
        }
        response = ordonnateurMapper.toEntity(ordonnateurDTO);
        response = ordonnateurRepository.save(response);
        return ordonnateurMapper.toDTO(response);
    }
    
    @Override
    public void delete(Long idOrdonnateur) {
        ordonnateurRepository.deleteById(idOrdonnateur);
    }
    
    @Override
    public OrdonnateurDTO desactiver(Long idOrdonnateur) {
        log.info("Desactivation de l'ordonnateur : {}", idOrdonnateur);
        Ordonnateur response = ordonnateurRepository.findById(idOrdonnateur).orElseThrow(() -> new RuntimeException("L'ordonnateur " + idOrdonnateur + " est introuvable."));
        response.setActuel(false);
        response.setFinMandat(new Date());
        return ordonnateurMapper.toDTO(ordonnateurRepository.save(response));
    }
    
    @Override
    public OrdonnateurDTO reactiver(Long idOrdonnateur) {
        log.info("Reactivation de l'ordonnateur : {}", idOrdonnateur);
        Ordonnateur response = ordonnateurRepository.findByActuelTrue().orElse(null);
        if (response == null) {
            response = ordonnateurRepository.findById(idOrdonnateur).orElseThrow(() -> new RuntimeException("L'ordonnateur " + idOrdonnateur + " est introuvable."));
            response.setActuel(true);
            response.setDebutMandat(new Date());
            response.setFinMandat(null);
            response = ordonnateurRepository.save(response);
        } else if (response != null && response.getIdOrdonnateur() != idOrdonnateur) {
            throw new RuntimeException("Vous avez déjà un ordonnateur actuel en vigueur différent de celui-ci. Veuillez le désactiver avant de poursuivre.");
        }
        return ordonnateurMapper.toDTO(response);
    }
}
