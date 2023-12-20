package bf.gov.gcob.medaille.model.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Getter
@Setter
@ToString
public class ResetConnectPaswword implements Serializable {

    private String newPassword;

    private String oldPassword;
}
