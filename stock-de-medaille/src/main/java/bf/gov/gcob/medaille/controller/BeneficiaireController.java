/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.gov.gcob.medaille.controller;

import bf.gov.gcob.medaille.exception.CreateNewElementException;
import bf.gov.gcob.medaille.model.dto.BeneficiaireDTO;
import bf.gov.gcob.medaille.services.BeneficiaireService;
import bf.gov.gcob.medaille.utils.web.PaginationUtil;
import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reactor.core.publisher.Mono;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/beneficiaires")
public class BeneficiaireController {

    private BeneficiaireService service;

    /**
     * Creation d'un beneficiaire
     *
     * @param request
     * @return
     * @throws URISyntaxException
     */
    @PostMapping()
    public Mono<ResponseEntity<BeneficiaireDTO>> create(@Valid @RequestBody BeneficiaireDTO request) throws URISyntaxException {
        if (request.getIdBeneficiaire() != null) {
            throw new CreateNewElementException();
        }
        BeneficiaireDTO response = service.create(request);
        return Mono.just(ResponseEntity.created(new URI("/api/beneficiaires/" + response.getIdBeneficiaire())).body(response));
    }

    /**
     * Met a jour les info d'unn beneficiaire
     *
     * @param beneficiaireDTO
     * @return
     * @throws URISyntaxException
     */
    @PutMapping()
    public Mono<ResponseEntity<BeneficiaireDTO>> update(@Valid @RequestBody BeneficiaireDTO beneficiaireDTO) throws URISyntaxException {
        if (beneficiaireDTO.getIdBeneficiaire() == null) {
            throw new RuntimeException("Veuillez fournir toutes les informations nécessaires(ID) SVP.");
        }
        BeneficiaireDTO response = service.update(beneficiaireDTO);
        return Mono.just(ResponseEntity.ok().body(response));
    }

    /**
     * Liste de tous les beneficiaires
     *
     * @return
     */
    @GetMapping()
    public ResponseEntity<List<BeneficiaireDTO>> findAll(@ParameterObject Pageable pageable) {
        final Page<BeneficiaireDTO> page = service.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * Suppression d'un beneficiaire via unID
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
