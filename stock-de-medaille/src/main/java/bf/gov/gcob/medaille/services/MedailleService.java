/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.gov.gcob.medaille.services;

import bf.gov.gcob.medaille.model.dto.DistinctionDTO;
import bf.gov.gcob.medaille.model.dto.MedailleDTO;
import java.io.IOException;
import java.util.List;
import org.springframework.http.codec.multipart.FilePart;

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
    MedailleDTO create(MedailleDTO medailleDTO, FilePart imageMedaille);

    /**
     * modifie des infos d'une medaille deja enregistrée
     *
     * @param medailleDTO
     * @return
     */
    MedailleDTO update(MedailleDTO medailleDTO);

    /**
     * remplace ou associe une image d'une medaille donnée
     *
     * @param medailleId : id de la medaille
     * @param imageMedaille : fichier image de la medaille
     * @return
     */
    MedailleDTO updateImagecatalogue(Long medailleId, FilePart imageMedaille);

    /**
     * Modifie les infos ainsi que l'image d'une medaille
     *
     * @param medailleDTO
     * @param imageMedaille
     * @return
     */
    MedailleDTO update(MedailleDTO medailleDTO, FilePart imageMedaille);

    /**
     * liste toutes les medailles
     *
     * @return
     */
    List<MedailleDTO> findAll() throws IOException;

    byte[] getImageMedaille(String lienImage) throws IOException;

    /**
     * supprime une medaille via un ID
     *
     * @param idMedaille
     */
    void delete(Long idMedaille);
}
