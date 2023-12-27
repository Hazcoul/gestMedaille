package bf.gov.gcob.medaille.services;

import bf.gov.gcob.medaille.model.dto.ProfilDTO;
import java.util.List;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface ProfilService {

    /**
     * Creer un profile
     *
     * @param profileDTO
     * @return
     */
    ProfilDTO save(ProfilDTO profileDTO);

    /**
     * Liste par page les profiles
     *
     * @return
     */
    List<ProfilDTO> findAll();

    /**
     * Consulte un profil via ID
     *
     * @param id
     * @return
     */
    ProfilDTO findById(Long id);

    /**
     * Liste les libellés des profiles
     *
     * @return
     */
    List<String> getProfiles();

    /**
     * Consulte un profil via CODE
     *
     * @param code
     * @return
     */
    ProfilDTO findByCode(String code);

    /**
     * Supprime un profile
     *
     * @param profileDTO
     */
    void delete(Long idProfil);
}
