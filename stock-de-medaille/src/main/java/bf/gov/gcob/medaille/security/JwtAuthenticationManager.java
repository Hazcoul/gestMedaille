/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bf.gov.gcob.medaille.security;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * manager delegué pour l'authentification
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private JwtUtil jwtUtil;

    private CustomUserDetailsService userDetailsService;

    public JwtAuthenticationManager(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication auth) {
        String authToken = auth.getCredentials().toString();
        String username = jwtUtil.extractUsername(authToken);
        if (username != null && jwtUtil.validateToken(authToken)) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return Mono.just(authentication);
        } else {
            return Mono.empty();
        }
    }
}
