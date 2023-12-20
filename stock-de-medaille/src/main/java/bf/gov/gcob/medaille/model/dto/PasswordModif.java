package bf.gov.gcob.medaille.model.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Setter
@Getter
@ToString
public class PasswordModif implements Serializable {

    private String password;

    private String token;
}
