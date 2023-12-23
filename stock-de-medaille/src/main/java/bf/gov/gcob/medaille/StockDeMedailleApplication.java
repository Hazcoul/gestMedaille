package bf.gov.gcob.medaille;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import bf.gov.gcob.medaille.services.ServiceImpl.InitalDataServiceImpl;
import bf.gov.gcob.medaille.utils.StockDeMedailleProperties;

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
        service.saveInitAuthorities();
        service.saveInitProfil();
        service.saveInitUser();
    }
    
    @Bean
    public StockDeMedailleProperties stockDeMedailleProperties() {
    	return new StockDeMedailleProperties();
    }
}
