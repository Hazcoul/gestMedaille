package bf.gov.gcob.medaille.controller;

import bf.gov.gcob.medaille.exception.CreateNewElementException;
import bf.gov.gcob.medaille.model.dto.FournisseurDTO;
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
        return ResponseEntity.ok().body(fournisseurs);
    }
    @PutMapping
    public ResponseEntity<FournisseurDTO> update(@RequestBody FournisseurDTO request) throws URISyntaxException {
        if (request.getIdFournisseur() == null) {
            throw new RuntimeException("Veuillez fournir un Id SVP.");
        }
        FournisseurDTO response = fournisseurService.update(request);
        return ResponseEntity.ok().body(response);
    }

}
