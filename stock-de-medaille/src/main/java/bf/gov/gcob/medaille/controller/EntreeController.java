package bf.gov.gcob.medaille.controller;

import bf.gov.gcob.medaille.model.dto.EntreeDTO;
import bf.gov.gcob.medaille.services.EntreeService;
import bf.gov.gcob.medaille.utils.web.HeaderUtil;
import bf.gov.gcob.medaille.utils.web.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class EntreeController {

    private final Logger log = LoggerFactory.getLogger(EntreeController.class);

    private static final String ENTITY_NAME = "entree";

    @Value("${application.name}")
    private String applicationName;

    private final EntreeService entreeService;

    public EntreeController(EntreeService entreeService) {
        this.entreeService = entreeService;
    }

    @PostMapping("/entrees")
    public ResponseEntity<EntreeDTO> createEntree(@Valid @RequestBody EntreeDTO entreeDTO) throws URISyntaxException {
        log.debug("REST request to save Entree : {}", entreeDTO);
        if (entreeDTO.getIdEntree() != null) {
            throw new BadRequestAlertException("A new entry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntreeDTO newEntreeDTO = entreeService.save(entreeDTO);
        return ResponseEntity
                .created(new URI("/api/entrees/" + newEntreeDTO.getIdEntree()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME + ".created", newEntreeDTO.getIdEntree().toString()))
                .body(newEntreeDTO);
    }

    @PutMapping("/entrees")
    public ResponseEntity<EntreeDTO> updateEntree(@Valid @RequestBody EntreeDTO entreeDTO) throws URISyntaxException {
        log.debug("REST request to save Entree : {}", entreeDTO);
        if (entreeDTO.getIdEntree() == null) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idnull");
        }
        EntreeDTO newEntreeDTO = entreeService.save(entreeDTO);
        return ResponseEntity
                .created(new URI("/api/entrees/" + newEntreeDTO.getIdEntree()))
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME + ".updated", newEntreeDTO.getIdEntree().toString()))
                .body(newEntreeDTO);
    }

    @GetMapping("/entrees")
    public ResponseEntity<List<EntreeDTO>> getAllEntrees() {
        log.debug("REST request to get all entrees");
        return new ResponseEntity<>(entreeService.findAll(), HttpStatus.OK);
    }

}
