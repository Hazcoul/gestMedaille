package bf.gov.gcob.medaille.controller;

import bf.gov.gcob.medaille.exception.CreateNewElementException;
import bf.gov.gcob.medaille.model.dto.DistinctionDTO;
import bf.gov.gcob.medaille.services.DistinctionService;
import bf.gov.gcob.medaille.utils.web.PaginationUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/distinctions")
public class DistinctionController {

    private DistinctionService distinctionService;

    @PostMapping()
    public ResponseEntity<DistinctionDTO> create(@RequestBody DistinctionDTO request) throws URISyntaxException {
        if (request.getIdDistinction() != null) {
            throw new CreateNewElementException();
        }
        DistinctionDTO response = distinctionService.create(request);
        return ResponseEntity.created(new URI("/api/distinctions")).body(response);
    }

    //@PreAuthorize("hasAnyAuthority(\"" + Constants.ADMIN + "\")")
    @GetMapping()
    public ResponseEntity<List<DistinctionDTO>> find() {
        List<DistinctionDTO> distinctions = distinctionService.findAll();
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), new PageImpl<>(distinctions));
        return new ResponseEntity<>(distinctions, headers, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<DistinctionDTO> update(@RequestBody DistinctionDTO request) throws URISyntaxException {
        if (request.getIdDistinction() == null) {
            throw new RuntimeException("Veuillez fournir un Id SVP.");
        }
        DistinctionDTO response = distinctionService.update(request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id", required = true) Long idFournisseur) {
        distinctionService.delete(idFournisseur);
    }

}
