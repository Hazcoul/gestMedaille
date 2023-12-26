/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.gov.gcob.medaille.services.ServiceImpl;

import bf.gov.gcob.medaille.mapper.BeneficiaireMapper;
import bf.gov.gcob.medaille.model.dto.BeneficiaireDTO;
import bf.gov.gcob.medaille.model.entities.Beneficiaire;
import bf.gov.gcob.medaille.repository.BeneficiaireRepository;
import bf.gov.gcob.medaille.services.BeneficiaireService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@AllArgsConstructor
@Slf4j
@Service
public class BeneficiaireServiceImpl implements BeneficiaireService {

    private BeneficiaireRepository beneficiaireRepository;

    private BeneficiaireMapper mapper;

    @Override
    public BeneficiaireDTO create(BeneficiaireDTO beneficiaireDTO) {
        log.info("Creation d'un beneficiaire {} ", beneficiaireDTO);
        Beneficiaire beneficiaire = mapper.toEntity(beneficiaireDTO);
        beneficiaire = beneficiaireRepository.save(beneficiaire);

        return mapper.toDTO(beneficiaire);
    }

    @Override
    public BeneficiaireDTO update(BeneficiaireDTO beneficiaireDTO) {
        log.info("Mise a jour d'un beneficiaire {} ", beneficiaireDTO);
        Beneficiaire beneficiaire = beneficiaireRepository.findById(beneficiaireDTO.getIdBeneficiaire()).orElseThrow(() -> new RuntimeException("Le beneficiaire ID [" + beneficiaireDTO.getIdBeneficiaire() + "] correspondant est introuvable. "));
        beneficiaire = mapper.toEntity(beneficiaireDTO);
        beneficiaire = beneficiaireRepository.save(beneficiaire);

        return mapper.toDTO(beneficiaire);
    }

    @Override
    public Page<BeneficiaireDTO> findAll(Pageable pageable) {
        log.info("Liste des beneficiaires");
        return beneficiaireRepository.findAll(pageable).map(mapper::toDTO);
    }

    @Override
    public void delete(Long idBeneficiaire) {
        log.info("Suppression du beneficiaire {} ", idBeneficiaire);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
