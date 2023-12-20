package bf.gov.gcob.medaille.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Objet View Model pour stocker les informations d’identification d’un
 * utilisateur (à la connexion) .
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public class LoginVM {

    @NotNull
    @Size(min = 1, max = 50)
    private String login;

    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    private Boolean rememberMe;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    @Override
    public String toString() {
        return "LoginVM{"
                + "login='" + login + '\''
                + ", rememberMe=" + rememberMe
                + '}';
    }
}
