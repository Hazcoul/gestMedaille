package bf.gov.gcob.medaille.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import bf.gov.gcob.medaille.model.dto.EntreeDTO;
import bf.gov.gcob.medaille.model.dto.FilterEntreeDto;
import bf.gov.gcob.medaille.model.dto.FilterSortieDto;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import bf.gov.gcob.medaille.model.dto.SortieDTO;
import bf.gov.gcob.medaille.services.SortieService;
import bf.gov.gcob.medaille.utils.web.HeaderUtil;
import bf.gov.gcob.medaille.utils.web.PaginationUtil;
import bf.gov.gcob.medaille.utils.web.errors.BadRequestAlertException;
import jakarta.validation.Valid;

@CrossOrigin("*")
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
        List<SortieDTO> response = sortieService.findAll();
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), new PageImpl<>(response));
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
    
    @GetMapping("/sorties/{id}")
    public ResponseEntity<SortieDTO> findByIdSortie(@PathVariable(value = "id", required = true) Long idSortie) {
    	log.debug("REST request to get one sortie");
    	return new ResponseEntity<>(sortieService.findOne(idSortie), HttpStatus.OK);
    }

    @GetMapping("/sorties/{annee}")
	public ResponseEntity<List<SortieDTO>> findByDecoByAn(@PathVariable(name = "annee", required = true) int annee) {
		List<SortieDTO> sortieDTOS = sortieService.findByDecoByAn(annee);
		return ResponseEntity.ok().body(sortieDTOS);
	}
    
    @DeleteMapping("/sorties/{id}")
    public ResponseEntity<Void> deleteSortie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Sortie: {}", id);
        sortieService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName, ENTITY_NAME + ".deleted", id.toString())).build();
    }

    @PostMapping("/sorties/statistique/sorties")
    public ResponseEntity<List<SortieDTO>> getAllSortieByCriteria(@RequestBody FilterSortieDto filterSortieDto, Pageable pageable){
        log.debug("REST request to get a page of sorties");
        Page<SortieDTO> page = sortieService.findAllByCriteria(filterSortieDto,pageable);
        HttpHeaders headers = new HttpHeaders() {
            {
                add("Access-Control-Expose-Headers", "X-Total-Count");
                add("X-Total-Count", String.valueOf(page.getTotalElements()));
            }
        };
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/sorties/statistique/sorties/impression/{idSortie}")
    public Resource getLigneSortieBySortie(@PathVariable("idSortie") Long id) {
        log.debug("REST request to get a page of sorties");
        return sortieService.getLigneSortieBySortie(id);
    }

}
