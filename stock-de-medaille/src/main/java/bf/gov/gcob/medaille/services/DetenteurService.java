/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.gov.gcob.medaille.services;

import bf.gov.gcob.medaille.model.dto.DetenteurDTO;
import java.util.List;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface DetenteurService {

    /**
     * ajoute un Detenteur en base
     *
     * @param detenteurDTO
     * @return
     */
    DetenteurDTO create(DetenteurDTO detenteurDTO);

    /**
     * modifie des infos d'un Detenteur deja enregistré
     *
     * @param detenteurDTO
     * @return
     */
    DetenteurDTO update(DetenteurDTO detenteurDTO);

    /**
     * liste tous les Detenteurs
     *
     * @return
     */
    List<DetenteurDTO> findAll();

    /**
     * supprime un Detenteur via un ID
     *
     * @param idDetenteur
     */
    void delete(Long idDetenteur);
}
