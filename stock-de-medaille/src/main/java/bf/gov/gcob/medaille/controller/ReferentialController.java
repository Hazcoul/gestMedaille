package bf.gov.gcob.medaille.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bf.gov.gcob.medaille.model.dto.MedailleDTO;
import bf.gov.gcob.medaille.model.dto.PieceJointeDTO;
import bf.gov.gcob.medaille.services.ReferentialService;
import bf.gov.gcob.medaille.utils.web.PaginationUtil;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class ReferentialController {
	
	private final Logger log = LoggerFactory.getLogger(ReferentialController.class);
	
	private final ReferentialService referentialService;
	
	public ReferentialController(ReferentialService referentialService) {
		this.referentialService = referentialService;
	}
	
	@GetMapping("/referentials")
	public ResponseEntity<Map<String, Collection<?>>> getAllEntrees() {
		log.debug("REST request to get all referentials");
        return new ResponseEntity<>(referentialService.getReferentials(), HttpStatus.OK);
	}
	
	@GetMapping("/referentials/medailles")
	public ResponseEntity<List<MedailleDTO>> getMedaillesForSelect() {
		log.debug("REST request to get all medailles");
        return new ResponseEntity<>(referentialService.getMedaillesForSelect(), HttpStatus.OK);
	}
	
   @GetMapping("/mouvements/{id}/{type}/piece-jointes")
    public Mono<ResponseEntity<List<PieceJointeDTO>>> findAllPieceJointes(@PathVariable("id") Long id, @PathVariable("type") String type) throws IOException {
        List<PieceJointeDTO> response = referentialService.FindPJByMvtIdAndType(id, type);
        HttpHeaders headers = PaginationUtil.getHeaders(new PageImpl<>(response));
        return Mono.just(ResponseEntity.ok().headers(headers).body(response));
    }


}
