/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.gov.gcob.medaille.services;

import bf.gov.gcob.medaille.model.dto.LoginVM;
import bf.gov.gcob.medaille.model.dto.PasswordModif;
import bf.gov.gcob.medaille.model.dto.ResetConnectPaswword;
import bf.gov.gcob.medaille.model.dto.ResetPaswword;
import bf.gov.gcob.medaille.model.dto.UtilisateurDTO;
import java.util.List;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface UtilisateurService {

    UtilisateurDTO saveUser(UtilisateurDTO utilisateurDTO, ServerHttpRequest request);

    UtilisateurDTO updateUser(final Long id, final UtilisateurDTO utilisateurDTO);

    UtilisateurDTO updateUserWithProfils(final Long id, final UtilisateurDTO utilisateurDTO);

    Boolean isUserGood(LoginVM authRequest);

    Boolean isUserActif(LoginVM authRequest);

    String generateToken(String username, boolean rememberMe);

    String processConfirmationForm(final String token);

    String processAdminConfirm(final PasswordModif passwordModif);

    UtilisateurDTO validateToken(ServerHttpRequest request);//il ya souci avec le HttpServletRequest

    String resendConfirmToken(String to, ServerHttpRequest request);

    String resendPasswordToken(final String to, ServerHttpRequest request);

    String processResetPassword(final ResetPaswword resetPaswword);

    String processResetConnectPassword(final ResetConnectPaswword resetPaswword, ServerHttpRequest request);

    List<UtilisateurDTO> findAll();

    UtilisateurDTO getById(Long id);
}
