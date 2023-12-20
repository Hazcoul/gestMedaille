package bf.gov.gcob.medaille.mapper;

import java.util.Objects;

import org.springframework.stereotype.Component;

import bf.gov.gcob.medaille.model.dto.DetenteurDTO;
import bf.gov.gcob.medaille.model.entities.Detenteur;
import bf.gov.gcob.medaille.model.enums.ECivilite;

@Component
public class DetenteurMapper extends AbstractBaseMapper {
    public DetenteurDTO toDTO(Detenteur entity) {
    	if(Objects.isNull(entity)) return null;
    	
    	DetenteurDTO dto = new DetenteurDTO();
    	dto.setCivilite(entity.getCivilite().getLibelle());
    	dto.setEmail(entity.getEmail());
    	dto.setFonction(entity.getFonction());
    	dto.setIdDetenteur(entity.getIdDetenteur());
    	dto.setMatricule(entity.getMatricule());
    	dto.setNom(entity.getNom());
    	dto.setPrenom(entity.getPrenom());
    	dto.setTelephone(entity.getTelephone());
    	setCommonFieldsFromEntity(entity, dto);

        return dto;
    }

    public Detenteur toEntity(DetenteurDTO dto) {
    	Detenteur entity = new Detenteur();
    	entity.setCivilite(ECivilite.getByLibelle(dto.getCivilite()));
    	entity.setEmail(dto.getEmail());
    	entity.setFonction(dto.getFonction());
    	entity.setIdDetenteur(dto.getIdDetenteur());
    	entity.setMatricule(dto.getMatricule());
    	entity.setNom(dto.getNom());
    	entity.setPrenom(dto.getPrenom());
    	entity.setTelephone(dto.getTelephone());
    	setCommonFieldsFromDTO(dto, entity);
    	
        return entity;
    }

}
