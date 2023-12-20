package bf.gov.gcob.medaille.mapper;

import bf.gov.gcob.medaille.model.dto.UtilisateurDTO;
import bf.gov.gcob.medaille.model.entities.Privilege;
import bf.gov.gcob.medaille.model.entities.Profil;
import bf.gov.gcob.medaille.model.entities.Utilisateur;
import bf.gov.gcob.medaille.repository.ProfilRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Service
public class UtilisateurMapper {

    @Autowired
    private ProfilRepository profilRepository;

    /**
     *
     * @param utilisateur
     * @return
     */
    public UtilisateurDTO toCustomDto(Utilisateur utilisateur) {
        if (utilisateur == null) {
            return null;
        }

        UtilisateurDTO dto = new UtilisateurDTO();

        dto.setId(utilisateur.getId());
        dto.setActif(utilisateur.isActif());
        dto.setActivationKey(utilisateur.getActivationKey());
        dto.setConfirmationExpireDate(utilisateur.getConfirmationExpireDate());
        dto.setContact(utilisateur.getContact());
        dto.setDeleted(utilisateur.isDeleted());
        dto.setEmail(utilisateur.getEmail());
        dto.setLogin(utilisateur.getLogin());
        dto.setMatricule(utilisateur.getMatricule());
        dto.setNom(utilisateur.getNom());
        dto.setPrenom(utilisateur.getPrenom());
        dto.setProfils(this.mapProfilsToStrings(utilisateur));
        dto.setPrivileges(this.mapPrivilegesToStrings(utilisateur));
        dto.setResetDate(utilisateur.getResetDate());
        dto.setResetExpireDate(utilisateur.getResetExpireDate());

        return dto;
    }

    /**
     *
     * @param utilisateurDTO
     * @return
     */
    public Utilisateur toCustomEntity(UtilisateurDTO utilisateurDTO) {
        if (utilisateurDTO == null) {
            return null;
        }

        Utilisateur entity = new Utilisateur();

        entity.setId(utilisateurDTO.getId());
        entity.setActif(utilisateurDTO.isActif());
        entity.setActivationKey(utilisateurDTO.getActivationKey());
        entity.setConfirmationExpireDate(utilisateurDTO.getConfirmationExpireDate());
        entity.setContact(utilisateurDTO.getContact());
        entity.setEmail(utilisateurDTO.getEmail());
        entity.setLogin(utilisateurDTO.getLogin());
        entity.setMatricule(utilisateurDTO.getMatricule());
        entity.setNom(utilisateurDTO.getNom());
        entity.setPrenom(utilisateurDTO.getPrenom());
        entity.setProfils(this.mapStringsToProfils(utilisateurDTO));
        entity.setResetDate(utilisateurDTO.getResetDate());
        entity.setResetExpireDate(utilisateurDTO.getResetExpireDate());
        entity.setResetKey(utilisateurDTO.getResetKey());

        return entity;
    }

//============================ PRIVATE UTILS =========================================
    private Set<String> mapProfilsToStrings(Utilisateur utilisateur) {
        if (utilisateur == null || utilisateur.getProfils().isEmpty()) {
            return null;
        }

        Set<String> profilsLibelles = new HashSet<>();

        utilisateur.getProfils().stream().forEach(p -> {
            profilsLibelles.add(p.getLibelle());
        });

        return profilsLibelles;
    }

    private Set<Profil> mapStringsToProfils(UtilisateurDTO utilisateurDTO) {
        if (utilisateurDTO == null || utilisateurDTO.getProfils() == null) {
            return null;
        }

        Set<Profil> profiles = new HashSet<>();

        profiles = utilisateurDTO.getProfils().stream()
                .map(profilRepository::findByLibelle)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        return profiles;
    }

    private Set<String> mapPrivilegesToStrings(Utilisateur utilisateur) {
        if (utilisateur == null) {
            return null;
        }

        Set<Privilege> actions = new HashSet<>();
        Set<String> privilegesLibelles = new HashSet<>();

        utilisateur.getProfils().stream().forEach(r -> {
            actions.addAll(r.getPrivilegeCollection());
        });

        privilegesLibelles = actions.stream()
                .map(Privilege::getCode)
                .collect(Collectors.toSet());

        return privilegesLibelles;
    }

}
