package bf.gov.gcob.medaille.model.dto;

import bf.gov.gcob.medaille.config.Constants;
import bf.gov.gcob.medaille.model.entities.Privilege;
import bf.gov.gcob.medaille.model.entities.Utilisateur;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Getter
@Setter
@ToString
public class UtilisateurDTO {

    private Long id;

    @NotBlank
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String matricule;

    @Size(max = 50)
    private String nom;

    @Size(max = 50)
    private String prenom;

    private String fonction;//la fonction de l'utilisateur dans son service

    private String titreHonorifique;//la distinction (ou titre honorifique) la plus elevee actuelle du user

    private String contact;

    private boolean actif = false;

    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    private boolean deleted = false;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private String activationKey;

    private String resetKey;

    private Instant resetDate;

    private LocalDateTime confirmationExpireDate;

    private LocalDateTime resetExpireDate;

    private Set<String> privileges;

    private Set<String> profils;

    //========================= CONSTRUCTORS ===========================
    public UtilisateurDTO() {
    }

    public UtilisateurDTO(Utilisateur user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.matricule = user.getMatricule();
        this.nom = user.getNom();
        this.prenom = user.getPrenom();
        this.fonction = user.getFonction();
        this.titreHonorifique = user.getTitreHonorifique();
        this.contact = user.getContact();
        this.email = user.getEmail();
        this.actif = user.isActif();
        this.deleted = user.isDeleted();
        this.createdBy = user.getCreatedBy();
        this.createdDate = user.getCreatedDate();
        this.lastModifiedBy = user.getLastModifiedBy();
        this.lastModifiedDate = user.getLastModifiedDate();
        Set<Privilege> actions = new HashSet<>();
        Set<String> profilsBuilder = new HashSet<>();
        user.getProfils().stream().forEach(r -> {
            profilsBuilder.add(r.getLibelle());
            actions.addAll(r.getPrivilegeCollection());
        });
        this.profils = profilsBuilder;
        this.privileges = actions.stream()
                .map(Privilege::getCode)
                .collect(Collectors.toSet());

    }
}
