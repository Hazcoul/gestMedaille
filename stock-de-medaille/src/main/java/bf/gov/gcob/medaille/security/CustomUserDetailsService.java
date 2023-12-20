package bf.gov.gcob.medaille.security;

import bf.gov.gcob.medaille.model.entities.Utilisateur;
import bf.gov.gcob.medaille.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * recherche le user via login et construit un spring security user
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UtilisateurRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Utilisateur user = usersRepository.findOneByLogin(s).orElse(null);
        CustomUserDetails userPrincipal = new CustomUserDetails(user);
        return userPrincipal;
    }
}
