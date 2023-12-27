package bf.gov.gcob.medaille.controller;

import bf.gov.gcob.medaille.config.Constants;
import bf.gov.gcob.medaille.model.dto.ProfilDTO;
import bf.gov.gcob.medaille.services.ProfilService;
import jakarta.validation.Valid;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@RestController
@RequestMapping("/api/auth/profils")
public class ProfilController {

    private final ProfilService profilService;

    public ProfilController(ProfilService profilService) {
        this.profilService = profilService;
    }

    /**
     *
     * @param profilDTO
     * @return
     */
    @PostMapping("/new")
    public ResponseEntity<ProfilDTO> save(@Valid @RequestBody ProfilDTO profilDTO) {
        return new ResponseEntity<>(profilService.save(profilDTO), HttpStatus.CREATED);
    }

    /**
     *
     * @return
     */
    @GetMapping("/list")
    public List<ProfilDTO> findAll() {
        return profilService.findAll();
    }

    /**
     *
     * @param profileDTO
     */
    @DeleteMapping
    public void delete(@RequestBody Long idProfil) {
        profilService.delete(idProfil);
    }

    /**
     *
     * @param id : id d'un profile
     * @return
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<ProfilDTO> get(@PathVariable(name = "id", required = true) Long id) {
        ProfilDTO result = profilService.findById(id);
        return ResponseEntity.ok().body(result);
    }

    /**
     *
     * @param code : code d'un profil
     * @return
     */
    @GetMapping("/{code:" + Constants.LOGIN_REGEX + "}")
    public ResponseEntity<ProfilDTO> getByCode(@PathVariable String code) {
        ProfilDTO profile = profilService.findByCode(code);
        return ResponseEntity.ok().body(profile);
    }

    /**
     * liste les libelles de tous les profiles
     *
     * @return
     */
    @GetMapping("/list/libelles")
    public ResponseEntity<List<String>> getAllProfilsLibelles() {
        List<String> response = profilService.getProfiles();
        return ResponseEntity.ok().body(response);
    }

}
