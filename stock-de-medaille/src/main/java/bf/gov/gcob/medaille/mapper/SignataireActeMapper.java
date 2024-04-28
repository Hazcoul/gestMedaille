/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.gov.gcob.medaille.mapper;

import bf.gov.gcob.medaille.model.dto.SignataireActeDTO;
import bf.gov.gcob.medaille.model.entities.SignataireActe;
import bf.gov.gcob.medaille.model.enums.EFonctionSignataire;
import bf.gov.gcob.medaille.model.enums.ENatureActe;
import java.util.Objects;
import org.springframework.stereotype.Component;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Component
public class SignataireActeMapper extends AbstractBaseMapper {

    public SignataireActeDTO toDTO(SignataireActe entity) {
        if (Objects.isNull(entity)) {
            return null;
        }

        SignataireActeDTO dto = new SignataireActeDTO();
        dto.setActif(entity.getActif());
        dto.setIdSignataire(entity.getIdSignataire());
        dto.setNatureActe(entity.getNatureActe().getLibelle());
        dto.setFonctionSignataire(entity.getFonctionSignataire().getLibelle());
        dto.setNomComplet(entity.getNomComplet());
        dto.setDepartement(entity.getDepartement());
        dto.setTitreHonorifique(entity.getTitreHonorifique());
        setCommonFieldsFromEntity(entity, dto);

        return dto;
    }

    public SignataireActe toEntity(SignataireActeDTO dto) {
        if (Objects.isNull(dto)) {
            return null;
        }
        SignataireActe entity = new SignataireActe();
        entity.setActif(dto.getActif());
        entity.setIdSignataire(dto.getIdSignataire());
        entity.setNomComplet(dto.getNomComplet());
        entity.setDepartement(dto.getDepartement());
        entity.setTitreHonorifique(dto.getTitreHonorifique());
        entity.setNatureActe(ENatureActe.getByLibelle(dto.getNatureActe()));
        entity.setFonctionSignataire(EFonctionSignataire.getByLibelle(dto.getFonctionSignataire()));
        setCommonFieldsFromDTO(dto, entity);

        return entity;
    }

}
