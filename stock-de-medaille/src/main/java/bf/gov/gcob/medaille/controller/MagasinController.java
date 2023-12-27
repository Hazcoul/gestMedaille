package bf.gov.gcob.medaille.controller;

import bf.gov.gcob.medaille.exception.CreateNewElementException;
import bf.gov.gcob.medaille.model.dto.MagasinDTO;
import bf.gov.gcob.medaille.services.MagasinService;
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
@RequestMapping(path = "/api/magasins")
public class MagasinController {
    
    private final MagasinService magasinService;
    
    @PostMapping()
    public ResponseEntity<MagasinDTO> create(@RequestBody MagasinDTO magasinDTO) throws URISyntaxException {
        if (magasinDTO.getIdMagasin() != null) {
            throw new CreateNewElementException();
        }
        MagasinDTO response = magasinService.create(magasinDTO);
        return ResponseEntity.created(new URI("/api/magasins")).body(response);
    }
    
    @GetMapping()
    public ResponseEntity<List<MagasinDTO>> find() {
        List<MagasinDTO> magasinDTOS = magasinService.findAll();
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), new PageImpl<>(magasinDTOS));
        return ResponseEntity.ok().headers(headers).body(magasinDTOS);
    }
    
    @PutMapping()
    public ResponseEntity<MagasinDTO> update(@RequestBody MagasinDTO request) throws URISyntaxException {
        if (request.getIdMagasin() == null) {
            throw new RuntimeException("Veuillez fournir un Id SVP.");
        }
        MagasinDTO response = magasinService.update(request);
        return ResponseEntity.ok().body(response);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id", required = true) Long idMagasin) {
        magasinService.delete(idMagasin);
    }
}
