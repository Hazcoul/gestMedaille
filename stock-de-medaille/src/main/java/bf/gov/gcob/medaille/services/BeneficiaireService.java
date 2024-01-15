/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.gov.gcob.medaille.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import bf.gov.gcob.medaille.model.dto.BeneficiaireDTO;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface BeneficiaireService {

    /**
     * ajoute un Beneficiaire en base
     *
     * @param beneficiaireDTO
     * @return
     */
    BeneficiaireDTO create(BeneficiaireDTO beneficiaireDTO);

    /**
     * modifie des infos d'un Beneficiaire deja enregistré
     *
     * @param beneficiaireDTO
     * @return
     */
    BeneficiaireDTO update(BeneficiaireDTO beneficiaireDTO);

    /**
     * liste tous les Beneficiaires
     *
     * @return
     */
    List<BeneficiaireDTO> findAll();

    /**
     * supprime un Beneficiaire via un ID
     *
     * @param idBeneficiaire
     */
    void delete(Long idBeneficiaire);
}
