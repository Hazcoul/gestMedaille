/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.gov.gcob.medaille.controller;

import bf.gov.gcob.medaille.exception.CreateNewElementException;
import bf.gov.gcob.medaille.model.dto.DetenteurDTO;
import bf.gov.gcob.medaille.services.DetenteurService;
import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/detenteurs")
public class DetenteurController {

    private DetenteurService service;

    /**
     * Creation d'un detenteur
     *
     * @param request
     * @return
     * @throws URISyntaxException
     */
    @PostMapping()
    public Mono<ResponseEntity<DetenteurDTO>> create(@Valid @RequestBody DetenteurDTO request) throws URISyntaxException {
        if (request.getIdDetenteur() != null) {
            throw new CreateNewElementException();
        }
        DetenteurDTO response = service.create(request);
        return Mono.just(ResponseEntity.created(new URI("/api/detenteurs/" + response.getIdDetenteur())).body(response));
    }

    /**
     * Met a jour les info d'un detenteur
     *
     * @param detenteurDTO
     * @return
     * @throws URISyntaxException
     */
    @PutMapping()
    public Mono<ResponseEntity<DetenteurDTO>> update(@Valid @RequestBody DetenteurDTO detenteurDTO) throws URISyntaxException {
        if (detenteurDTO.getIdDetenteur() == null) {
            throw new RuntimeException("Veuillez fournir toutes les informations nécessaires(ID) SVP.");
        }
        DetenteurDTO response = service.update(detenteurDTO);
        return Mono.just(ResponseEntity.ok().body(response));
    }

    /**
     * Liste de tous les detenteurs
     *
     * @return
     */
    @GetMapping()
    public Mono<ResponseEntity<List<DetenteurDTO>>> findAll() {
        List<DetenteurDTO> response = service.findAll();
        return Mono.just(ResponseEntity.ok().body(response));
    }

    /**
     * Suppression d'un detenteur via unID
     *
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return Mono.just(ResponseEntity
                .noContent()
                .build());

    }

}
