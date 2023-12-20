package bf.gov.gcob.medaille.mapper;

import bf.gov.gcob.medaille.model.AbstractBaseDTO;
import bf.gov.gcob.medaille.model.AbstractBaseEntity;

public abstract class AbstractBaseMapper {
	
    protected void setCommonFieldsFromEntity(AbstractBaseEntity entity, AbstractBaseDTO dto) {
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLastModifiedBy(entity.getLastModifiedBy());
        dto.setLastModifiedDate(entity.getLastModifiedDate());
    }

    protected void setCommonFieldsFromDTO(AbstractBaseDTO dto, AbstractBaseEntity entity) {
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setLastModifiedBy(dto.getLastModifiedBy());
        entity.setLastModifiedDate(dto.getLastModifiedDate());
    }

}
