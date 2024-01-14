//package bf.gov.gcob.medaille.security;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.Customizer;
//import static org.springframework.security.config.Customizer.withDefaults;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
//import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
//import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
//import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
//
//@Configuration
//@EnableMethodSecurity(securedEnabled = true)
//public class SecurityConfigurationOLD {
//
//    @Value("${gco.content.security.policy}")
//    private String contentSecurityPolicy;
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
//        http
//                .cors(withDefaults())
//                .csrf(csrf -> csrf.disable())
//                .addFilterAfter(new SpaWebFilter(), BasicAuthenticationFilter.class)
//                .headers(headers
//                        -> headers
//                        .contentSecurityPolicy(csp -> csp.policyDirectives(contentSecurityPolicy))
//                        .frameOptions(FrameOptionsConfig::sameOrigin)
//                        .referrerPolicy(referrer -> referrer.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN))
//                        .permissionsPolicy(permissions
//                                -> permissions.policy(
//                                "camera=(), fullscreen=(self), geolocation=(), gyroscope=(), magnetometer=(), microphone=(), midi=(), payment=(), sync-xhr=()"
//                        )
//                        )
//                )
//                .authorizeHttpRequests(authz
//                        -> // prettier-ignore
//                        authz
//                        .requestMatchers(mvc.pattern(HttpMethod.OPTIONS, "*/**")).permitAll()
//                        .requestMatchers(mvc.pattern("/v3/api-docs/**")).permitAll()
//                        .requestMatchers(mvc.pattern("/swagger-ui/**")).permitAll()
//                        .requestMatchers(mvc.pattern("/swagger-ui.html")).permitAll()
//                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/auth/utilisateurs/signin")).permitAll()
//                        .requestMatchers(mvc.pattern("/api/auth/utilisateurs/reset-password")).permitAll()
//                        .requestMatchers(mvc.pattern("/api/auth/utilisateurs/validate")).permitAll()
//                        //                    .requestMatchers(mvc.pattern("/api/**")).authenticated()
//                        .requestMatchers(mvc.pattern("/api/**")).permitAll()
//                        .anyRequest().authenticated()
//                )
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .exceptionHandling(exceptions
//                        -> exceptions
//                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
//                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
//                )
//                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
//        return http.build();
//    }
//
//    @Bean
//    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
//        return new MvcRequestMatcher.Builder(introspector);
//    }
//}
