package bf.gov.gcob.medaille.security;

import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public class JwtAuthenticationWebFilter extends AuthenticationWebFilter {

    public JwtAuthenticationWebFilter(JwtAuthenticationManager authenticationManager, JwtServerAuthenticationConverter authenticationConverter) {
        super(authenticationManager);
        this.setServerAuthenticationConverter(authenticationConverter);
    }
}
