/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.gov.gcob.medaille.controller;

import bf.gov.gcob.medaille.exception.CreateNewElementException;
import bf.gov.gcob.medaille.model.dto.MedailleDTO;
import bf.gov.gcob.medaille.services.MedailleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/medailles")
public class MedailleController {

    private MedailleService service;

    /**
     *
     * @param jsonRequest : objet dto de la medaille
     * @param photo : image de la medaille
     * @return un dto
     * @throws URISyntaxException
     * @throws JsonProcessingException
     */
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<MedailleDTO> create(
            @Valid @RequestPart(value = "data", required = true) String jsonRequest,
            @RequestPart(value = "photo", required = true) MultipartFile photo) throws URISyntaxException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        MedailleDTO medailleDTO = objectMapper.readValue(jsonRequest, MedailleDTO.class);

        if (medailleDTO.getIdMedaille() != null) {
            throw new CreateNewElementException();
        }
        if (photo == null) {
            throw new RuntimeException("Veuillez charger une image catalogue de la médaille SVP.");
        }
        MedailleDTO response = service.create(medailleDTO, photo);
        return ResponseEntity.created(new URI("/api/medailles/" + response.getIdMedaille())).body(response);
    }

}
