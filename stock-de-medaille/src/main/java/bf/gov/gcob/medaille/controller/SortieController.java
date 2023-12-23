package bf.gov.gcob.medaille.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bf.gov.gcob.medaille.model.dto.SortieDTO;
import bf.gov.gcob.medaille.services.SortieService;
import bf.gov.gcob.medaille.utils.web.HeaderUtil;
import bf.gov.gcob.medaille.utils.web.errors.BadRequestAlertException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class SortieController {
	
private final Logger log = LoggerFactory.getLogger(SortieController.class);
	
	private static final String ENTITY_NAME = "sortie";
	
	@Value("${application.name}")
    private String applicationName;
	
	private final SortieService sortieService;
	
	public SortieController(SortieService sortieService) {
		this.sortieService = sortieService;
	}
	
	@PostMapping("/sorties")
	public ResponseEntity<SortieDTO> createSortie(@Valid @RequestBody SortieDTO sortieDTO) throws URISyntaxException {
		log.debug("REST request to save Sortie : {}", sortieDTO);
		if (sortieDTO.getIdSortie() != null) {
            throw new BadRequestAlertException("A new entry cannot already have an ID", ENTITY_NAME, "idexists");
        } 
		SortieDTO newSortieDTO = sortieService.save(sortieDTO);
		return ResponseEntity
                .created(new URI("/api/sorties/" + newSortieDTO.getIdSortie()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME + ".created", newSortieDTO.getIdSortie().toString()))
                .body(newSortieDTO);
	}
	
	@PutMapping("/sorties")
	public ResponseEntity<SortieDTO> updateSortie(@Valid @RequestBody SortieDTO sortieDTO) throws URISyntaxException {
		log.debug("REST request to save Sortie : {}", sortieDTO);
		if (sortieDTO.getIdSortie() == null) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idnull");
        } 
		SortieDTO newSortieDTO = sortieService.save(sortieDTO);
		return ResponseEntity
                .created(new URI("/api/sorties/" + newSortieDTO.getIdSortie()))
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME + ".updated", newSortieDTO.getIdSortie().toString()))
                .body(newSortieDTO);
	}
	
	@GetMapping("/sorties")
	public ResponseEntity<List<SortieDTO>> getAllSorties() {
		log.debug("REST request to get all sorties");
        return new ResponseEntity<>(sortieService.findAll(), HttpStatus.OK);
	}

}
