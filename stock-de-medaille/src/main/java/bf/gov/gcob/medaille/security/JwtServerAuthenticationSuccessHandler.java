/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bf.gov.gcob.medaille.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import reactor.core.publisher.Mono;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public class JwtServerAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        String username = authentication.getName();
        System.out.println("********\n username:" + username + "\n********");
        boolean rememberMe = Boolean.FALSE;
        // Générez un nouveau token JWT ici, par exemple avec un service JwtUtil
        String token = jwtUtil.generateToken(username, rememberMe);
        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        response.getHeaders().add("Authorization", "Bearer " + token);
        return Mono.empty();
    }
}
