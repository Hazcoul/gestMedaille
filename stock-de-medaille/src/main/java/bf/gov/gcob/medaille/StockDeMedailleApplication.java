package bf.gov.gcob.medaille;

import bf.gov.gcob.medaille.services.ServiceImpl.InitalDataServiceImpl;
import bf.gov.gcob.medaille.utils.StockDeMedailleProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

//@OpenAPIDefinition(info = @Info(/*CONFIG SWAGGER*/
//        title = "STOCK-MEDAILLES API REST",
//        description = "SPINGDOC OF STOCK-MEDAILLES API REST",
//        version = "1.0",
//        contact = @Contact(name = "GCOB-DEV-TEAM", email = "infos@gcob.bf", url = "http://www.gcob.bf"),
//        license = @License(name = "Licence 1.0")))
@SpringBootApplication
@EnableCaching
public class StockDeMedailleApplication implements CommandLineRunner {

    @Autowired
    InitalDataServiceImpl service;

    public static void main(String[] args) {
        SpringApplication.run(StockDeMedailleApplication.class, args);
    }

    /**
     * S'exécute automatiquement au lancement de l'appli
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
//        service.saveInitAuthorities();
        service.saveInitProfil();
        service.saveInitUser();
        service.initGCOBConfig();
    }

    @Bean
    public StockDeMedailleProperties stockDeMedailleProperties() {
        return new StockDeMedailleProperties();
    }
}
