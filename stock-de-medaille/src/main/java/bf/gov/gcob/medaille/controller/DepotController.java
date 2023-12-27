package bf.gov.gcob.medaille.controller;

import bf.gov.gcob.medaille.exception.CreateNewElementException;
import bf.gov.gcob.medaille.model.dto.DepotDTO;
import bf.gov.gcob.medaille.services.DepotService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/depots")
public class DepotController {

    private final DepotService depotService;

    @PostMapping()
    public ResponseEntity<DepotDTO> create(@RequestBody DepotDTO depotDTO) throws URISyntaxException {
        if (depotDTO.getIdDepot() != null) {
            throw new CreateNewElementException();
        }
        DepotDTO response = depotService.create(depotDTO);
        return ResponseEntity.created(new URI("/api/depots")).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<DepotDTO>> find() {
        List<DepotDTO> depotDTOS = depotService.findAll();
        return ResponseEntity.ok().body(depotDTOS);
    }

    @PutMapping()
    public ResponseEntity<DepotDTO> update(@RequestBody DepotDTO request) throws URISyntaxException {
        if (request.getIdDepot() == null) {
            throw new RuntimeException("Veuillez fournir un Id SVP.");
        }
        DepotDTO response = depotService.update(request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id", required = true) Long idDepot) {
        depotService.delete(idDepot);
    }
}
