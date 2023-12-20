package bf.gov.gcob.medaille.mapper;

import java.util.Objects;

import org.springframework.stereotype.Component;

import bf.gov.gcob.medaille.model.dto.PieceJointeDTO;
import bf.gov.gcob.medaille.model.entities.PieceJointe;

@Component
public class PieceJointeMapper extends AbstractBaseMapper {
    public PieceJointeDTO toDTO(PieceJointe entity) {
    	if(Objects.isNull(entity)) return null;
    	
    	PieceJointeDTO dto = new PieceJointeDTO();
    	setCommonFieldsFromEntity(entity, dto);

        return dto;
    }

    public PieceJointe toEntity(PieceJointeDTO dto) {
    	PieceJointe entity = new PieceJointe();
    	setCommonFieldsFromDTO(dto, entity);

        return entity;
    }

}
