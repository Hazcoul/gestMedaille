package bf.gov.gcob.medaille.controller;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bf.gov.gcob.medaille.services.ReferentialService;

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

}
