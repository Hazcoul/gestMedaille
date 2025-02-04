package bf.gov.gcob.medaille.mapper;

import bf.gov.gcob.medaille.model.dto.GradeDTO;
import bf.gov.gcob.medaille.model.entities.Grade;
import bf.gov.gcob.medaille.model.enums.ETypeGrade;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class GradeMapper extends AbstractBaseMapper {

    public GradeDTO toDTO(Grade entity) {
        if (Objects.isNull(entity)) {
            return null;
        }

        GradeDTO dto = new GradeDTO();
        dto.setDescription(entity.getDescription());
        dto.setIdGrade(entity.getIdGrade());
        dto.setCode(entity.getCode());
        dto.setLibelle(entity.getLibelle());
        dto.setTypeGrade(entity.getTypeGrade().getLibelle());
        setCommonFieldsFromEntity(entity, dto);

        return dto;
    }

    public Grade toEntity(GradeDTO dto) {
        if (Objects.isNull(dto)) {
            return null;
        }
        Grade entity = new Grade();
        entity.setDescription(dto.getDescription());
        entity.setIdGrade(dto.getIdGrade());
        entity.setCode(dto.getCode());
        entity.setLibelle(dto.getLibelle());
        entity.setTypeGrade(ETypeGrade.getByLibelle(dto.getTypeGrade()));
        setCommonFieldsFromDTO(dto, entity);

        return entity;
    }

}
