package bf.gov.gcob.medaille.controller;

import bf.gov.gcob.medaille.exception.CreateNewElementException;
import bf.gov.gcob.medaille.model.dto.DistinctionDTO;
import bf.gov.gcob.medaille.services.DistinctionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

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
            return ResponseEntity.created(new URI("/api/fournisseurs")).body(response);
        }
    @GetMapping()
    public ResponseEntity<List<DistinctionDTO>> find() {
        List<DistinctionDTO> fournisseurs = distinctionService.findAll();
        return ResponseEntity.ok().body(fournisseurs);
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
    public void delete(@PathVariable(name = "id", required = true) Long idFournisseur){
        distinctionService.delete(idFournisseur);
    }

}
