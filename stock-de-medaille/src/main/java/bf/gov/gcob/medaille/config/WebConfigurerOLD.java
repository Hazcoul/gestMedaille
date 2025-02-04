//package bf.gov.gcob.medaille.config;
//
//import bf.gov.gcob.medaille.utils.StockDeMedailleProperties;
//import jakarta.servlet.ServletContext;
//import jakarta.servlet.ServletException;
//import java.io.File;
//import static java.net.URLDecoder.decode;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Paths;
//import java.util.Arrays;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.web.server.WebServerFactory;
//import org.springframework.boot.web.server.WebServerFactoryCustomizer;
//import org.springframework.boot.web.servlet.ServletContextInitializer;
//import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
///**
// * Configuration of web application with Servlet 3.0 APIs.
// */
//@Configuration
//public class WebConfigurerOLD implements ServletContextInitializer, WebServerFactoryCustomizer<WebServerFactory> {
//
//    private final Logger log = LoggerFactory.getLogger(WebConfigurerOLD.class);
//
//    private final Environment env;
//
//    private final StockDeMedailleProperties stockDeMedailleProperties;
//
//    public WebConfigurerOLD(Environment env, StockDeMedailleProperties stockDeMedailleProperties) {
//        this.env = env;
//        this.stockDeMedailleProperties = stockDeMedailleProperties;
//    }
//
//    @Override
//    public void onStartup(ServletContext servletContext) throws ServletException {
//        if (env.getActiveProfiles().length != 0) {
//            log.info("Web application configuration, using profiles: {}", (Object[]) env.getActiveProfiles());
//        }
//
//        log.info("Web application fully configured");
//    }
//
//    /**
//     * Customize the Servlet engine: Mime types, the document root, the cache.
//     */
//    @Override
//    public void customize(WebServerFactory server) {
//        // When running in an IDE or with ./mvnw spring-boot:run, set location of the static web assets.
//        setLocationForStaticAssets(server);
//    }
//
//    private void setLocationForStaticAssets(WebServerFactory server) {
//        if (server instanceof ConfigurableServletWebServerFactory servletWebServer) {
//            File root;
//            String prefixPath = resolvePathPrefix();
//            root = new File(prefixPath + "target/classes/static/");
//            if (root.exists() && root.isDirectory()) {
//                servletWebServer.setDocumentRoot(root);
//            }
//        }
//    }
//
//    /**
//     * Resolve path prefix to static resources.
//     */
//    private String resolvePathPrefix() {
//        String fullExecutablePath = decode(this.getClass().getResource("").getPath(), StandardCharsets.UTF_8);
//        String rootPath = Paths.get(".").toUri().normalize().getPath();
//        String extractedPath = fullExecutablePath.replace(rootPath, "");
//        int extractionEndIndex = extractedPath.indexOf("target/");
//        if (extractionEndIndex <= 0) {
//            return "";
//        }
//        return extractedPath.substring(0, extractionEndIndex);
//    }
//
////    @Bean
////    public CorsFilter corsFilter() {
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        CorsConfiguration config = stockDeMedailleProperties.getCors();
////        if (!CollectionUtils.isEmpty(config.getAllowedOrigins()) || !CollectionUtils.isEmpty(config.getAllowedOriginPatterns())) {
////            log.debug("Registering CORS filter");
////            source.registerCorsConfiguration("/api/**", config);
////            source.registerCorsConfiguration("/**", config);
////            source.registerCorsConfiguration("/management/**", config);
////            source.registerCorsConfiguration("/v3/api-docs", config);
////            source.registerCorsConfiguration("/swagger-ui/**", config);
////        }
////        return new CorsFilter(source);
////    }
//    @Bean
//    public CorsFilter corsFilter() {//ceci fonctionne avec le angular
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowedOrigins(Arrays.asList("*"));
//        //or any domain that you want to restrict to 
//        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization"));
//        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
//        //Add the method support as you like 
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }
//}
