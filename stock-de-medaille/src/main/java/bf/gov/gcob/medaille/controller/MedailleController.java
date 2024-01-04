/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.gov.gcob.medaille.controller;

import bf.gov.gcob.medaille.exception.CreateNewElementException;
import bf.gov.gcob.medaille.model.dto.MedailleDTO;
import bf.gov.gcob.medaille.services.MedailleService;
import bf.gov.gcob.medaille.utils.web.PaginationUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reactor.core.publisher.Mono;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/medailles")
public class MedailleController {

    private MedailleService service;

    /**
     * Creation d'une medaille avec upload d'image catalogue
     *
     * @param jsonRequest : objet dto stringfied de la medaille
     * @param photo : image de la medaille
     * @return un dto
     * @throws URISyntaxException
     * @throws JsonProcessingException
     */
    //@PreAuthorize("hasAnyAuthority(\"" + Constants.ADMIN + "\",\"" + Constants.GEST + "\")")
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Mono<ResponseEntity<MedailleDTO>> create(
            @Valid @RequestPart(value = "data", required = true) MedailleDTO medailleDTO,
            @RequestPart(value = "photo", required = true) MultipartFile photo) throws URISyntaxException, JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        MedailleDTO medailleDTO = objectMapper.readValue(jsonRequest, MedailleDTO.class);

        if (medailleDTO.getIdMedaille() != null) {
            throw new CreateNewElementException();
        }
        if (photo == null) {
            throw new RuntimeException("Veuillez charger une image catalogue de la médaille SVP.");
        }
        MedailleDTO response = service.create(medailleDTO, photo);
        return Mono.just(ResponseEntity.created(new URI("/api/medailles/" + response.getIdMedaille())).body(response));
    }

    /**
     * Modification de donnés d'une medaille; sans toucher à l'image catalogue
     *
     * @param medailleDTO : objet dto de la medaille
     * @return
     * @throws URISyntaxException
     */
    @PutMapping()
    public Mono<ResponseEntity<MedailleDTO>> update(@Valid @RequestBody MedailleDTO medailleDTO) throws URISyntaxException {
        if (medailleDTO.getIdMedaille() == null) {
            throw new RuntimeException("Veuillez fournir toutes les informations nécessaires(ID) SVP.");
        }
        MedailleDTO response = service.update(medailleDTO);
        return Mono.just(ResponseEntity.ok().body(response));
    }

    /**
     * Mise à jour d'une image de medaille
     *
     * @param idMedaille : id de la medaille
     * @param photoFile : fichier image de la medaille
     * @return
     * @throws URISyntaxException
     * @throws JsonProcessingException
     */
    @PostMapping(path = "/update-image", consumes = {MediaType.MULTIPART_MIXED_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Mono<ResponseEntity<MedailleDTO>> updateImagecatalogue(@Valid @RequestPart(value = "id", required = true) String idMedaille,
            @RequestPart(value = "photo", required = true) MultipartFile photoFile) throws URISyntaxException {
        if (photoFile == null) {
            throw new RuntimeException("Veuillez joindre l'image catalogue de la medaille SVP.");
        }
        MedailleDTO response = service.updateImagecatalogue(Long.valueOf(idMedaille), photoFile);
        return Mono.just(ResponseEntity.ok().body(response));
    }

    /**
     * Liste de toutes les medailles
     *
     * @return
     */
    @GetMapping()
    public Mono<ResponseEntity<List<MedailleDTO>>> findAll() throws IOException {
        List<MedailleDTO> response = service.findAll();
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), new PageImpl<>(response));
        return Mono.just(ResponseEntity.ok().headers(headers).body(response));
    }

    /**
     * Lis une image catalogue de medaille depuis le server de stockage
     *
     * @param lienImage
     * @return
     */
    @GetMapping("/{lien}")
    public Mono<ResponseEntity<byte[]>> getImageMedaille(@PathVariable(name = "lien", required = true) String lienImage) throws IOException {
        byte[] response = service.getImageMedaille(lienImage);
        return Mono.just(ResponseEntity.ok().body(response));
    }

    /**
     * Suppression d'une medaille via unID
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
