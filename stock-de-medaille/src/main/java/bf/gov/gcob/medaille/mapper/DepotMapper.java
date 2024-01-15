package bf.gov.gcob.medaille.mapper;

import java.util.Objects;

import org.springframework.stereotype.Component;

import bf.gov.gcob.medaille.model.dto.DepotDTO;
import bf.gov.gcob.medaille.model.entities.Depot;

@Component
public class DepotMapper extends AbstractBaseMapper {
	
    public DepotDTO toDTO(Depot entity) {
    	if(Objects.isNull(entity)) return null;
    	
    	DepotDTO dto = new DepotDTO();
    	dto.setDescription(entity.getDescription());
    	dto.setIdDepot(entity.getIdDepot());
    	dto.setNomDepot(entity.getNomDepot());
        setCommonFieldsFromEntity(entity, dto);
        
        return dto;
    }

    public Depot toEntity(DepotDTO dto) {
    	Depot entity = new Depot();
    	entity.setDescription(dto.getDescription());
    	entity.setIdDepot(dto.getIdDepot());
    	entity.setNomDepot(dto.getNomDepot());
    	//setCommonFieldsFromDTO(dto, entity);
    	
        return entity;
    }

}
