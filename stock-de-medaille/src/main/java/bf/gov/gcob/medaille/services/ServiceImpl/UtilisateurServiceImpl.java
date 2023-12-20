package bf.gov.gcob.medaille.services.ServiceImpl;

import bf.gov.gcob.medaille.mapper.UtilisateurMapper;
import bf.gov.gcob.medaille.model.dto.LoginVM;
import bf.gov.gcob.medaille.model.dto.PasswordModif;
import bf.gov.gcob.medaille.model.dto.ResetConnectPaswword;
import bf.gov.gcob.medaille.model.dto.ResetPaswword;
import bf.gov.gcob.medaille.model.dto.UtilisateurDTO;
import bf.gov.gcob.medaille.model.entities.Profil;
import bf.gov.gcob.medaille.model.entities.Utilisateur;
import bf.gov.gcob.medaille.repository.ProfilRepository;
import bf.gov.gcob.medaille.repository.UtilisateurRepository;
import bf.gov.gcob.medaille.security.JwtUtil;
import bf.gov.gcob.medaille.services.UtilisateurService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private ProfilRepository profilRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;
//
//    @Autowired
//    private MailServiceImpl mailService;

    @Autowired
    private UtilisateurMapper mapper;

    @Autowired
    private CacheManager cacheManager;

    @Value("${jwt.token-confirm}")
    private int confirmValidtity;

    @Value("${jwt.token-reset}")
    private int resetValidtity;

    @Value("${application.domain.url}")
    private String urlConfirm;

    @Value("${jwt.base64-secret}")
    private String secret;

    private final String DEFAULT_PASSWORD = "S!g@c!L:2023";

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public UtilisateurDTO saveUser(UtilisateurDTO utilisateurDTO, ServerHttpRequest request) {
        log.info("Creation d'un utilisateur : {}", utilisateurDTO);
        String authorizationHeader = request.getHeaders().getFirst("Authorization");//request.getHeader("Authorization");

        String token = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();
        String email = utilisateurDTO.getEmail();
        String username = utilisateurDTO.getLogin();

        Utilisateur userWithEmail
                    = utilisateurRepository.findOneByEmailIgnoreCase(utilisateurDTO.getEmail()).orElse(null);
        if (userWithEmail != null) {
            throw new RuntimeException("L'utilisateur avec l'adresse mail " + email + " existe deja.");
        }

        Utilisateur userWithUsername
                    = utilisateurRepository.findOneByLogin(utilisateurDTO.getLogin()).orElse(null);
        if (userWithUsername != null) {
            throw new RuntimeException("L'utilisateur avec  le nom d'utilisateur " + username + " existe deja");
        }

        LocalDateTime tokenConfirmValidit = LocalDateTime.now().plusSeconds(confirmValidtity);
        LocalDateTime resetTokenConfirm = LocalDateTime.now().plusSeconds(resetValidtity);

        utilisateurDTO.setActif(false);
        utilisateurDTO.setActivationKey(token);
        utilisateurDTO.setConfirmationExpireDate(tokenConfirmValidit);
        utilisateurDTO.setResetExpireDate(resetTokenConfirm);
        String confirmUrl;
        String pass;
        if (authorizationHeader == null) {
            pass = passwordEncoder.encode(this.DEFAULT_PASSWORD);
            confirmUrl = this.getConfirmationUrl(request, token, urlConfirm.concat("/account/confirme"));
        } else {
            pass = passwordEncoder.encode(password);
            confirmUrl = this.getConfirmationUrl(request, token, urlConfirm.concat("/account/confirme-user"));
        }
        Utilisateur utilisateur = mapper.toCustomEntity(utilisateurDTO);
        utilisateur.setPassword(pass);
        utilisateur = utilisateurRepository.save(utilisateur);

        //on s'assure que l'email est valide. COMMENT CAR DEPENDANCE MAIL ET SERVICE NON AJOUTEES
//        if (mailService.isEmailValid(email)) {
//            String emailSubject = "Activation de votre compte SIGACIL";
//            String emailBody = "Cliquez sur ce lien pour activer votre compte SIGACIL: <a href=\""
//                    + confirmUrl + "\">activer votre compte</a><br><br>";
//            mailService.sendEmail(email, emailSubject, emailBody);
//        } else {
//            throw new RuntimeException("L'email de l'utilisateur n'est pas valide.");
//        }
        this.clearUsersCaches(utilisateur);
        return mapper.toCustomDto(utilisateur);
    }

    @Override
    public UtilisateurDTO updateUser(final Long id, final UtilisateurDTO utilisateurDTO) {
        log.info("Mise à jour d'un utilisateur : {} et {}", id, utilisateurDTO);
        Utilisateur existingUser = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("L'utilisateur avec l'id " + id + " n'existe pas"));

        if (utilisateurDTO.getEmail() != null) {
            existingUser.setEmail(utilisateurDTO.getEmail());
        }
        if (utilisateurDTO.getLogin() != null) {
            existingUser.setLogin(utilisateurDTO.getLogin());
        }
        if (utilisateurDTO.getNom() != null) {
            existingUser.setNom(utilisateurDTO.getNom());
        }

        if (utilisateurDTO.getPrenom() != null) {
            existingUser.setPrenom(utilisateurDTO.getPrenom());
        }
        if (utilisateurDTO.getContact() != null) {
            existingUser.setContact(utilisateurDTO.getContact());
        }

        Utilisateur updatedUser = utilisateurRepository.save(existingUser);
        return mapper.toCustomDto(updatedUser);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public UtilisateurDTO updateUserWithProfils(final Long id, final UtilisateurDTO utilisateurDTO) {
        log.info("Mise à jour d'un utilisateur y compris les profils : {} et {}", id, utilisateurDTO);
        Optional<Utilisateur> existingUser = Optional.of(utilisateurRepository
                .findById(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(user -> {
                    this.clearUsersCaches(user);
                    user.setMatricule(utilisateurDTO.getMatricule().toLowerCase());
                    user.setNom(utilisateurDTO.getNom());
                    user.setPrenom(utilisateurDTO.getPrenom());
                    user.setContact(utilisateurDTO.getContact());
                    user.setEmail(utilisateurDTO.getEmail());
                    user.setActif(utilisateurDTO.isActif());
                    Set<Profil> managedProfiles = user.getProfils();
                    managedProfiles.clear();
                    utilisateurDTO.getProfils().stream()
                            .map(profilRepository::findByLibelle)
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .forEach(managedProfiles::add);
                    this.clearUsersCaches(user);
                    log.info("Infos changées pour l'tilisateur: {}", user);
                    return user;
                });
        return mapper.toCustomDto(existingUser.get());
    }

    private String getConfirmationUrl(ServerHttpRequest request, String token, String url) {
        log.info("Reconstitution de l'uri de confirmation");
        return url + "?token=" + token;
    }

    /**
     * Méthode d'activation du compte utilisateur à la suite de l'envoye du mail
     *
     * @param token
     * @return
     */
    @Override
    public String processConfirmationForm(final String token) {
        log.info("Init activation de compte utilisateur, token = {}", token);
        Utilisateur optionalUser = utilisateurRepository.findOneByActivationKey(token).orElse(null);
        LocalDateTime confirmTime = LocalDateTime.now();

        if (optionalUser == null) {
            throw new RuntimeException("Le lien de confirmation n'est pas valide");
        }

        LocalDateTime userConfirmTime = optionalUser.getConfirmationExpireDate();
        if (!confirmTime.isBefore(userConfirmTime)) {
            throw new RuntimeException("Le lien de confirmation a expire");
        }

        optionalUser.setActif(true);
        optionalUser.setActivationKey(null);
        String email = optionalUser.getEmail();
        String emailSubject = "Compte activé";
        String emailBody = "Félicitations, votre compte a été activé. Visitez les services offerts par la CIL.";
        utilisateurRepository.save(optionalUser);
//        if (mailService.isEmailValid(email)) {
//            mailService.sendEmail(email, emailSubject, emailBody);
//        } else {
//            throw new RuntimeException("L'email de l'utilisateur n'est pas valide.");
//        }

        return "Votre compte est activé";
    }

    @Override
    public String processAdminConfirm(final PasswordModif passwordModif) {
        log.info("Check et activation par new password du compte utilisateur");
        Utilisateur optionalUser = utilisateurRepository.findOneByActivationKey(passwordModif.getToken()).orElse(null);
        LocalDateTime confirmTime = LocalDateTime.now();

        if (optionalUser == null) {
            throw new RuntimeException("Le lien de confirmation n'est pas valide");
        }

        LocalDateTime userConfirmTime = optionalUser.getConfirmationExpireDate();
        if (!confirmTime.isBefore(userConfirmTime)) {
            throw new RuntimeException("Le lien de confirmation a expire");
        }

        String password = passwordEncoder.encode(passwordModif.getPassword());
        optionalUser.setActif(true);
        optionalUser.setActivationKey(null);
        optionalUser.setPassword(password);
        String email = optionalUser.getEmail();
        String emailSubject = "Compte activé";
        String emailBody = "Félicitations, votre compte a été activé. Visitez les services offerts par la CIL.";
        utilisateurRepository.save(optionalUser);
//        if (mailService.isEmailValid(email)) {
//            mailService.sendEmail(email, emailSubject, emailBody);
//        } else {
//            throw new RuntimeException("L'email de l'utilisateur n'est pas valide.");
//        }

        return "Votre compte est activé";
    }

    @Override
    public String generateToken(String username, boolean rememberMe) {
        log.info("Generation de token pour : {}", username);
        return jwtUtil.generateToken(username, rememberMe);
    }

//    public void validateToken(String token) {
//        jwtService.validateToken(token);
//    }
    @Override
    public UtilisateurDTO validateToken(ServerHttpRequest request) {
        log.info("Check du token fourni et recherche de l'utilisateur concerné");
        String authorizationHeader = request.getHeaders().getFirst("Authorization");//request.getHeader("Authorization");
        String token = null;
        String[] parts = authorizationHeader.split(" ");
        if (parts.length == 2 && parts[0].equalsIgnoreCase("Bearer")) {
            token = parts[1];
            // Faites quelque chose avec le jeton d'authentification
        }

        byte[] signingKey = Base64.getDecoder().decode(secret.getBytes());

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(signingKey))
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();

        //Recherche de l'utilisateur correspondant aux infos du token fourni
        Utilisateur utilisateur = utilisateurRepository.findOneByLogin(username).orElse(null);
        UtilisateurDTO user = mapper.toCustomDto(utilisateur);
        // Set other user properties from token claims if needed
        return user;
    }

    @Override
    public String resendConfirmToken(String to, ServerHttpRequest request) {
        log.info("Renvoi de mail d'activation de compte utilisateur");
        String authorizationHeader = request.getHeaders().getFirst("Authorization");
        String token = UUID.randomUUID().toString();
        Utilisateur optionalUser = utilisateurRepository.findOneByEmailIgnoreCase(to).orElse(null);

        String email = optionalUser.getEmail();

        String confirmUrl;
        if (authorizationHeader == null) {
            confirmUrl = this.getConfirmationUrl(request, token, urlConfirm.concat("/account/confirme"));
        } else {
            confirmUrl = this.getConfirmationUrl(request, token, urlConfirm.concat("/account/confirme-user"));
        }
        String emailSubject = "Activation de votre compte SIGACIL";
        String emailBody = "Cliquez sur ce lien pour activer votre compte SIGACIL: <a href=\""
                + confirmUrl + "\">activer votre compte</a><br><br>";
        LocalDateTime expirationDateTime = LocalDateTime.now().plusSeconds(1);
        optionalUser.setActivationKey(token);
        optionalUser.setConfirmationExpireDate(expirationDateTime);
        utilisateurRepository.save(optionalUser);
//        if (mailService.isEmailValid(email)) {
//            mailService.sendEmail(email, emailSubject, emailBody);
//        } else {
//            throw new RuntimeException("L'email de l'utilisateur n'est pas valide.");
//        }
        return "Lien de confirmation renvoyé";

    }

    @Override
    public String resendPasswordToken(final String to, ServerHttpRequest request) {
        log.info("Renvoi de mail de reset password via : {}", to);
        String authorizationHeader = request.getHeaders().getFirst("Authorization");
        String token = UUID.randomUUID().toString();
        LocalDateTime resetValidity = LocalDateTime.now().plusSeconds(resetValidtity);
        Utilisateur optionalUser = utilisateurRepository.findOneByEmailIgnoreCase(to).orElse(null);

        if (optionalUser == null) {
            throw new RuntimeException("Le compte avec l'adresse mail " + to + " n'existe pas");
        }

        if (!optionalUser.isActif()) {
            throw new RuntimeException("Le compte avec l'adresse mail " + to + " n'est pas encore validé");
        }

        String confirmUrl;
        if (authorizationHeader == null) {
            confirmUrl = this.getConfirmationUrl(request, token, urlConfirm.concat("/account/confirme"));
        } else {
            confirmUrl = this.getConfirmationUrl(request, token, urlConfirm.concat("/account/confirme-user"));
        }
        String emailSubject = "Demande de réinitialisation de mot de passe";
        String emailBody = "Cliquez sur ce lien pour initialiser votre mot de passe: <a href=\""
                + confirmUrl + "\">initialiser votre mot de passe</a><br><br>";
        optionalUser.setResetKey(token);
        optionalUser.setResetExpireDate(resetValidity);
        utilisateurRepository.save(optionalUser);
//        if (mailService.isEmailValid(to)) {
//            mailService.sendEmail(to, emailSubject, emailBody);
//        } else {
//            throw new RuntimeException("L'email de l'utilisateur n'est pas valide.");
//        }
        return "Lien de réinitialisation de mot de passe envoyé";

    }

    @Override
    public String processResetPassword(final ResetPaswword resetPaswword) {
        log.info("Reinitialise un compte via le token mailé et le nouveau password : {}", resetPaswword);
        Utilisateur optionalUser = utilisateurRepository.findOneByResetKey(resetPaswword.getToken()).orElse(null);
        if (optionalUser == null) {
            throw new RuntimeException("Le lien de confirmation n'est pas valide");
        }
        LocalDateTime confirmTime = LocalDateTime.now();
        if (!confirmTime.isBefore(optionalUser.getResetExpireDate())) {
            throw new RuntimeException("Le lien de confirmation a expire");
        }

        if (!resetPaswword.getNewPassword().equals(resetPaswword.getConfirmNewPassword())) {
            throw new RuntimeException("Le lien de confirmation n'est pas valide");
        }
        String password = passwordEncoder.encode(resetPaswword.getNewPassword());
        Utilisateur user = optionalUser;
        user.setResetKey(null);
        user.setPassword(password);
        String email = optionalUser.getEmail();
        String emailSubject = "Mot de passe modifié";
        String emailBody = "Votre de mot de passe a été modifié avec succès! Si vous n'êtes pas à l'origine de cette action, "
                + "veuillez nous contacter.";
        utilisateurRepository.save(user);
//        if (mailService.isEmailValid(email)) {
//            mailService.sendEmail(email, emailSubject, emailBody);
//        } else {
//            throw new RuntimeException("L'email de l'utilisateur n'est pas valide.");
//        }
        return "Le mot de passe a été changé";
    }

    @Override
    public String processResetConnectPassword(final ResetConnectPaswword resetPaswword, ServerHttpRequest request) {
        log.info("Modifier l'ancien password par un nouveau : {}", resetPaswword);
        Utilisateur optionalUser = this.getCurrentUser(request);

        if (optionalUser == null) {
            throw new RuntimeException("Cet utilisateur n'existe pas");
        }
        if (!passwordEncoder.matches(resetPaswword.getOldPassword(), optionalUser.getPassword())) {
            throw new RuntimeException("Votre ancien mot de passe n'est pas correct!");
        }
        String password = passwordEncoder.encode(resetPaswword.getNewPassword());
        Utilisateur user = optionalUser;
        user.setResetKey(null);
        user.setPassword(password);
        String email = optionalUser.getEmail();
        String emailSubject = "Mot de passe modifié";
        String emailBody = "Votre de mot de passe a été modifié avec succès! Si vous n'êtes pas à l'origine de cette action, "
                + "veuillez nous contacter.";
        utilisateurRepository.save(user);
//        if (mailService.isEmailValid(email)) {
//            mailService.sendEmail(email, emailSubject, emailBody);
//        } else {
//            throw new RuntimeException("L'email de l'utilisateur n'est pas valide.");
//        }
        return "Le mot de passe a été changé";
    }

    @Override
    public List<UtilisateurDTO> findAll() {
        log.info("Liste tous les utilisateurs");
        return utilisateurRepository.findAll()
                .stream()
                .map(u -> mapper.toCustomDto(u))
                .collect(Collectors.toList());
    }

    private Utilisateur getCurrentUser(ServerHttpRequest request) {
        log.info("Obtenir l'utilisateur courant");
        String token = request.getHeaders().getFirst("Authorization").substring(7);
        String username = Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody().getSubject();
        Utilisateur utilisateur = utilisateurRepository.findOneByLogin(username).orElse(null);
        return utilisateur;
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public Boolean isUserGood(LoginVM authRequest) {
        Boolean isCorrect = true;

        Optional<Utilisateur> optionalUser = utilisateurRepository.findOneByLogin(authRequest.getLogin());

        if (optionalUser.isPresent()) {
            String currentEncryptedPassword = optionalUser.get().getPassword();
            if (!passwordEncoder.matches(authRequest.getPassword(), currentEncryptedPassword)) {
                isCorrect = false;
            }
        } else {
            isCorrect = false;
        }
        return isCorrect;
    }

    @Override
    public Boolean isUserActif(LoginVM authRequest) {
        Optional<Utilisateur> optionalUser = utilisateurRepository.findOneByLogin(authRequest.getLogin());
        if (optionalUser.isPresent()) {
            return optionalUser.get().isActif();
        } else {
            return false;
        }
    }

    private void clearUsersCaches(Utilisateur user) {
        Objects.requireNonNull(cacheManager.getCache(UtilisateurRepository.USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
    }

    @Override
    public Optional<Utilisateur> getById(Long id) {
        return utilisateurRepository.findById(id);
    }

}
