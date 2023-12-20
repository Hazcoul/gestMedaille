/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.gov.gcob.medaille.services;

import bf.gov.gcob.medaille.model.dto.MedailleDTO;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface MedailleService {

    /**
     * ajoute une medaille en base
     *
     * @param medailleDTO
     * @return
     */
    MedailleDTO create(MedailleDTO medailleDTO, MultipartFile imageMedaille);

    /**
     * modifie des infos d'une medaille deja enregistrée
     *
     * @param medailleDTO
     * @return
     */
    MedailleDTO update(MedailleDTO medailleDTO);

    /**
     * liste toutes les medailles
     *
     * @return
     */
    List<MedailleDTO> findAll();

    /**
     * liste les medailles hors usage ou non
     *
     * @return
     */
    List<MedailleDTO> findAllHorsUsageOrNo(boolean isUtilisable);

    /**
     * supprime une medaille via un ID
     *
     * @param idMedaille
     */
    void delete(Long idMedaille);
}
