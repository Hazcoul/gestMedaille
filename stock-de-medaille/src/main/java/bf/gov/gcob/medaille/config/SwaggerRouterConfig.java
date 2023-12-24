///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package bf.gov.gcob.medaille.config;
//
//import bf.gov.gcob.medaille.services.DetenteurService;
//import org.springdoc.core.annotations.RouterOperation;
//import org.springdoc.core.annotations.RouterOperations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.RouterFunctions;
//import org.springframework.web.reactive.function.server.ServerResponse;
//
///**
// *
// * @author Canisius <canisiushien@gmail.com>
// */
//@Configuration
//public class SwaggerRouterConfig {
//
//    @Autowired
//    private DetenteurService service;
//
//    @Bean
//    @RouterOperations({
//        @RouterOperation(
//                path = "/api/detenteurs",
//                produces = {MediaType.APPLICATION_JSON_VALUE},
//                method = RequestMethod.GET,
//                beanClass = DetenteurService.class,
//                beanMethod = "findAll"
//        )
//    })
//    public RouterFunction<ServerResponse> routerFunction() {
//        return RouterFunctions.route()
//                .GET("/api/detenteurs", service::findAll)
//                .PUT("/api/detenteurs", service::update)
//                .POST("/api/detenteurs", service::create)
//                .build();
//    }
//
//}
