package bf.gov.gcob.medaille.mapper;

import java.util.Objects;

import org.springframework.stereotype.Component;

import bf.gov.gcob.medaille.model.dto.OrdonnateurDTO;
import bf.gov.gcob.medaille.model.entities.Ordonnateur;

@Component
public class OrdonnateurMapper extends AbstractBaseMapper {
    public OrdonnateurDTO toDTO(Ordonnateur entity) {
    	if(Objects.isNull(entity)) return null;
    	
    	OrdonnateurDTO dto = new OrdonnateurDTO();
    	setCommonFieldsFromEntity(entity, dto);

        return dto;
    }

    public Ordonnateur toEntity(OrdonnateurDTO dto) {
    	Ordonnateur entity = new Ordonnateur();
    	setCommonFieldsFromDTO(dto, entity);

        return entity;
    }

}
