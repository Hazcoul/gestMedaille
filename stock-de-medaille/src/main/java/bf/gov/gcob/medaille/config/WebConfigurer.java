/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.gov.gcob.medaille.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * Configuration of web application with Servlet 3.0 APIs.
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Configuration
@EnableWebFlux
public class WebConfigurer {

    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("*"));
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.setAllowCredentials(false);
        config.setMaxAge(1800L);
        config.setExposedHeaders(Arrays.asList("Authorization,Link,X-Total-Count,X-GCOB-alert,X-GCOB-error,X-GCOB-params"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        if (!CollectionUtils.isEmpty(config.getAllowedOrigins())) {
            source.registerCorsConfiguration("/api/**", config);
            source.registerCorsConfiguration("/v3/api-docs", config);
            source.registerCorsConfiguration("/swagger-resources", config);
            source.registerCorsConfiguration("/swagger-ui/**", config);
            source.registerCorsConfiguration("/**", config);
        }
        return new CorsWebFilter(source);
    }

    //swagger doc config
    @Bean
    public OpenAPI openApiInformation() {
        Server localServer = new Server()
                .url("http://localhost:8070")
                .description("Localhost Server URL");

        Contact contact = new Contact()
                .email("infos@gcob.bf")
                .name("GCOB Dev team");

        Info info = new Info()
                .contact(contact)
                .description("Documentation API GESTION-STOCK-MEDAILLE")
                .summary("Demo of Spring Boot 3 & Open API 3 Integration")
                .title("STOCK-MEDAILLE API REST")
                .version("V1.0.0")
                .license(new License());

        return new OpenAPI().info(info).addServersItem(localServer);
    }
}
