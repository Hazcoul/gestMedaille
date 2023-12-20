package bf.gov.gcob.medaille.model.dto;

import lombok.Data;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Data
public class PrivilegeDTO {

    private Long id;
    private String code;
    private String libelle;
    private String description;
}
