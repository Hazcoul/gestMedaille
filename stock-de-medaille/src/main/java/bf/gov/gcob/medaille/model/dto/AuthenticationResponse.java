package bf.gov.gcob.medaille.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * reponse retournée après authentification
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Getter
@Setter
@ToString
public class AuthenticationResponse {

    private String accessToken;
    private String tokenType = "Bearer";

    public AuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
