package bf.gov.gcob.medaille.controller;

import bf.gov.gcob.medaille.exception.CreateNewElementException;
import bf.gov.gcob.medaille.model.dto.FournisseurDTO;
import bf.gov.gcob.medaille.services.FournisseurService;
import bf.gov.gcob.medaille.utils.web.PaginationUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/fournisseurs")
public class FournisseurController {

    private FournisseurService fournisseurService;

    @PostMapping()
    public ResponseEntity<FournisseurDTO> create(@RequestBody FournisseurDTO request) throws URISyntaxException {
        if (request.getIdFournisseur() != null) {
            throw new CreateNewElementException();
        }
        FournisseurDTO response = fournisseurService.create(request);
        return ResponseEntity.created(new URI("/api/fournisseurs")).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<FournisseurDTO>> find() {
        List<FournisseurDTO> fournisseurs = fournisseurService.find();
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), new PageImpl<>(fournisseurs));
        return ResponseEntity.ok().headers(headers).body(fournisseurs);
    }

    @PutMapping
    public ResponseEntity<FournisseurDTO> update(@RequestBody FournisseurDTO request) throws URISyntaxException {
        if (request.getIdFournisseur() == null) {
            throw new RuntimeException("Veuillez fournir un Id SVP.");
        }
        FournisseurDTO response = fournisseurService.update(request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id", required = true) Long idFournisseur) {
        fournisseurService.delete(idFournisseur);
    }
}
