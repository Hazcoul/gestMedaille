/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.gov.gcob.medaille.controller;

import bf.gov.gcob.medaille.exception.CreateNewElementException;
import bf.gov.gcob.medaille.model.dto.SignataireActeDTO;
import bf.gov.gcob.medaille.services.SignataireActeService;
import bf.gov.gcob.medaille.utils.web.PaginationUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/signataires")
public class SignataireActeController {

    private final SignataireActeService signataireActeService;

    @PostMapping()
    public ResponseEntity<SignataireActeDTO> create(@Validated @RequestBody SignataireActeDTO signataireDTO) throws URISyntaxException {
        if (signataireDTO.getIdSignataire() != null) {
            throw new CreateNewElementException();
        }
        SignataireActeDTO response = signataireActeService.create(signataireDTO);
        return ResponseEntity.created(new URI("/api/signataires")).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SignataireActeDTO> get(@PathVariable(name = "id", required = true) Long idSignataire) {
        SignataireActeDTO response = signataireActeService.get(idSignataire).get();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping()
    public ResponseEntity<List<SignataireActeDTO>> findAll() {
        List<SignataireActeDTO> signataireDTOS = signataireActeService.findAll();
        HttpHeaders headers = PaginationUtil.getHeaders(new PageImpl<>(signataireDTOS));
        return ResponseEntity.ok().headers(headers).body(signataireDTOS);
    }

    @GetMapping("/desactivation/{id}")
    public ResponseEntity<SignataireActeDTO> desactiver(@PathVariable(name = "id", required = true) Long idSignataire) {
        SignataireActeDTO response = signataireActeService.desactiver((idSignataire));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/reactivation/{id}")
    public ResponseEntity<SignataireActeDTO> reactiver(@PathVariable(name = "id", required = true) Long idSignataire) {
        SignataireActeDTO response = signataireActeService.reactiver((idSignataire));
        return ResponseEntity.ok().body(response);
    }

    @PutMapping()
    public ResponseEntity<SignataireActeDTO> update(@RequestBody SignataireActeDTO signataireDTO) throws URISyntaxException {
        if (signataireDTO.getIdSignataire() == null) {
            throw new RuntimeException("Veuillez fournir un identifiant SVP");
        }
        SignataireActeDTO response = signataireActeService.update((signataireDTO));
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id", required = true) Long idSignataire) {
        signataireActeService.delete(idSignataire);
    }
}
