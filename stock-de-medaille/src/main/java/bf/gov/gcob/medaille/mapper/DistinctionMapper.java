package bf.gov.gcob.medaille.mapper;

import java.util.Objects;

import org.springframework.stereotype.Component;

import bf.gov.gcob.medaille.model.dto.DistinctionDTO;
import bf.gov.gcob.medaille.model.entities.Distinction;
import bf.gov.gcob.medaille.model.enums.ETypeDistinction;

@Component
public class DistinctionMapper extends AbstractBaseMapper {
	
    public DistinctionDTO toDTO(Distinction entity) {
    	if(Objects.isNull(entity)) return null;
    	
    	DistinctionDTO dto = new DistinctionDTO();
    	dto.setAbreviation(entity.getAbreviation());
    	dto.setCategoryDistinction(entity.getCategoryDistinction().getLibelle());
    	dto.setCode(entity.getCode());
    	dto.setDateDecretCreation(entity.getDateDecretCreation());
    	dto.setDescription(entity.getDescription());
    	dto.setIdDistinction(entity.getIdDistinction());
    	dto.setLibelle(entity.getLibelle());
    	dto.setReferenceDecret(entity.getReferenceDecret());
    	setCommonFieldsFromEntity(entity, dto);
    	
        return dto;
    }

    public Distinction toEntity(DistinctionDTO dto) {
    	Distinction entity = new Distinction();
    	entity.setAbreviation(dto.getAbreviation());
    	entity.setCategoryDistinction(ETypeDistinction.getByLibelle(dto.getCategoryDistinction()));
    	entity.setCode(dto.getCode());
    	entity.setDateDecretCreation(dto.getDateDecretCreation());
    	entity.setDescription(dto.getDescription());
    	entity.setIdDistinction(dto.getIdDistinction());
    	entity.setLibelle(dto.getLibelle());
    	entity.setReferenceDecret(dto.getReferenceDecret());
    	setCommonFieldsFromDTO(dto, entity);
    	
        return entity;
    }

}
