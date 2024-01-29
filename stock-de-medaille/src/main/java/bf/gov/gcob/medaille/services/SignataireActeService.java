/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.gov.gcob.medaille.services;

import bf.gov.gcob.medaille.model.dto.SignataireActeDTO;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface SignataireActeService {

    SignataireActeDTO create(SignataireActeDTO signataireActeDTO);

    SignataireActeDTO update(SignataireActeDTO signataireActeDTO);

    SignataireActeDTO desactiver(Long idSignataire);

    SignataireActeDTO reactiver(Long idSignataire);

    Optional<SignataireActeDTO> get(Long id);

    List<SignataireActeDTO> findAll();

    void delete(Long idSignataire);
}
