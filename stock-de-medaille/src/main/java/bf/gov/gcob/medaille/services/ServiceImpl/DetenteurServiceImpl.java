/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.gov.gcob.medaille.services.ServiceImpl;

import bf.gov.gcob.medaille.mapper.DetenteurMapper;
import bf.gov.gcob.medaille.model.dto.DetenteurDTO;
import bf.gov.gcob.medaille.model.entities.Detenteur;
import bf.gov.gcob.medaille.repository.DetenteurRepository;
import bf.gov.gcob.medaille.services.DetenteurService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@Service
@AllArgsConstructor
public class DetenteurServiceImpl implements DetenteurService {

    private DetenteurRepository detenteurRepository;
    private DetenteurMapper mapper;

    @Override
    public DetenteurDTO create(DetenteurDTO detenteurDTO) {
        log.info("Creation d'un detenteur {} ", detenteurDTO);
        Detenteur detenteur = mapper.toEntity(detenteurDTO);
        detenteur = detenteurRepository.save(detenteur);

        return mapper.toDTO(detenteur);
    }

    @Override
    public DetenteurDTO update(DetenteurDTO detenteurDTO) {
        log.info("Mise a jour d'un detenteur {} ", detenteurDTO);
        Detenteur detenteur = detenteurRepository.findById(detenteurDTO.getIdDetenteur()).orElseThrow(() -> new RuntimeException("Le detenteur ID [" + detenteurDTO.getIdDetenteur() + "] correspondant est introuvable. "));
        detenteur = mapper.toEntity(detenteurDTO);
        detenteur = detenteurRepository.save(detenteur);

        return mapper.toDTO(detenteur);
    }

    @Override
    public List<DetenteurDTO> findAll() {
        log.info("Liste des detenteurs");
        return detenteurRepository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void delete(Long idDetenteur) {
        log.info("Suppression du detenteur {} ", idDetenteur);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
