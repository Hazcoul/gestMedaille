package bf.gov.gcob.medaille.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bf.gov.gcob.medaille.exception.CreateNewElementException;
import bf.gov.gcob.medaille.model.dto.DistinctionDTO;
import bf.gov.gcob.medaille.services.DistinctionService;
import bf.gov.gcob.medaille.utils.web.PaginationUtil;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

//@CrossOrigin("*")
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

    @GetMapping()
    public Mono<ResponseEntity<List<DistinctionDTO>>> find() {
        List<DistinctionDTO> distinctions = distinctionService.findAll();
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), new PageImpl<>(distinctions));
        HttpHeaders headers = PaginationUtil.getHeaders(new PageImpl<>(distinctions));
        return Mono.just(ResponseEntity.ok().headers(headers).body(distinctions));
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
    public void delete(@PathVariable(name = "id", required = true) Long idDistinction) {
        distinctionService.delete(idDistinction);
    }

}
