package bf.gov.gcob.medaille.mapper;

import java.util.Objects;

import org.springframework.stereotype.Component;

import bf.gov.gcob.medaille.model.dto.OrdonnateurDTO;
import bf.gov.gcob.medaille.model.entities.Ordonnateur;
import bf.gov.gcob.medaille.model.enums.ECivilite;

@Component
public class OrdonnateurMapper extends AbstractBaseMapper {
    public OrdonnateurDTO toDTO(Ordonnateur entity) {
    	if(Objects.isNull(entity)) return null;
    	
    	OrdonnateurDTO dto = new OrdonnateurDTO();
    	dto.setCivilite(entity.getCivilite().getLibelle());
    	dto.setEmail(entity.getEmail());
    	dto.setFonction(entity.getFonction());
    	dto.setIdOrdonnateur(entity.getIdOrdonnateur());
    	dto.setMatricule(entity.getMatricule());
    	dto.setNom(entity.getNom());
    	dto.setPrenom(entity.getPrenom());
    	dto.setTelephone(entity.getTelephone());
    	setCommonFieldsFromEntity(entity, dto);

        return dto;
    }

    public Ordonnateur toEntity(OrdonnateurDTO dto) {
    	Ordonnateur entity = new Ordonnateur();
    	entity.setCivilite(ECivilite.getByLibelle(dto.getCivilite()));
    	entity.setEmail(dto.getEmail());
    	entity.setFonction(dto.getFonction());
    	entity.setIdOrdonnateur(dto.getIdOrdonnateur());
    	entity.setMatricule(dto.getMatricule());
    	entity.setNom(dto.getNom());
    	entity.setPrenom(dto.getPrenom());
    	entity.setTelephone(dto.getTelephone());
    	setCommonFieldsFromDTO(dto, entity);

        return entity;
    }

}
