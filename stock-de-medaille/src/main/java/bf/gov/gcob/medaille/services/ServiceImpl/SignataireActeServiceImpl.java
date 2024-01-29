/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.gov.gcob.medaille.services.ServiceImpl;

import bf.gov.gcob.medaille.mapper.SignataireActeMapper;
import bf.gov.gcob.medaille.model.dto.SignataireActeDTO;
import bf.gov.gcob.medaille.model.entities.SignataireActe;
import bf.gov.gcob.medaille.model.enums.EFonctionSignataire;
import bf.gov.gcob.medaille.model.enums.ENatureActe;
import bf.gov.gcob.medaille.repository.SignataireActeRepository;
import bf.gov.gcob.medaille.services.SignataireActeService;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
public class SignataireActeServiceImpl implements SignataireActeService {
    
    private final SignataireActeRepository signataireActeRepository;
    private final SignataireActeMapper signataireActeMapper;
    
    @Override
    public SignataireActeDTO create(SignataireActeDTO signataireActeDTO) {
        
        SignataireActe response = signataireActeMapper.toEntity(signataireActeDTO);
        SignataireActe check = signataireActeRepository.findByNatureActeAndFonctionSignataireAndActifTrue(ENatureActe.getByLibelle(signataireActeDTO.getNatureActe()), EFonctionSignataire.getByLibelle(signataireActeDTO.getFonctionSignataire())).orElse(null);
        if (check == null) {//premier enregistrement
            response.setActif(true);
            response.setDebutMandat(new Date());
            response.setFinMandat(null);
        } else {
            throw new RuntimeException("Vous avez un signataire dédié actuel en vigueur. Veuillez le désactiver avant de poursuivre.");
        }
        response = signataireActeRepository.save(response);
        return signataireActeMapper.toDTO(response);
    }
    
    @Override
    public SignataireActeDTO update(SignataireActeDTO signataireActeDTO) {
        SignataireActe response = signataireActeRepository.findById(signataireActeDTO.getIdSignataire()).orElse(null);
        if (response == null) {
            throw new RuntimeException("L'identifiant de ce signataire est : " + signataireActeDTO.getIdSignataire() + " n'existe pas");
        }
        if (signataireActeDTO.getActif() == true && response.getActif() == false) {//tentative hazardeuse d'activer un signataire
            throw new RuntimeException("Vous avez déjà un signataire dédié actuel en vigueur différent de celui-ci. Veuillez le désactiver avant de poursuivre.");
        }
        response = signataireActeMapper.toEntity(signataireActeDTO);
        if (signataireActeDTO.getActif() == false && response.getActif() == true) {//on tente une modif + desactivation
            response.setFinMandat(new Date());
        }
        response = signataireActeRepository.save(response);
        return signataireActeMapper.toDTO(response);
    }
    
    @Override
    public SignataireActeDTO desactiver(Long idSignataireActe) {
        log.info("Desactivation du signataire : {}", idSignataireActe);
        SignataireActe response = signataireActeRepository.findById(idSignataireActe).orElseThrow(() -> new RuntimeException("Le signataire " + idSignataireActe + " est introuvable."));
        response.setActif(false);
        response.setFinMandat(new Date());
        return signataireActeMapper.toDTO(signataireActeRepository.save(response));
    }
    
    @Override
    public SignataireActeDTO reactiver(Long idSignataireActe) {
        log.info("Reactivation du signataire  : {}", idSignataireActe);
        SignataireActe response = signataireActeRepository.findByIdSignataireAndActifTrue(idSignataireActe).orElse(null);
        if (response == null) {
            response = signataireActeRepository.findById(idSignataireActe).orElseThrow(() -> new RuntimeException("Le signataire " + idSignataireActe + " est introuvable."));
            response.setActif(true);
            response.setDebutMandat(new Date());
            response.setFinMandat(null);
            response = signataireActeRepository.save(response);
        } else if (response != null && response.getIdSignataire() != idSignataireActe) {
            throw new RuntimeException("Vous avez déjà un signataire dédié actuel en vigueur différent de celui-ci. Veuillez le désactiver avant de poursuivre.");
        }
        return signataireActeMapper.toDTO(response);
    }
    
    @Override
    public Optional<SignataireActeDTO> get(Long id) {
        Optional<SignataireActeDTO> response = signataireActeRepository.findById(id).map(signataireActeMapper::toDTO);
        return response;
    }
    
    @Override
    public List<SignataireActeDTO> findAll() {
        List<SignataireActeDTO> response = signataireActeRepository.findAll().stream().map(signataireActeMapper::toDTO).collect(Collectors.toList());
        return response;
    }
    
    @Override
    public void delete(Long idSignataire) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
