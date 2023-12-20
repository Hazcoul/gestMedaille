package bf.gov.gcob.medaille.mapper;

import java.util.Objects;

import org.springframework.stereotype.Component;

import bf.gov.gcob.medaille.model.dto.SortieDTO;
import bf.gov.gcob.medaille.model.entities.Sortie;

@Component
public class SortieMapper extends AbstractBaseMapper {
    public SortieDTO toDTO(Sortie entity) {
    	if(Objects.isNull(entity)) return null;
    	
    	SortieDTO dto = new SortieDTO();
    	setCommonFieldsFromEntity(entity, dto);

        return dto;
    }

    public Sortie toEntity(SortieDTO dto) {
    	Sortie entity = new Sortie();
    	setCommonFieldsFromDTO(dto, entity);

        return entity;
    }

}
