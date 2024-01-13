package bf.gov.gcob.medaille.controller;

import bf.gov.gcob.medaille.config.Constants;
import bf.gov.gcob.medaille.model.dto.EntreeDTO;
import bf.gov.gcob.medaille.model.dto.FilterEntreeDto;
import bf.gov.gcob.medaille.services.EntreeService;
import bf.gov.gcob.medaille.services.ReportService;
import bf.gov.gcob.medaille.utils.web.HeaderUtil;
import bf.gov.gcob.medaille.utils.web.PaginationUtil;
import bf.gov.gcob.medaille.utils.web.errors.BadRequestAlertException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import net.sf.jasperreports.engine.JRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    private final ReportService reportService;

    public EntreeController(EntreeService entreeService, ReportService reportService) {
        this.entreeService = entreeService;
        this.reportService = reportService;
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
        List<EntreeDTO> response = entreeService.findAll();
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), new PageImpl<>(response));
        HttpHeaders headers = PaginationUtil.getHeaders(new PageImpl<>(response));
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @GetMapping("/entrees/{id}")
    public ResponseEntity<EntreeDTO> findByIdEntree(@PathVariable(value = "id", required = true) Long idEntree) {
        log.debug("REST request to get one entree");
        return new ResponseEntity<>(entreeService.findOne(idEntree), HttpStatus.OK);
    }

    @GetMapping("/{annee}")
    public ResponseEntity<List<EntreeDTO>> findByAn(@PathVariable(name = "annee", required = true) int annee) {
        List<EntreeDTO> entreeDTOS = entreeService.findByAn(annee);
        return ResponseEntity.ok().body(entreeDTOS);
    }

    @DeleteMapping("/entrees/{id}")
    public ResponseEntity<Void> deleteEntree(@PathVariable("id") Long id) {
        log.debug("REST request to delete Entree: {}", id);
        entreeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName, ENTITY_NAME + ".deleted", id.toString())).build();
    }

    @PostMapping("/entrees/statistique/commandes")
    public ResponseEntity<List<EntreeDTO>> getAllCommandeByCriteria(@RequestBody FilterEntreeDto filterEntreeDto, Pageable pageable) {
        log.debug("REST request to get a page of commandes");
        Page<EntreeDTO> page = entreeService.findAllByCriteria(filterEntreeDto, pageable);
        HttpHeaders headers = new HttpHeaders() {
            {
                add("Access-Control-Expose-Headers", "X-Total-Count");
                add("X-Total-Count", String.valueOf(page.getTotalElements()));
            }
        };
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/entrees/statistique/commandes/impression/{idCommande}")
    public Resource getlisteEntreeByCommande(@PathVariable("idCommande") Long id) {
        log.debug("REST request to get a page of commandes");
        log.debug(String.valueOf(id));
        return entreeService.getlisteEntreeByCommande(id);
    }

    /**
     * Valide une entree et genere un etat d'ordre d'entree de matieres
     *
     * @param response
     * @param idEntree
     * @param format
     * @throws IOException
     * @throws JRException
     */
    @GetMapping(value = "/entrees/validation/{id}/{format}")
    public void validerEntreeMatieres(HttpServletResponse response, @PathVariable(name = "id", required = true) Long idEntree, @PathVariable(name = "format", required = true) String format)
            throws IOException, JRException {
        EntreeDTO entree = entreeService.validerEntree(idEntree);

        String[] tab = Constants.constructFormatAndExtension(format);
        response.setContentType(tab[0]);
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"ORDRE_ENTREE_" + idEntree + tab[1] + "\""));
        OutputStream outputStream = response.getOutputStream();
        reportService.printOrdreEntreeMatiere(entree.getIdEntree(), format, outputStream);

    }
}
