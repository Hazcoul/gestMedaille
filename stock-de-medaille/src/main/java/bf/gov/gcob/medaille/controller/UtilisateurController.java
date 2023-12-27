/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.gov.gcob.medaille.controller;

import bf.gov.gcob.medaille.model.dto.AuthenticationResponse;
import bf.gov.gcob.medaille.model.dto.LoginVM;
import bf.gov.gcob.medaille.model.dto.PasswordModif;
import bf.gov.gcob.medaille.model.dto.ResetConnectPaswword;
import bf.gov.gcob.medaille.model.dto.ResetPaswword;
import bf.gov.gcob.medaille.model.dto.UtilisateurDTO;
import bf.gov.gcob.medaille.security.JwtAuthenticationManager;
import bf.gov.gcob.medaille.security.JwtUtil;
import bf.gov.gcob.medaille.services.UtilisateurService;
import bf.gov.gcob.medaille.utils.web.PaginationUtil;
import java.io.IOException;
import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reactor.core.publisher.Mono;

/**
 * Toutes ressources liées à l'utilisateur
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth/utilisateurs")
public class UtilisateurController {

    private UtilisateurService service;

    private JwtAuthenticationManager authenticationManager;

    private JwtUtil jwtUtil;

    public UtilisateurController(UtilisateurService service,
            JwtAuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.service = service;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    /**
     * creation d'un user
     *
     * @param user : json data du user
     * @param request
     * @return
     */
    @PostMapping("/new")
    public ResponseEntity<UtilisateurDTO> addNewUser(@RequestBody UtilisateurDTO user, ServerHttpRequest request) {
        return new ResponseEntity<>(service.saveUser(user, request), HttpStatus.CREATED);
    }

    /**
     * Modifie juste des infos basic (nom, prenom, email, login, fonction) du
     * user
     *
     * @param id
     * @param utilisateurDTO
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<UtilisateurDTO> updateUser(
            @PathVariable final Long id,
            @RequestBody final UtilisateurDTO utilisateurDTO) {
        return new ResponseEntity<>(service.updateUser(id, utilisateurDTO), HttpStatus.CREATED);
    }

    /**
     * Modifie des infos du user y compris ses profiles
     *
     * @param id : id du user sujet de modification
     * @param utilisateurDTO : donnees json de modif
     * @return
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<UtilisateurDTO> updateUserWithProfils(
            @PathVariable final Long id,
            @RequestBody final UtilisateurDTO utilisateurDTO) {
        return new ResponseEntity<>(service.updateUserWithProfils(id, utilisateurDTO), HttpStatus.CREATED);
    }

    /**
     * api d'authentification
     *
     * @param authRequest
     * @param request
     * @return
     */
    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody LoginVM authRequest) {
        if (!service.isUserGood(authRequest)) {
            throw new RuntimeException("Les informations d'authentification sont erronées!");
        }

        if (!service.isUserActif(authRequest)) {
            throw new RuntimeException("Le compte " + authRequest.getLogin() + " n'est pas activé");
        }
        String jwt = jwtUtil.generateToken(authRequest.getLogin(), false);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + jwt)
                .body(new AuthenticationResponse(jwt));
    }

    /**
     * endpoint d'activation du compte utilisateur
     *
     * @param token
     * @return string
     */
    @GetMapping("/confirm")
    public ResponseEntity<String> processConfirmationForm(final @RequestParam("token") String token) {
        return new ResponseEntity<>(service.processConfirmationForm(token), HttpStatus.OK);

    }

    /**
     * endpoint d'activation du compte utilisateur
     *
     * @param passwordModif
     * @return string
     */
    @PostMapping("/confirm-user")
    public ResponseEntity<String> processAdminConfirm(final @RequestBody PasswordModif passwordModif) {
        return new ResponseEntity<>(service.processAdminConfirm(passwordModif), HttpStatus.OK);

    }

    /**
     *
     * @param request
     * @return
     */
    @GetMapping("/validate")
    public UtilisateurDTO validateToken(ServerHttpRequest request) throws IOException {
        //System.out.println("_______________ request : " + request.getHeaders().getFirst("Authorization"));
        if (request.getHeaders().getFirst("Authorization") == null) {//pour muetter le logout frontend
            return null;
        }
        service.validateToken(request);
        return service.validateToken(request);
    }

    /**
     * On renvoi un Lien (par mail) de confirmation/activation compte
     *
     * @param to
     * @param request
     * @return
     */
    @PostMapping("/reset-confirm-token")
    public ResponseEntity<String> resendConfirmToken(@RequestParam("to") String to, ServerHttpRequest request) {
        return new ResponseEntity<>(service.resendConfirmToken(to, request), HttpStatus.CREATED);
    }

    /**
     * On renvoi un Lien (par mail) pour reinitialisation de password
     *
     * @param to
     * @param request
     * @return
     */
    @PostMapping("/reset-password-token")
    public ResponseEntity<String> resendPasswordToken(@RequestParam("to") String to, ServerHttpRequest request) {
        return new ResponseEntity<>(service.resendPasswordToken(to, request), HttpStatus.CREATED);

    }

    /**
     * on reinitialise le mot de passe en verifiant le token generé par mail et
     * le nouveau password renseigné
     *
     * @param resetPaswword
     * @return
     */
    @PostMapping("/reset-password")
    public ResponseEntity<String> processResetPassword(final @RequestBody ResetPaswword resetPaswword) {
        return new ResponseEntity<>(service.processResetPassword(resetPaswword), HttpStatus.OK);
    }

    /**
     * On recueille l'ancien et le nouveau password et effectue le changement de
     * password
     *
     * @param resetPassword
     * @param request
     * @return
     */
    @PostMapping("/reset-connect-password")
    public ResponseEntity<String> processResetConnectPassword(final @RequestBody ResetConnectPaswword resetPassword, ServerHttpRequest request) {
        return new ResponseEntity<>(service.processResetConnectPassword(resetPassword, request), HttpStatus.OK);
    }

    /**
     * On liste tous les utilisateurs avec leurs profils respectifs
     *
     * @return
     */
    @GetMapping(path = "/list")
    public Mono<ResponseEntity<List<UtilisateurDTO>>> findAll() {
        List<UtilisateurDTO> response = service.findAll();
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), new PageImpl<>(response));
        return Mono.just(ResponseEntity.ok().headers(headers).body(response));
    }

    /**
     * On recherche un utilisateur via un ID
     *
     * @param idUser
     * @return
     */
    @GetMapping(path = "/{id}")
    public Mono<ResponseEntity<UtilisateurDTO>> findById(@PathVariable(name = "id", required = true) Long idUser) {
        UtilisateurDTO response = service.getById(idUser);
        return Mono.just(ResponseEntity.ok().body(response));
    }
}
