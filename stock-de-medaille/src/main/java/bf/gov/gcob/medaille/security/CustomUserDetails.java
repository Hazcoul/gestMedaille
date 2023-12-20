package bf.gov.gcob.medaille.security;

import bf.gov.gcob.medaille.model.entities.Privilege;
import bf.gov.gcob.medaille.model.entities.Utilisateur;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public class CustomUserDetails implements UserDetails {

    private static final long serialVersionUID = 7872682205601592675L;

    private Utilisateur user;

    public CustomUserDetails(Utilisateur user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Privilege> roles = new ArrayList<>();
        user.getProfils().forEach(prof -> {
            roles.addAll(prof.getPrivilegeCollection());
        });
        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getCode()))
                .distinct()
                .collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isActif();
    }

    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }

}
