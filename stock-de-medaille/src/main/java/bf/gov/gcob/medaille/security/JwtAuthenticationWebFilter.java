/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
