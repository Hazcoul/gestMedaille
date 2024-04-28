package bf.gov.gcob.medaille.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import bf.gov.gcob.medaille.model.dto.ApiResponse;
import bf.gov.gcob.medaille.model.dto.EntreeDTO;
import bf.gov.gcob.medaille.model.dto.FilterEntreeDto;
import bf.gov.gcob.medaille.model.dto.PieceJointeDTO;
import bf.gov.gcob.medaille.model.enums.EMvtStatus;
import bf.gov.gcob.medaille.services.EntreeService;
import bf.gov.gcob.medaille.services.ReportService;
import bf.gov.gcob.medaille.services.UtilisateurService;
import bf.gov.gcob.medaille.utils.web.HeaderUtil;
import bf.gov.gcob.medaille.utils.web.PaginationUtil;
import bf.gov.gcob.medaille.utils.web.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import net.sf.jasperreports.engine.JRException;
import reactor.core.publisher.Mono;

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
    
    private final UtilisateurService utilisateurService;

    public EntreeController(EntreeService entreeService, ReportService reportService,
    		UtilisateurService utilisateurService) {
        this.entreeService = entreeService;
        this.reportService = reportService;
        this.utilisateurService = utilisateurService;
    }

    @PostMapping(value ="/entrees"/*, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}, headers = "Content-Type=multipart/form-data"*/)
    public ResponseEntity<EntreeDTO> createEntree(@Valid @RequestPart(value = "data") EntreeDTO entreeDTO, @RequestPart(value = "pjData") List<PieceJointeDTO> pjDTOs, @RequestPart(value = "pjFiles") List<FilePart> pFiles) throws URISyntaxException {
        log.debug("REST request to save Entree : {}", entreeDTO);
        if (entreeDTO.getIdEntree() != null) {
            throw new BadRequestAlertException("A new entry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntreeDTO newEntreeDTO = entreeService.save(entreeDTO, pjDTOs, pFiles);
        return ResponseEntity
                .created(new URI("/api/entrees/" + newEntreeDTO.getIdEntree()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME + ".created", newEntreeDTO.getIdEntree().toString()))
                .body(newEntreeDTO);
    }

    @PutMapping(value ="/entrees"/*, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}, headers = "Content-Type=multipart/form-data"*/)
    public ResponseEntity<?> updateEntree(@RequestPart(value = "data") EntreeDTO entreeDTO, @RequestPart(value = "pjData") List<PieceJointeDTO> pjDTOs, @RequestPart(value = "pjFiles") List<FilePart> pFiles) throws URISyntaxException {
        log.debug("REST request to save Entree : {}", entreeDTO);
        if (entreeDTO.getIdEntree() == null) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idnull");
        }
        if(EMvtStatus.valueOf(entreeDTO.getStatus().toString()).equals(EMvtStatus.VALIDATED)) {
        	ApiResponse<Object> result = new ApiResponse<>();
        	result.setCode(ENTITY_NAME);
        	result.setMsg("Une entrée validée n'est plus modifiable.");
        	return ResponseEntity.badRequest().body(result);
        }
        EntreeDTO newEntreeDTO = entreeService.save(entreeDTO, pjDTOs, pFiles);
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
    public ResponseEntity<?> deleteEntree(@PathVariable("id") Long id) {
        log.debug("REST request to delete Entree: {}", id);
        try {
        	entreeService.delete(id);
            return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName, ENTITY_NAME + ".deleted", id.toString())).build();
		} catch (Exception ex) {
			ApiResponse<Object> result = new ApiResponse<>();
        	result.setCode(ENTITY_NAME);
        	result.setMsg(ex.getMessage());
        	result.setData(ex.getCause());
        	return ResponseEntity.badRequest().body(result);
		}
    }
    
    @DeleteMapping("/entrees/{id}/lignes/{idLine}")
    public ResponseEntity<?> deleteLineE(@PathVariable("id") Long id, @PathVariable("idLine") Long idLine) {
        log.debug("REST request to delete given line of Entree: {}", idLine);
        try {
        	entreeService.deleteLine(id, idLine);
            return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName, ENTITY_NAME + ".deleted", id.toString())).build();
		} catch (Exception ex) {
			ApiResponse<Object> result = new ApiResponse<>();
        	result.setCode(ENTITY_NAME);
        	result.setMsg(ex.getMessage());
        	result.setData(ex.getCause());
        	return ResponseEntity.badRequest().body(result);
		}
    }

    @PostMapping("/entrees/statistique/commandes")
    public ResponseEntity<List<EntreeDTO>> getAllCommandeByCriteria(@RequestBody FilterEntreeDto filterEntreeDto) {
        log.debug("REST request to get a page of commandes");
        List<EntreeDTO> entreeDTOS = entreeService.findAllByCriteria(filterEntreeDto);
        return ResponseEntity.ok().body(entreeDTOS);
    }

    @GetMapping("/entrees/statistique/commandes/impression/{idCommande}")
    public Resource getlisteEntreeByCommande(@PathVariable("idCommande") Long id) {
        log.debug("REST request to get a page of commandes");
        log.debug(String.valueOf(id));
        return entreeService.getlisteEntreeByCommande(id);
    }

    /**
     * Genere un etat d'ordre d'entree de matieres
     *
     * @param idEntree
     * @param format
     * @return
     * @throws IOException
     * @throws JRException
     */
    @GetMapping(value = "/entrees/{id}/etat/{format}")
    public Resource generateEtat(@PathVariable(name = "id", required = true) Long idEntree, @PathVariable(name = "format", required = true) String format)
    	throws IOException, JRException {
    	
        return reportService.printOrdreEntreeMatiere(idEntree, format);
    }
    
    /**
     * Valide une entree de matieres
     *
     * @param idEntree
     * @return
     */
    @GetMapping(value = "/entrees/{id}/valider")
    public Mono<?> validerEntreeMatieres(@PathVariable(name = "id", required = true) Long idEntree, ServerHttpRequest request) {
        try {
        	EntreeDTO entree = entreeService.validerEntree(idEntree, utilisateurService.findUserInfos(request));
            return Mono.just(entree);
		} catch (Exception e) {
			ApiResponse<Object> result = new ApiResponse<>();
        	result.setCode(ENTITY_NAME);
        	result.setMsg(e.getMessage());
        	result.setData(e.getCause());
			return Mono.just(result);
		}
    }
    
    @GetMapping(value = "/entrees/{id}/rejeter")
    public Mono<?> rejeterEntree(@PathVariable(name = "id", required = true) Long id, @RequestParam(name = "comment", required = true) String comment) {
    	try {
            return Mono.just(entreeService.rejeter(id, comment));
		} catch (Exception e) {
			ApiResponse<Object> result = new ApiResponse<>();
        	result.setCode(ENTITY_NAME);
        	result.setMsg(e.getMessage());
        	result.setData(e.getCause());
			return Mono.just(ResponseEntity.badRequest().body(result));
		}
    }
}
