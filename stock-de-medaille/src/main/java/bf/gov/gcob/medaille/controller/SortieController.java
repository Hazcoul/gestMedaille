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
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import bf.gov.gcob.medaille.model.dto.FilterSortieDto;
import bf.gov.gcob.medaille.model.dto.LigneImpressionSortiePeriodeDTO;
import bf.gov.gcob.medaille.model.dto.PieceJointeDTO;
import bf.gov.gcob.medaille.model.dto.SortieDTO;
import bf.gov.gcob.medaille.model.enums.EMvtStatus;
import bf.gov.gcob.medaille.services.ReportService;
import bf.gov.gcob.medaille.services.SortieService;
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
public class SortieController {

    private final Logger log = LoggerFactory.getLogger(SortieController.class);

    private static final String ENTITY_NAME = "sortie";

    @Value("${application.name}")
    private String applicationName;

    private final SortieService sortieService;

    private final ReportService reportService;
    
    private final UtilisateurService utilisateurService;

    public SortieController(SortieService sortieService, ReportService reportService,
    		UtilisateurService utilisateurService) {
        this.sortieService = sortieService;
        this.reportService = reportService;
        this.utilisateurService = utilisateurService;
    }

    @PostMapping(value ="/sorties", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}, headers = "Content-Type=multipart/form-data")
    public ResponseEntity<SortieDTO> createSortie(@Valid @RequestPart(value = "data") SortieDTO sortieDTO, @RequestPart(value = "pjData") List<PieceJointeDTO> pjDTOs, @RequestPart(value = "pjFiles") List<FilePart> pFiles) throws URISyntaxException {
        log.debug("REST request to save Sortie : {}", sortieDTO);
        if (sortieDTO.getIdSortie() != null) {
            throw new BadRequestAlertException("A new entry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SortieDTO newSortieDTO = sortieService.save(sortieDTO, pjDTOs, pFiles);
        return ResponseEntity
                .created(new URI("/api/sorties/" + newSortieDTO.getIdSortie()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME + ".created", newSortieDTO.getIdSortie().toString()))
                .body(newSortieDTO);
    }

    @PutMapping(value ="/sorties", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}, headers = "Content-Type=multipart/form-data")
    public ResponseEntity<?> updateSortie(@Valid @RequestPart(value = "data") SortieDTO sortieDTO, @RequestPart(value = "pjData") List<PieceJointeDTO> pjDTOs, @RequestPart(value = "pjFiles") List<FilePart> pFiles) throws URISyntaxException {
        log.debug("REST request to save Sortie : {}", sortieDTO);
        if (sortieDTO.getIdSortie() == null) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idnull");
        }
        if(EMvtStatus.valueOf(sortieDTO.getStatus().toString()).equals(EMvtStatus.VALIDATED)) {
        	ApiResponse<Object> result = new ApiResponse<>();
        	result.setCode(ENTITY_NAME);
        	result.setMsg("Une sortie validée n'est plus modifiable.");
        	return ResponseEntity.badRequest().body(result);
        }
        SortieDTO newSortieDTO = sortieService.save(sortieDTO, pjDTOs, pFiles);
        return ResponseEntity
                .created(new URI("/api/sorties/" + newSortieDTO.getIdSortie()))
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME + ".updated", newSortieDTO.getIdSortie().toString()))
                .body(newSortieDTO);
    }

    @GetMapping("/sorties")
    public ResponseEntity<List<SortieDTO>> getAllSorties() {
        log.debug("REST request to get all sorties");
        List<SortieDTO> response = sortieService.findAll();
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), new PageImpl<>(response));
        HttpHeaders headers = PaginationUtil.getHeaders(new PageImpl<>(response));
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @GetMapping("/sorties/{id}")
    public ResponseEntity<SortieDTO> findByIdSortie(@PathVariable(value = "id", required = true) Long idSortie) {
        log.debug("REST request to get one sortie");
        return new ResponseEntity<>(sortieService.findOne(idSortie), HttpStatus.OK);
    }

    @GetMapping("/sorties/par-annee/{annee}")
    public ResponseEntity<List<SortieDTO>> findByDecoByAn(@PathVariable(name = "annee", required = true) int annee) {
        List<SortieDTO> sortieDTOS = sortieService.findByDecoByAn(annee);
        return ResponseEntity.ok().body(sortieDTOS);
    }

    @DeleteMapping("/sorties/{id}")
    public ResponseEntity<?> deleteSortie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Sortie: {}", id);
        try {
        	sortieService.delete(id);
            return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName, ENTITY_NAME + ".deleted", id.toString())).build();
		} catch (Exception ex) {
			ApiResponse<Object> result = new ApiResponse<>();
        	result.setCode(ENTITY_NAME);
        	result.setMsg(ex.getMessage());
        	result.setData(ex.getCause());
        	return ResponseEntity.badRequest().body(result);// TODO: handle exception
		}
    }
    
    @DeleteMapping("/sorties/{id}/lignes/{idLine}")
    public ResponseEntity<?> deleteLineS(@PathVariable("id") Long id, @PathVariable("idLine") Long idLine) {
        log.debug("REST request to delete line of Sortie: {}", idLine);
        try {
        	sortieService.delete(id);
            return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName, ENTITY_NAME + ".deleted", id.toString())).build();
		} catch (Exception ex) {
			ApiResponse<Object> result = new ApiResponse<>();
        	result.setCode(ENTITY_NAME);
        	result.setMsg(ex.getMessage());
        	result.setData(ex.getCause());
        	return ResponseEntity.badRequest().body(result);// TODO: handle exception
		}
    }

    @PostMapping("/sorties/statistique/sorties")
    public ResponseEntity<List<SortieDTO>> getAllSortieByCriteria(@RequestBody FilterSortieDto filterSortieDto) {
        log.debug("REST request to get a page of sorties");
        List<SortieDTO> sortieDTOS = sortieService.findAllByCriteria(filterSortieDto);
       /* HttpHeaders headers = new HttpHeaders() {
            {
                add("Access-Control-Expose-Headers", "X-Total-Count");
                add("X-Total-Count", String.valueOf(page.getTotalElements()));
            }
        };*/
        return ResponseEntity.ok().body(sortieDTOS);
    }

    @PostMapping("/sorties/statistique/sorties/periode")
    public ResponseEntity<List<LigneImpressionSortiePeriodeDTO>> getAllSortiePeriodeByCriteria(@RequestBody FilterSortieDto filterSortieDto, Pageable pageable) {
        log.debug("REST request to get a page of sorties");
        List<LigneImpressionSortiePeriodeDTO> ligneImpressionSortiePeriodeDTOS = sortieService.findAllSortiesByPeriode(filterSortieDto);
        return ResponseEntity.ok().body(ligneImpressionSortiePeriodeDTOS);
    }

    @GetMapping("/sorties/statistique/sorties/impression/{idSortie}")
    public Resource getLigneSortieBySortie(@PathVariable("idSortie") Long id) {
        log.debug("REST request to get a page of sorties");
        return sortieService.getLigneSortieBySortie(id);
    }

    @PostMapping("/sorties/statistique/sorties/periode/impression")
    public Resource getLigneSortieByPeriode(@RequestBody FilterSortieDto filterSortieDto) {
        log.debug("REST request to get a page of sorties");
        return sortieService.getLigneSortieByPeriode(filterSortieDto);
    }

    /**
     * Genere un bordereau de mise en consommation
     *
     * @param idSortie
     * @param format
     * @return
     * @throws IOException
     * @throws JRException
     */
    @GetMapping(value = "/sorties/{id}/bordereau/{format}")
    public  Mono<Resource> generateBordereau(@PathVariable(name = "id", required = true) Long idSortie, @PathVariable(name = "format", required = true) String format)
    	throws IOException, JRException {
    	
    	return Mono.just(reportService.printBmConsommation(idSortie, format));
    }
    
    /**
     * Valide une sortie de matieres
     *
     * @param idSortie
     * @return
     */
    @GetMapping(value = "/sorties/{id}/valider")
    public Mono<?> validerSortie(@PathVariable(name = "id", required = true) Long idSortie, ServerHttpRequest request) {
        try {
        	SortieDTO sortie = sortieService.validerSortie(idSortie, utilisateurService.findUserInfos(request));
            return Mono.just(sortie);
		} catch (Exception e) {
			ApiResponse<Object> result = new ApiResponse<>();
        	result.setCode(ENTITY_NAME);
        	result.setMsg(e.getMessage());
        	result.setData(e.getCause());
			return Mono.just(result);
		}
    }
    
    @GetMapping(value = "/sorties/{id}/rejeter")
    public Mono<?> rejeterSortie(@PathVariable(name = "id", required = true) Long id, @RequestParam(name = "comment", required = true) String comment) {
    	try {
            return Mono.just(sortieService.rejeter(id, comment));
		} catch (Exception e) {
			ApiResponse<Object> result = new ApiResponse<>();
        	result.setCode(ENTITY_NAME);
        	result.setMsg(e.getMessage());
        	result.setData(e.getCause());
			return Mono.just(ResponseEntity.badRequest().body(result));
		}
    }

}
