package bf.gov.gcob.medaille.model.dto;

import java.util.Set;
import lombok.Data;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Data
public class ProfilDTO {

    private Long id;
    private String code;
    private String libelle;
    private String description;
    private boolean nativeProfile = false;
    private Set<PrivilegeDTO> privilegeCollection;
}
