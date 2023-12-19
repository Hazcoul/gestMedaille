package bf.gov.gcob.medaille.controller;

import bf.gov.gcob.medaille.exception.CreateNewElementException;
import bf.gov.gcob.medaille.model.dto.FournisseurDto;
import bf.gov.gcob.medaille.services.FournisseurService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/fournisseurs")
public class FournisseurController {
    private FournisseurService fournisseurService;

    @PostMapping()
        public ResponseEntity<FournisseurDto> create(@RequestBody FournisseurDto request) throws URISyntaxException {
        if (request.getIdFournisseur() != null) {
            throw new CreateNewElementException();
        }
            FournisseurDto response = fournisseurService.create(request);
        return ResponseEntity.created(new URI("/api/fournisseurs")).body(response);
    }
    @GetMapping()
    public ResponseEntity<List<FournisseurDto>> find() {
        List<FournisseurDto> fournisseurs = fournisseurService.find();
        return ResponseEntity.ok().body(fournisseurs);
    }
    @PutMapping
    public ResponseEntity<FournisseurDto> update(@RequestBody FournisseurDto request) throws URISyntaxException {
        if (request.getIdFournisseur() == null) {
            throw new RuntimeException("Veuillez fournir un Id SVP.");
        }
        FournisseurDto response = fournisseurService.update(request);
        return ResponseEntity.ok().body(response);
    }

}
