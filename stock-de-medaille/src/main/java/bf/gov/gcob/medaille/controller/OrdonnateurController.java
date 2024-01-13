package bf.gov.gcob.medaille.controller;

import bf.gov.gcob.medaille.exception.CreateNewElementException;
import bf.gov.gcob.medaille.model.dto.OrdonnateurDTO;
import bf.gov.gcob.medaille.services.OrdonnateurService;
import bf.gov.gcob.medaille.utils.web.PaginationUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/ordonnateurs")
public class OrdonnateurController {

    private final OrdonnateurService ordonnateurService;

    @PostMapping()
    public ResponseEntity<OrdonnateurDTO> create(@Validated @RequestBody OrdonnateurDTO ordonnateurDTO) throws URISyntaxException {
        if (ordonnateurDTO.getIdOrdonnateur() != null) {
            throw new CreateNewElementException();
        }
        OrdonnateurDTO response = ordonnateurService.create(ordonnateurDTO);
        return ResponseEntity.created(new URI("/api/ordonnateurs")).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<OrdonnateurDTO>> findAll() {
        List<OrdonnateurDTO> ordonnateurDTOS = ordonnateurService.findAll();
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), new PageImpl<>(ordonnateurDTOS));
        HttpHeaders headers = PaginationUtil.getHeaders(new PageImpl<>(ordonnateurDTOS));
        return ResponseEntity.ok().headers(headers).body(ordonnateurDTOS);
    }

    @GetMapping("/desactivation/{id}")
    public ResponseEntity<OrdonnateurDTO> desactiver(@PathVariable(name = "id", required = true) Long idOrdonnateur) {
        OrdonnateurDTO response = ordonnateurService.desactiver((idOrdonnateur));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/reactivation/{id}")
    public ResponseEntity<OrdonnateurDTO> reactiver(@PathVariable(name = "id", required = true) Long idOrdonnateur) {
        OrdonnateurDTO response = ordonnateurService.reactiver((idOrdonnateur));
        return ResponseEntity.ok().body(response);
    }

    @PutMapping()
    public ResponseEntity<OrdonnateurDTO> update(@RequestBody OrdonnateurDTO ordonnateurDTO) throws URISyntaxException {
        if (ordonnateurDTO.getIdOrdonnateur() == null) {
            throw new RuntimeException("Veuillez fournir un identifiant SVP");
        }
        OrdonnateurDTO response = ordonnateurService.update((ordonnateurDTO));
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id", required = true) Long idOrdonnateur) {
        ordonnateurService.delete(idOrdonnateur);
    }

}
