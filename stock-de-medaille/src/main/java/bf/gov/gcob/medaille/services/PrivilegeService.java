package bf.gov.gcob.medaille.services;

import bf.gov.gcob.medaille.model.dto.PrivilegeDTO;
import java.util.List;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface PrivilegeService {

    /**
     * Liste tous les privileges
     *
     * @return
     */
    List<PrivilegeDTO> findAll();

}
