package bf.gov.gcob.medaille.mapper;

import java.util.Objects;

import org.springframework.stereotype.Component;

import bf.gov.gcob.medaille.model.dto.MedailleDTO;
import bf.gov.gcob.medaille.model.entities.Medaille;

@Component
public class MedailleMapper extends AbstractBaseMapper {
	
    public MedailleDTO toDTO(Medaille entity) {
    	if(Objects.isNull(entity)) return null;
    	
    	MedailleDTO dto = new MedailleDTO();
    	dto.setDescription(entity.getDescription());
    	setCommonFieldsFromEntity(entity, dto);
    	
        return dto;
    }

    public Medaille toEntity(MedailleDTO dto) {
    	Medaille entity = new Medaille();
       
        return entity;
    }

}
