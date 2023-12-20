/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.gov.gcob.medaille.services.ServiceImpl;

import bf.gov.gcob.medaille.model.entities.Privilege;
import bf.gov.gcob.medaille.model.entities.Profil;
import bf.gov.gcob.medaille.model.entities.Utilisateur;
import bf.gov.gcob.medaille.repository.PrivilegeRepository;
import bf.gov.gcob.medaille.repository.ProfilRepository;
import bf.gov.gcob.medaille.repository.UtilisateurRepository;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

/**
 * Service d'auto initialisation de données
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@Service
public class InitalDataServiceImpl {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private ProfilRepository profileRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Value("${application.resources.static-locations}")
    private String dataPath;

    @Transactional
    public void saveInitAuthorities() throws FileNotFoundException, IOException {
        if (privilegeRepository.findAll().isEmpty()) {
            log.info("Chargement des données privileges... ok");
            ClassLoader classLoader = getClass().getClassLoader();
            File file = ResourceUtils.getFile("classpath:data/authorities.txt");
            try (BufferedReader lineReader = new BufferedReader(new FileReader(file))) {
                String lineText;
                int count = 0;
                List<Privilege> roles = new ArrayList<>();
                while ((lineText = lineReader.readLine()) != null) {
                    String[] data = lineText.split(";");
                    if (count != 0) {
                        Privilege role = new Privilege();
                        role.setId(Long.valueOf(data[0]));
                        role.setCode(data[1]);
                        role.setLibelle(data[2]);
                        roles.add(role);
                    }
                    count++;
                }
                privilegeRepository.saveAll(roles);
            }
        }
    }

    @Transactional
    public void saveInitProfil() {
        if (profileRepository.findAll().isEmpty()) {
            log.info("Chargement des données profiles... ok");
            Set<Privilege> privileges = privilegeRepository.findAll().stream().collect(Collectors.toSet());
            privileges.addAll(privileges);
            Profil profil = new Profil();
            profil.setId(1L);
            profil.setCode("ADMIN");
            profil.setLibelle("Administrateur");
            profil.setNativeProfile(true);
            profil.setPrivilegeCollection(privileges);
            profileRepository.save(profil);
        }
    }

    @Transactional
    public void saveInitUser() {
        if (utilisateurRepository.findAll().isEmpty()) {
            log.info("Chargement des données utilisateur... ok");
            Profil profile = profileRepository.findByCode("ADMIN").orElse(null);
            Utilisateur user = new Utilisateur();
            user.setId(1L);
            user.setLogin("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setNom("admin");
            user.setPrenom("admin");
            user.setMatricule("admin");
            user.setEmail("infos@gcob.bf");
            user.setActif(true);
            user.setProfils(Arrays.asList(profile).stream().collect(Collectors.toSet()));
            utilisateurRepository.save(user);
            log.info("Utilisateur {} crée avec profile {}... ok", user.getNom(), profile.getCode());
        }
    }

}
